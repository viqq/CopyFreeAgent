package com.free.agent.dto;

import com.free.agent.annotation.Email;
import lombok.Data;

/**
 * Created by antonPC on 23.12.15.
 */
@Data
public class EmailDto {
    @Email
    private String email;
}
