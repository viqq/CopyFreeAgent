package com.free.agent.dto;

import com.free.agent.annotation.Email;
import com.free.agent.annotation.Password;
import com.free.agent.annotation.User;

/**
 * Created by antonPC on 25.09.15.
 */
public class UserRegistrationDto {
    private Long id;
    @Email
    private String email;
    @Password
    private String password;
    @User
    private String firstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
