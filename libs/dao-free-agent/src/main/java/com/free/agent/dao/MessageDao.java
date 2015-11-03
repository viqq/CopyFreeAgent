package com.free.agent.dao;

import com.free.agent.model.Message;

import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageDao extends GenericDao<Message, Long> {

    Set<Message> findAllByReceiver(String email);

    Set<Message> findAllByReceiverAndAuthor(Long id, Long authorId);

    Set<Message> findOlderThen(Date date);

    int countUnreadMessages(String email);

    Set<Message> findAllByAuthorEmailAndId(Long authorId);

    Set<Message> findAllByAuthorId(Long authorId);

    Set<Message> getHistory(Long userId, Long authorId);

    Set<Long> getParticipants(Long id);
}
