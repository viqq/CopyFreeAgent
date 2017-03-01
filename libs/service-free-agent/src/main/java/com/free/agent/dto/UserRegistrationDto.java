package com.free.agent.dto;

import com.free.agent.annotation.Password;
import com.free.agent.annotation.User;
import lombok.Data;

/**
 * Created by antonPC on 25.09.15.
 */
@Data
public class UserRegistrationDto extends EmailDto {

    private Long id;
    @Password
    private String password;
    @User
    private String firstName;

}
