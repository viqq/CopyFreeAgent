package com.free.agent.service;

import com.free.agent.model.Message;

import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    Set<Message> findAllByReceiver(String login);

    Set<Message> findAllByReceiverAndAuthor(String login, String author);
}
