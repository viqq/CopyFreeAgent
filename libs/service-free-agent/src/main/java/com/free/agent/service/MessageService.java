package com.free.agent.service;

import com.free.agent.dto.MessageDto;
import com.free.agent.dto.MessageUIDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.model.Message;

import java.security.Principal;
import java.util.Collection;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    Collection<MessageUIDto> findAllByReceiver(String email);

    Collection<Message> findAllByAuthor(String email);

    Collection<Message> findAllByReceiverAndAuthor(Long id, Principal principal);

    void save(MessageDto messageDto, String email, Principal principal) throws EmailAlreadyUsedException;

    void updateMessageStatus(Long id, String email);

    int countUnreadMessages(String email);

    Collection<MessageUIDto> getHistory(Long id, String email);

    Collection<Long> getParticipants(String email);
}
