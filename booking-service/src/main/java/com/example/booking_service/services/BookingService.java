package com.example.booking_service.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.booking_service.Feign.EventsClient;
import com.example.booking_service.Kafka.KafkaMessagePublisher;
import com.example.booking_service.bookingsDTO.BookingDTO;
import com.example.booking_service.entities.Booking;
import com.example.booking_service.entities.BookingStatusResponse;
import com.example.booking_service.entities.Seat;
import com.example.booking_service.repositories.BookingRepository;
import com.example.booking_service.repositories.BookingStatusRepository;
import com.example.booking_service.repositories.SeatRepository;
import com.example.booking_service.utilities.BookingStatus;
import com.example.booking_service.utilities.SeatStatus;
// import com.example.nextshowdto.;
import com.example.nextshowdto.PaymnentsServiceBookingObject;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingStatusRepository bookingStatusRepository;

    @Autowired
    private EventsClient eventsClient;

    @Autowired
    KafkaMessagePublisher kafkjKafkaMessagePublisher;

    ConcurrentHashMap<String, String> bookingStatusMap = new ConcurrentHashMap<String, String>();

    @Transactional
    public BookingDTO createBooking(Long userId, Long showId, List<com.example.nextshowdto.Seat> seatRequestdto) {
        BookingStatusResponse saved_status = bookingStatusRepository.save(new BookingStatusResponse(
                String.valueOf(userId) + String.valueOf(showId) + seatRequestdto.get(0).getSeatId(), "In Progress"));

        System.out.println("Saved Booking Status: " + saved_status);
        List<com.example.booking_service.entities.Seat> seatRequests = convertToCompatibleBookingDTO(seatRequestdto);

        List<String> seatIds = new ArrayList<>();

        // Extract seat IDs from the request list
        for (Seat seat : seatRequests) {
            seatIds.add(seat.getSeatId());
        }

        // <!-- API CALL TO CHECK IF SEATS ARE VALID & THERE GET THERE PRICE --!> //add
        // redis here cache

        // calling events service to check validity of seat
        Long price = check_seats_validity(seatRequestdto);
        Double Price = price.doubleValue();

        // calling show service to check price

        // Fetch existing seats from DB
        List<Seat> existingSeats = seatRepository.findBySeatIdIn(seatIds);

        List<Seat> finalSeats = new ArrayList<>();

        // Iterate over requested seats and handle existing/non-existing seats
        for (Seat seatRequest : seatRequests) {
            boolean seatExists = false;

            for (Seat dbSeat : existingSeats) {
                if (dbSeat.getSeatId().equals(seatRequest.getSeatId())) {
                    seatExists = true;

                    // Check if seat is available
                    if (dbSeat.getSeatStatus() != SeatStatus.AVAILABLE) {
                        throw new RuntimeException("Some seats are not available!");
                    }
                    finalSeats.add(dbSeat);
                    break;
                }
            }

            // If the seat does not exist in DB, consider it available
            if (!seatExists) {
                Seat newSeat = new Seat();
                newSeat.setSeatId(seatRequest.getSeatId());
                newSeat.setSeatStatus(SeatStatus.AVAILABLE);
                newSeat.setSeatPrice(Price * seatRequestdto.size()); // TODO: Get actual price

                finalSeats.add(newSeat);
            }
        }

        // Create Booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setShowId(showId);
        booking.setFinalPrice(Price); // TODO: Calculate actual price
        booking.setBookingStatus(BookingStatus.ONGOING);

        booking = bookingRepository.save(booking);

        // Assign booking to seats and update status
        for (Seat seat : finalSeats) {
            seat.setBooking(booking);
            seat.setSeatStatus(SeatStatus.RESERVED);
        }

        List<Seat> seats = (List<Seat>) seatRepository.saveAll(finalSeats); // Save seats after assigning to booking

        // <!-- API CALL TO FOR PAYMENT AND NOTIFY SEVRVICE --!>
        PaymentAndNotify(userId, showId, seatRequestdto, Price);
        //

        bookingStatusRepository.save(new BookingStatusResponse(
                String.valueOf(userId) + String.valueOf(showId) + seatRequestdto.get(0).getSeatId(),
                "Ready for Payment"));

        System.out.println("seats booking came here");
        booking.setSeats(seats);
        return entityToDTO(booking);
    }

    private void PaymentAndNotify(Long userId, Long showId, List<com.example.nextshowdto.Seat> seatRequestdto,
            Double Price) {
        // call
        com.example.nextshowdto.PaymnentsServiceBookingObject obj = new com.example.nextshowdto.PaymnentsServiceBookingObject(
                userId, showId,
                seatRequestdto,
                Price);
        kafkjKafkaMessagePublisher.AddPaymnentsServiceBooking("payment-and-notifications", obj);
    }

    public String getBookingStatusUpdates(String userId, String showid, String firstseat) {
        Optional<BookingStatusResponse> res = bookingStatusRepository.findById(userId + showid + firstseat);
        if (res.isPresent()) {
            return res.get().getStatus();
        }
        return "Bookng_status_not_found";
    }

    public String getBookingStatus(String userId, String showId, String firstSeat) {
        Optional<BookingStatusResponse> res = bookingStatusRepository.findById(userId + showId + firstSeat);
        if (res.isPresent()) {
            return res.get().getStatus();
        } else {
            throw new RuntimeException("booking not found");
        }

    }

    public BookingDTO entityToDTO(Booking booking) {
        return BookingDTO.builder()
                .bookingId(booking.getBookingId())
                .userId(booking.getUserId())
                .showId(booking.getShowId())
                .seats(booking.getSeats()) // Copy seats directly (ensure DTO does not have @Entity annotation)
                .finalPrice(booking.getFinalPrice())
                .build();
    }

    // Sxxx-Cxxx-Rxxx
    public Map<String, Map<Long, Long>> GetSeatsMap(List<String> seatIds) throws IllegalArgumentException {
        final String SEAT_ID_REGEX = "S\\d{3}-C(\\d{3})-R(\\d{3})"; // Capturing groups for CXXX and RXXX
        Pattern pattern = Pattern.compile(SEAT_ID_REGEX);

        Map<String, Map<Long, Long>> result = new HashMap<>();

        for (String seatId : seatIds) {
            Matcher matcher = pattern.matcher(seatId);

            if (matcher.matches()) {
                Long columnNumber = Long.parseLong(matcher.group(1)); // Extract XXX after C
                Long rowNumber = Long.parseLong(matcher.group(2)); // Extract XXX after R

                result.put(seatId, Map.of(columnNumber, rowNumber)); // Store in map
            } else {
                throw new IllegalArgumentException("Invalid seat ID format: " + seatId);
            }
        }

        return result;
    }

    public List<Seat> convertToCompatibleBookingDTO(List<com.example.nextshowdto.Seat> seatRequests) {
        return seatRequests.stream().map(seat -> {
            Seat newSeat = new Seat();
            newSeat.setSeatId(seat.getSeatId());
            newSeat.setSeatPrice(seat.getSeatPrice());
            newSeat.setSeatStatus(seat.getSeatStatus() != null ? seat.getSeatStatus().toString() : null);
            newSeat.setBooking(null); // Prevent circular reference issues

            return newSeat;
        }).collect(Collectors.toList());
    }

    // check with other mirco-services check seat is valid and return price
    public Long check_seats_validity(List<com.example.nextshowdto.Seat> seats) {
        try {
            ResponseEntity<Long> ticket_price = eventsClient.check_seats_validity_and_get_price(seats);

            if (ticket_price.getBody().equals(-1)) {
                throw new IllegalArgumentException("seat is invalid");
            }

            return ticket_price.getBody();

        } catch (Exception e) {

            throw new IllegalArgumentException("request to events service rejected maybe service down");

        }
    }

}
