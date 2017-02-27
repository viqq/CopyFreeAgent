package com.free.agent;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by antonPC on 18.12.15.
 */
@Getter
@AllArgsConstructor
public final class SocialNetworkProperty {
    private final String client_id;
    private final String client_secret;
    private final String token_url;
    private final String profile_url;

}
