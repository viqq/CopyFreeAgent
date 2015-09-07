package com.free.agent.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by antonPC on 18.08.15.
 */
public class UserWithSport extends UserDto {

    private List<String> sports;
    private Date dateOfRegistration;

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }
}
