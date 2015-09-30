package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.MessageDao;
import com.free.agent.dao.UserDao;
import com.free.agent.dao.dto.Participant;
import com.free.agent.model.Message;
import com.free.agent.model.User;
import com.free.agent.service.MessageService;
import com.free.agent.service.dto.MessageDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = Logger.getLogger(MessageServiceImpl.class);
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
    public Set<Message> findAllByReceiver(String email) {
        return messageDao.findAllByReceiver(email);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByAuthor(String email) {
        User user = userDao.findByEmail(email);
        return messageDao.findAllByAuthorEmailAndId(user.getEmail(), user.getId());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByReceiverAndAuthor(Long id, String email, Principal principal) {
        return messageDao.findAllByReceiverAndAuthor(id, email, userDao.findByEmail(principal.getName()).getId());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void save(MessageDto messageDto, String email, Principal principal) throws IllegalAccessException {
        Message message = new Message(messageDto.getTitle(), messageDto.getText());
        if (principal == null) {
            if (isEmailFree(email)) {
                message.setAuthorEmail(email);
            } else {
                LOGGER.error("User with " + email + " has registered already");
                throw new IllegalAccessException("User with " + email + " has registered already");
            }
        } else {
            message.setAuthorId(userDao.findByEmail(principal.getName()).getId());
        }
        message.setTimeOfCreate(new Date());
        //unchecked
        User u = userDao.find(messageDto.getId());
        message.setUser(u);
        List<Message> list = u.getMessages();
        list.add(message);
        u.setMessages(list);
        messageDao.create(message);
        userDao.update(u);
    }

    private boolean isEmailFree(String email) {
        return userDao.findByEmail(email) == null;
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void updateMessageStatus(Long id, String email) {
        Message message = messageDao.find(id);
        if (message.getUser().getEmail().equals(email)) {
            message.setTimeOfRead(new Date());
            messageDao.update(message);
        }
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public int countUnreadMessages(String id) {
        return messageDao.countUnreadMessages(id);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> getHistory(Long id, String email) {
        User user = userDao.findByEmail(email);
        return messageDao.getHistory(id, user.getId(), user.getEmail());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Participant> getParticipants(String email) {
        return messageDao.getParticipants(userDao.findByEmail(email).getId());
    }


}
