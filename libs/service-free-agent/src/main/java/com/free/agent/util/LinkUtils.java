package com.free.agent.util;

import com.free.agent.FreeAgentAPI;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by antonPC on 03.11.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LinkUtils {

    public static String getLinkForRegistration(String email, String randomString, boolean isPostponed) {
        String key = "key=" + EncryptionUtils.md5(email);
        String hash = "&hash=" + randomString;
        return FreeAgentAPI.host + (isPostponed ? "/registration?" : "/activate?") + key + hash;
    }
}
