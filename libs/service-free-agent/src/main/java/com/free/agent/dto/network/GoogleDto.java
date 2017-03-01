package com.free.agent.dto.network;

import lombok.Data;

/**
 * Created by antonPC on 19.12.15.
 */
@Data
public class GoogleDto {
    private String id;
    private String email;
    private String verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
    private String gender;
    private String locale;

}
