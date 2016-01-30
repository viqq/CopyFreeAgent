package com.free.agent.dto.network;

import com.free.agent.field.Gender;
import com.free.agent.model.User;

import java.util.Date;

/**
 * Created by antonPC on 19.12.15.
 */
public interface SocialProfile {

    String getEmail();

    boolean isVerified();

    String getFirstName();

    String getLastName();

    Long getId();

    String getImage();

    byte[] getImageByte();

    String getCity();

    String getCountry();

    Date getBirthday();

    Gender getGender();

    SocialNetwork getType();

    boolean isAuthentication(User user);

}
