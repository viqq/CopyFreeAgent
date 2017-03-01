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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("mailService")
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    @Autowired
    private MailSender mailSender;

    public void sendMail(final String to, final String subject, final String body) {
        executor.submit(() -> {
            LOGGER.info("sendMail start");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            LOGGER.info("sendMail before start");
            mailSender.send(message);
            LOGGER.info(String.format("Email %s about %s was sent to %s", subject, body, to));
        });
    }
}
