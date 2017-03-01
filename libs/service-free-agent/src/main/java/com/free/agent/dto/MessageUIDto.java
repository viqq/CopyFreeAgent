package com.free.agent.dto;

import lombok.Data;

/**
 * Created by antonPC on 15.09.15.
 */
@Data
public class MessageUIDto {

    private Long id;
    private Long authorId;
    private String authorEmail;
    private String title;
    private String text;
    private Long timeOfCreate;
    private Long timeOfRead;

}
