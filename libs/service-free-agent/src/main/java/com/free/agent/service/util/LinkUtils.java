package com.free.agent.service.util;

/**
 * Created by antonPC on 03.11.15.
 */
public final class LinkUtils {
    private static final String HOST = "http://localhost:8080";

    public static String getLinkForRegistration(String email, String randomString, boolean isPostponed) {
        String key = "key=" + EncryptionUtils.md5(email);
        String hash = "&hash=" + randomString;
        return HOST + (isPostponed ? "/registration?" : "/activate?") + key + hash;
    }
}
