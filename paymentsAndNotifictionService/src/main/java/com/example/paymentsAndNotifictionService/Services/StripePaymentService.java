package com.example.paymentsAndNotifictionService.Services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    public String createCheckoutSession(Long quantity, Double amount, String currency, String successUrl,
            String cancelUrl) throws StripeException { // call build params to build payment params in 1 object suitable
                                                       // for stripe creates a session for payment with returining url
                                                       // to pay for
        Stripe.apiKey = secretKey; // Set API Key

        SessionCreateParams params = buildSessionParams(quantity, amount, currency, successUrl, cancelUrl);
        Session session = Session.create(params);
        return session.getUrl();
    }

    private SessionCreateParams buildSessionParams(Long quantity, Double amount, String currency, String successUrl,
            String cancelUrl) { // actually build param required by stripe
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(buildLineItem(quantity, amount, currency)) // build data apart from urls and payment mode
                                                                        // setting
                .build();
    }

    private SessionCreateParams.LineItem buildLineItem(Long quantity, Double amount, String currency) { // build object
                                                                                                        // for amount
                                                                                                        // quantity
                                                                                                        // currentcy etc
        return SessionCreateParams.LineItem.builder()
                .setQuantity(quantity)
                .setPriceData(buildPriceData(amount, currency))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData buildPriceData(Double amount, String currency) { // setup curency
        if (currency == null || currency == "") {
            currency = "INR";

        }
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(currency)
                .setUnitAmount((long) (amount * 100)) // Convert amount to cents
                .setProductData(buildProductData()) // name of paument product or style
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData buildProductData() {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("Booking Payment to next show ")
                .build();
    }
}
