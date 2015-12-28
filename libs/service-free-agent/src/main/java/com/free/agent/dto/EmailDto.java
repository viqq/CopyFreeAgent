package com.free.agent.dto;

import com.free.agent.annotation.Email;

/**
 * Created by antonPC on 23.12.15.
 */
public class EmailDto {
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
