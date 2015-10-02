package com.free.agent.service;

/**
 * Created by antonPC on 30.09.15.
 */
public interface MailService {

    void sendMail(String to, String subject, String body);
}
