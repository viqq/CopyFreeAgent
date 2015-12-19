package com.free.agent;

/**
 * Created by antonPC on 18.12.15.
 */
public final class SocialNetworkProperty {
    private final String client_id;
    private final String client_secret;
    private final String token_url;
    private final String profile_url;


    public SocialNetworkProperty(String client_id, String client_secret, String token_url, String profile_url) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.token_url = token_url;
        this.profile_url = profile_url;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getToken_url() {
        return token_url;
    }

    public String getProfile_url() {
        return profile_url;
    }
}
