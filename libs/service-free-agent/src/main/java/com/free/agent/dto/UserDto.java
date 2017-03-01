package com.free.agent.dto;

import com.free.agent.annotation.Phone;
import lombok.Data;


/**
 * Created by antonPC on 28.06.15.
 */
@Data
public class UserDto extends UserRegistrationDto {

    private String city;
    private String country;
    private String description;
    private String lastName;
    private Long dateOfBirth;
    @Phone
    private String phone;
    private String gender;

}
