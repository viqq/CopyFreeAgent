package com.free.agent.dto.network;


import com.free.agent.field.Gender;
import com.free.agent.model.User;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by antonPC on 19.12.15.
 */
public class GoogleProfile implements SocialProfile {

    private GoogleDto dto;

    public GoogleProfile(HttpEntity entity) throws IOException {
        this.dto = new Gson().fromJson(EntityUtils.toString(entity), GoogleDto.class);
    }

    @Override
    public String getEmail() {
        return dto.getEmail();
    }

    @Override
    public boolean isVerified() {
        return Boolean.parseBoolean(dto.getVerified_email());
    }

    @Override
    public String getFirstName() {
        return dto.getName();
    }

    @Override
    public String getLastName() {
        return dto.getFamily_name();
    }

    @Override
    public Long getId() {
        return new BigDecimal(dto.getId()).longValue();
    }

    @Override
    public String getImage() {
        return dto.getPicture();
    }

    @Override
    public byte[] getImageByte() {
        throw new UnsupportedOperationException("Use getImage() for google profile");
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public Date getBirthday() {
        return null;
    }

    @Override
    public Gender getGender() {
        return dto.getGender() == null ? null : Gender.valueOf(dto.getGender().toUpperCase());
    }

    @Override
    public SocialNetwork getType() {
        return SocialNetwork.GOOGLE;
    }

    @Override
    public boolean isAuthentication(User user) {
        if (user.getGoogleId() == null) {
            throw new IllegalArgumentException("Trying login with google. But you registered by another social network");
        }
        return (user.getEmail().equals(getEmail()) && user.getGoogleId().equals(getId()));
    }
}
