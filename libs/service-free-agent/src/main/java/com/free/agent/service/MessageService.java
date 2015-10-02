package com.free.agent.service;

import com.free.agent.dao.dto.Participant;
import com.free.agent.model.Message;
import com.free.agent.service.dto.MessageDto;

import java.security.Principal;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    Set<Message> findAllByReceiver(String email);

    Set<Message> findAllByAuthor(String email);

    Set<Message> findAllByReceiverAndAuthor(Long id, String email, Principal principal);

    void save(MessageDto messageDto, String email, Principal principal) throws IllegalAccessException;

    void updateMessageStatus(Long id, String email);

    int countUnreadMessages(String email);

    Set<Message> getHistory(Long id, String email);

    Set<Participant> getParticipants(String email);
}
