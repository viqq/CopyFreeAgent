package com.free.agent.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by antonPC on 18.08.15.
 */
@Data
public class UserWithSportUIDto extends UserDto {
    private List<SportUIDto> sports;
    private Long dateOfRegistration;
    private String role;
    private String link;

}
