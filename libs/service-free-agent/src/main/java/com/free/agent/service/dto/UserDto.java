package com.free.agent.service.dto;

import com.free.agent.service.annotation.Phone;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;


/**
 * Created by antonPC on 28.06.15.
 */

public class UserDto extends UserRegistrationDto {
    private String city;
    private String description;
    private String lastName;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Past
    private Date dateOfBirth;
    @Phone
    private String phone;
    private String gender;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
