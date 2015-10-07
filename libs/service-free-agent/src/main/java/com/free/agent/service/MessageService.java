package com.free.agent.service;

import com.free.agent.dao.dto.Participant;
import com.free.agent.model.Message;
import com.free.agent.service.dto.MessageDto;
import com.free.agent.service.dto.MessageUIDto;

import java.security.Principal;
import java.util.Collection;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    Collection<MessageUIDto> findAllByReceiver(String email);

    Collection<Message> findAllByAuthor(String email);

    Collection<Message> findAllByReceiverAndAuthor(Long id, String email, Principal principal);

    void save(MessageDto messageDto, String email, Principal principal) throws IllegalAccessException;

    void updateMessageStatus(Long id, String email);

    int countUnreadMessages(String email);

    Collection<MessageUIDto> getHistory(Long id, String email);

    Collection<Participant> getParticipants(String email);
}
