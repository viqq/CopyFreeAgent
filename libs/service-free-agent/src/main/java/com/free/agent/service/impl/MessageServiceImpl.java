package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.MessageDao;
import com.free.agent.dao.UserDao;
import com.free.agent.model.Message;
import com.free.agent.model.User;
import com.free.agent.service.MessageService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private final int HALF_YEAR_BEFORE = -6;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void removeOldMessages() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, HALF_YEAR_BEFORE);
        messageDao.delete(messageDao.findOlderThen(calendar.getTime()));
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByReceiver(String login) {
        return messageDao.findAllByReceiver(login);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByAuthor(String author) {
        return messageDao.findAllByAuthor(author);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByReceiverAndAuthor(String login, String author) {
        return messageDao.findAllByReceiverAndAuthor(login, author);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void save(Long id, Message message, String author) {
        message.setAuthor(author);
        message.setTimeOfCreate(new Date());
        User u = userDao.find(id);
        message.setUser(u);
        List<Message> list = u.getMessages();
        list.add(message);
        u.setMessages(list);
        messageDao.create(message);
        userDao.update(u);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void updateMessageStatus(Long id, String name) {
        Message message = messageDao.find(id);
        if (message.getUser().getLogin().equals(name)) {
            message.setTimeOfRead(new Date());
            messageDao.update(message);
        }
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public int countUnreadMessages(String name) {
        return messageDao.countUnreadMessages(name);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> getHistory(String name, Long id) {
        Set<Message> messages = Sets.newHashSet();
        messages.addAll(messageDao.findAllByReceiverAndAuthor(name, userDao.find(id).getLogin()));
        messages.addAll(messageDao.findAllByReceiverAndAuthor(userDao.find(id).getLogin(), name));
        return messageDao.getHistory(name, userDao.find(id).getLogin());
    }


}
