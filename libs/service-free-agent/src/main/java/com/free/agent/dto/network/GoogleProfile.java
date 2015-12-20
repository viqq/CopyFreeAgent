package com.free.agent.dto.network;


import com.free.agent.field.Gender;
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
}
