package com.free.agent.service;

import com.free.agent.dto.MessageDto;
import com.free.agent.dto.MessageUIDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.model.Message;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageService {

    void removeOldMessages();

    List<MessageUIDto> findAllByReceiver(String email);

    Set<Message> findAllByAuthor(String email);

    Set<Message> findAllByReceiverAndAuthor(Long id, Principal principal);

    void save(MessageDto messageDto, String email, Principal principal) throws EmailAlreadyUsedException;

    void updateMessageStatus(Long id, String email);

    int countUnreadMessages(String email);

    List<MessageUIDto> getHistory(Long id, String email);

    Set<Long> getParticipants(String email);
}
