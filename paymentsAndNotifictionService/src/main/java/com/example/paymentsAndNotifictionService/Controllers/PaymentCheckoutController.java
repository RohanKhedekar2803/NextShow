package com.example.paymentsAndNotifictionService.Controllers;

import com.example.paymentsAndNotifictionService.Cache.PaymentCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-checkout")
public class PaymentCheckoutController {

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
}
