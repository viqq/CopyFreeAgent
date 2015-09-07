package com.free.agent.dao;

import com.free.agent.model.Message;

import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
public interface MessageDao extends GenericDao<Message, Long> {

    Set<Message> findAllByReceiver(String login);

    Set<Message> findAllByReceiverAndAuthor(String login, String author);

    Set<Message> findOlderThen(Date date);
}
