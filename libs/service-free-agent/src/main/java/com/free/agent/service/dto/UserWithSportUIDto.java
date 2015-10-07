package com.free.agent.service.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by antonPC on 18.08.15.
 */
public class UserWithSportUIDto extends UserDto {
    private List<SportUIDto> sports;
    private Date dateOfRegistration;

    public List<SportUIDto> getSports() {
        return sports;
    }

    public void setSports(List<SportUIDto> sports) {
        this.sports = sports;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }
}
