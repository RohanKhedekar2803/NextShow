package com.example.paymentsAndNotifictionService.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StripeInfoStore {
    private static final Map<String, stripecallbackUserInfo> StripeCache = new ConcurrentHashMap<>();

    public static void Store(String bookingKey, stripecallbackUserInfo info) {
        StripeCache.put(bookingKey, info);
    }

    public static stripecallbackUserInfo Get(String bookingKey) {
        return StripeCache.get(bookingKey);
    }

    public static void Remove(String bookingKey) {
        StripeCache.remove(bookingKey);
    }
}
