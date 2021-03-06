package com.free.agent.dao;

import com.free.agent.dao.dto.Participant;
import com.free.agent.model.Message;

import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageDao extends GenericDao<Message, Long> {

    Set<Message> findAllByReceiver(String email);

    Set<Message> findAllByReceiverAndAuthor(Long id, String authorEmail, Long authorId);

    Set<Message> findOlderThen(Date date);

    int countUnreadMessages(String email);

    Set<Message> findAllByAuthorEmailAndId(String authorEmail, Long id);

    Set<Message> findAllByAuthorId(Long authorId);

    Set<Message> getHistory(Long userId, Long authorId, String authorEmail);

    Set<Participant> getParticipants(Long id);
}
