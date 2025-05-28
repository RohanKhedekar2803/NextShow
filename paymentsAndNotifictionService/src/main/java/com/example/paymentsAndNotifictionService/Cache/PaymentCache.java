package com.example.paymentsAndNotifictionService.Cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class PaymentCache {
    private static final Map<String, String> checkoutUrlMap = new ConcurrentHashMap<>();

    public static void store(String key, String checkoutUrl) {
        checkoutUrlMap.put(key, checkoutUrl);
    }

    public static String get(String key) {
        return checkoutUrlMap.get(key);
    }

    public static boolean contains(String key) {
        return checkoutUrlMap.containsKey(key);
    }

    public static void clear() {
        checkoutUrlMap.clear();
    }
}
