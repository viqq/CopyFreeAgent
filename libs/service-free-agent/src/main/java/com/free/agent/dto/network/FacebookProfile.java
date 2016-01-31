package com.free.agent.dto.network;

import com.free.agent.field.Gender;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by antonPC on 19.12.15.
 */
public class FacebookProfile implements SocialProfile {
    private User dto;
    private byte[] picture;

    public FacebookProfile(Facebook dto) {
        this.dto = dto.userOperations().getUserProfile();
        this.picture = dto.userOperations().getUserProfileImage(200, 200);
    }

    @Override
    public String getEmail() {
        return dto.getEmail();
    }

    @Override
    public boolean isVerified() {
        return dto.isVerified();
    }

    @Override
    public String getFirstName() {
        return dto.getFirstName();
    }

    @Override
    public String getLastName() {
        return dto.getLastName();
    }

    @Override
    public Long getId() {
        return new BigDecimal(dto.getId()).longValue();
    }

    @Override
    public String getImage() {
        throw new UnsupportedOperationException("Use getImageByte() for facebook profile");
    }

    public byte[] getImageByte() {
        return picture;
    }

    @Override
    public String getCity() {
        return dto.getLocation().getName().split(",")[0];
    }

    @Override
    public String getCountry() {
        return dto.getLocation().getName().substring(dto.getLocation().getName().indexOf(",") + 1);
    }

    @Override
    public Date getBirthday() {
        try {
            return new SimpleDateFormat("dd/mm/yyyy").parse(dto.getBirthday());
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Gender getGender() {
        return dto.getGender() == null ? null : Gender.valueOf(dto.getGender().toUpperCase());
    }

    @Override
    public SocialNetwork getType() {
        return SocialNetwork.FACEBOOK;
    }

    @Override
    public boolean isAuthentication(com.free.agent.model.User user) {
        if (user.getFacebookId() == null) {
            throw new IllegalArgumentException("Trying login with facebook. But you registered by another social network");
        }
        return (user.getEmail().equals(getEmail()) && user.getFacebookId().equals(getId()));
    }
}
