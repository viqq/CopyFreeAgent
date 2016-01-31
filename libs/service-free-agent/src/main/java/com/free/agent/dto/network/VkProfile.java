package com.free.agent.dto.network;

import com.free.agent.field.Gender;
import com.free.agent.model.User;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by antonPC on 19.12.15.
 */
public class VkProfile implements SocialProfile {
    private VkDto dto;
    private String city;
    private String country;
    private String email;

    public VkProfile(HttpEntity entity) throws IOException {
        String json = EntityUtils.toString(entity);
        this.dto = new Gson().fromJson(json.substring(json.indexOf("[") + 1, json.indexOf("]")), VkDto.class);
    }

    public void setCity(String city) {
        this.city = city.substring(city.indexOf("name") + 7, city.indexOf("\"}]}"));
    }

    public void setCountry(String country) {
        this.country = country.substring(country.indexOf("name") + 7, country.indexOf("\"}]}"));
    }

    public String getCityId() {
        return dto.getCity();
    }

    public String getCountryId() {
        return dto.getCountry();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isVerified() {
        return Integer.parseInt(dto.getVerified()) == 1;
    }

    @Override
    public String getFirstName() {
        return dto.getFirst_name();
    }

    @Override
    public String getLastName() {
        return dto.getLast_name();
    }

    @Override
    public Long getId() {
        return new BigDecimal(dto.getUid()).longValue();
    }

    @Override
    public String getImage() {
        return dto.getPhoto_200();
    }

    @Override
    public byte[] getImageByte() {
        throw new UnsupportedOperationException("Use getImage() for vk profile");
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public Date getBirthday() {
        try {
            return new SimpleDateFormat("dd.mm.yyyy").parse(dto.getBdate());
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Gender getGender() {
        switch (Integer.parseInt(dto.getSex())) {
            case 1: {
                return Gender.FEMALE;
            }
            case 2: {
                return Gender.MALE;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public SocialNetwork getType() {
        return SocialNetwork.VK;
    }

    @Override
    public boolean isAuthentication(User user) {
        if (user.getVkId() == null) {
            throw new IllegalArgumentException("Trying login with vk. But you registered by another social network");
        }
        return user.getEmail().equals(getEmail()) && user.getVkId().equals(getId());
    }


}
