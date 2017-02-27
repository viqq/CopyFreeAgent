package com.free.agent.dto;

import lombok.Data;

/**
 * Created by antonPC on 11.09.15.
 */
@Data
public class MessageDto {

    private Long id;
    private String title;
    private String text;
    private String email;

}
