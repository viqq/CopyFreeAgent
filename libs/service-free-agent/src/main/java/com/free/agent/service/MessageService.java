package com.free.agent.service;

import com.free.agent.model.Message;
import com.free.agent.service.dto.MessageDto;

import java.security.Principal;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    Set<Message> findAllByReceiver(String login);

    Set<Message> findAllByAuthor(String login);

    Set<Message> findAllByReceiverAndAuthor(Long id, String email, Principal principal);

    void save(MessageDto messageDto, String email, Principal principal) throws IllegalAccessException;

    void updateMessageStatus(Long id, String name);

    int countUnreadMessages(String id);

    Set<Message> getHistory(Long name, String login);
}
