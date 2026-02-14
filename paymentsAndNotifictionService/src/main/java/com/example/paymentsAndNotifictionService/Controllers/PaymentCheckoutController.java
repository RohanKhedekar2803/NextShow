package com.example.paymentsAndNotifictionService.Controllers;

import com.example.paymentsAndNotifictionService.Cache.PaymentCache;
import com.example.paymentsAndNotifictionService.Cache.StripeInfoStore;
import com.example.paymentsAndNotifictionService.Cache.stripecallbackUserInfo;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.paymentsAndNotifictionService.Services.EmailService;

import com.stripe.model.checkout.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment-checkout")
public class PaymentCheckoutController {

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{userId}/{showId}/{seatId}")
    public ResponseEntity<String> getCheckoutUrl(
            @PathVariable String userId,
            @PathVariable String showId,
            @PathVariable String seatId) {

        String key = userId + "-" + showId + "-" + seatId;

        if (PaymentCache.contains(key)) {
            return ResponseEntity.ok(PaymentCache.get(key));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            System.out.println("🔔 Stripe webhook received");
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("❌ Invalid Stripe signature");
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        System.out.println("✅ Stripe event type: " + event.getType());

        // We care only about checkout session events
        if (!event.getType().startsWith("checkout.session")) {
            return ResponseEntity.ok("ignored");
        }

        // ---------------- Deserialize safely ----------------
        var deserializer = event.getDataObjectDeserializer();
        Session session = null;

        try {
            if (deserializer.getObject().isPresent()) {
                session = (Session) deserializer.getObject().get();
            } else {
                System.out.println("⚠️ Falling back to unsafe deserialization");
                session = (Session) deserializer.deserializeUnsafe();
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to deserialize session: " + e.getMessage());
            return ResponseEntity.ok("deserialize error");
        }

        if (session == null) {
            System.out.println("❌ Session is null");
            return ResponseEntity.ok("no session");
        }

        String bookingKey = session.getMetadata() != null
                ? session.getMetadata().get("bookingKey")
                : null;

        if (bookingKey == null) {
            System.out.println("❌ bookingKey missing in metadata");
            return ResponseEntity.ok("missing metadata");
        }

        System.out.println("🎯 bookingKey: " + bookingKey);

        // ---------------- Fetch booking info ----------------
        stripecallbackUserInfo info = StripeInfoStore.Get(bookingKey);

        if (info == null) {
            System.out.println("❌ No booking info found for " + bookingKey);
            return ResponseEntity.ok("no booking info");
        }

        // ================= SUCCESS =================
        if ("checkout.session.completed".equals(event.getType())) {
            System.out.println("💰 Payment SUCCESS for " + bookingKey);
            emailService.sendEmail(info.getSeats(), info.getShowId(), info.getUserId(), true);
        }

        // ================= EXPIRED =================
        if ("checkout.session.expired".equals(event.getType())) {
            System.out.println("⌛ Payment EXPIRED for " + bookingKey);
            emailService.sendEmail(info.getSeats(), info.getShowId(), info.getUserId(), false);
        }

        return ResponseEntity.ok("received");
    }

}
