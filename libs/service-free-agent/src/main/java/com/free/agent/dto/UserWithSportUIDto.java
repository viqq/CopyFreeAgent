package com.free.agent.dto;

import java.util.List;

/**
 * Created by antonPC on 18.08.15.
 */
public class UserWithSportUIDto extends UserDto {
    private List<SportUIDto> sports;
    private Long dateOfRegistration;
    private String role;
    private String link;

    public List<SportUIDto> getSports() {
        return sports;
    }

    public void setSports(List<SportUIDto> sports) {
        this.sports = sports;
    }

    public Long getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Long dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
