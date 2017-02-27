package com.free.agent.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by antonPC on 18.08.15.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithSportUIDto extends UserDto {

    private List<SportUIDto> sports;
    private Long dateOfRegistration;
    private String role;
    private String link;

}
