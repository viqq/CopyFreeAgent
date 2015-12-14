package com.free.agent.dto;

/**
 * Created by antonPC on 11.12.15.
 */
public class FavoriteDto {

    private Long userId;
    private String userEmail;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
