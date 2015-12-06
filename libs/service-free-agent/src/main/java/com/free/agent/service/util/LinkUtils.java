package com.free.agent.service.util;

import com.free.agent.service.FreeAgentAPI;

/**
 * Created by antonPC on 03.11.15.
 */
public final class LinkUtils {

    public static String getLinkForRegistration(String email, String randomString, boolean isPostponed) {
        String key = "key=" + EncryptionUtils.md5(email);
        String hash = "&hash=" + randomString;
        return FreeAgentAPI.host + (isPostponed ? "/registration?" : "/activate?") + key + hash;
    }
}
