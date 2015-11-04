package com.free.agent.service.impl;

/**
 * Created by antonPC on 27.09.15.
 */

import com.free.agent.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    private MailSender mailSender;


    public void sendMail(String to, String subject, String body) {
        LOGGER.info("sendMail start");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        LOGGER.info("sendMail before start");
        mailSender.send(message);
        LOGGER.info("Email '" + subject + "' about '" + body + "' was sent to " + to);
    }
}
