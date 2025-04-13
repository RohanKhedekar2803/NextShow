package com.example.booking_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_service.Kafka.KafkaMessagePublisher;
import com.example.booking_service.bookingsDTO.BookingDTO;
import com.example.booking_service.services.BookingService;

@RestController
@RequestMapping("Bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    // @PostMapping("")
    // public BookingDTO addBooking(@RequestBody com.example.nextshowdto.BookingDTO
    // bookingDTO) {
    // BookingDTO booking = bookingService.createBooking(bookingDTO.getUserId(),
    // bookingDTO.getShowId(),
    // bookingDTO.getSeats());
    // return booking;
    // }
    @GetMapping("/test")
    public String data() {
        return "CWT";
    }

    @PostMapping("publish/{topic}") // controller to add booking in queue
    public ResponseEntity<?> produce(@PathVariable String topic, @RequestBody com.example.nextshowdto.BookingDTO msg) {
        try {
            kafkaMessagePublisher.AddRequestInQueue(topic, msg);
            return ResponseEntity.ok("msg sent in topic [" + topic + "] data is [" + msg + " ]");
        } catch (Exception e) {
            System.out.println("error is " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("status/{userId}/{showid}/{firstseat}") // controller to add booking in queue
    public ResponseEntity<String> BookingStatusUpdates(@PathVariable String userId, @PathVariable String showid,
            @PathVariable String firstseat) {

        return ResponseEntity.ok(
                bookingService.getBookingStatusUpdates(userId, showid, firstseat));

    }
}
