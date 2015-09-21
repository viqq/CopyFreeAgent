package com.free.agent.service;

import com.free.agent.model.Message;

import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    Set<Message> findAllByReceiver(String login);

    Set<Message> findAllByAuthor(String author);

    Set<Message> findAllByReceiverAndAuthor(String login, String author);

    void save(Long id, Message message, String author);

    void updateMessageStatus(Long id, String name);

    int countUnreadMessages(String name);

    Set<Message> getHistory(String name, Long id);
}
