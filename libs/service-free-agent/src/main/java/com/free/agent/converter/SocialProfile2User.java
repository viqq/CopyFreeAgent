package com.free.agent.converter;

import com.free.agent.dto.network.SocialProfile;
import com.free.agent.field.Role;
import com.free.agent.model.User;
import com.free.agent.util.EncryptionUtils;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class SocialProfile2User implements Converter<SocialProfile, User> {

    @Override
    public User convert(SocialProfile profile) {
        User user = new User();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setDateOfRegistration(DateTime.now().toDate());
        user.setLastActivity(DateTime.now().toDate());
        user.setCity(profile.getCity());
        user.setCountry(profile.getCountry());
        user.setLastName(profile.getLastName());
        user.setGender(profile.getGender());
        user.setDateOfBirth(profile.getBirthday());
        setType(user, profile);
        setRole(user, profile.isVerified());
        return user;
    }

    private static void setType(User user, SocialProfile profile) {
        switch (profile.getType()) {
            case GOOGLE: {
                user.setGoogleId(profile.getId());
                break;
            }
            case VK: {
                user.setVkId(profile.getId());
                break;
            }
            case FACEBOOK: {
                user.setFacebookId(profile.getId());
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported type of social network");
            }
        }
    }

    private static void setRole(User user, boolean verified) {
        if (verified) {
            user.setRole(Role.ROLE_MODERATOR);
        } else {
            user.setRole(Role.ROLE_NOT_CONFIRMED);
            user.setHash(EncryptionUtils.getRandomString());
        }
    }
}
