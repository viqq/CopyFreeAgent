package com.free.agent;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by antonPC on 22.11.15.
 */
@Data
public class Token implements Serializable {
    private String access_token;
    private String token_type;
    private String expires_in;
    private String id_token;
    private String user_id;
    private String email;

}
