package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.MessageDao;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.MessageDto;
import com.free.agent.dto.MessageUIDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.field.Role;
import com.free.agent.model.Message;
import com.free.agent.model.User;
import com.free.agent.service.MailService;
import com.free.agent.service.MessageService;
import com.free.agent.util.EncryptionUtils;
import com.free.agent.util.LinkUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by antonPC on 29.07.15.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = Logger.getLogger(MessageServiceImpl.class);
    private static final int HALF_YEAR_BEFORE = -6;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void removeOldMessages() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, HALF_YEAR_BEFORE);
        messageDao.delete(messageDao.findOlderThen(calendar.getTime()));
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<MessageUIDto> findAllByReceiver(String email) {
        return messageDao.findAllByReceiver(email).stream().map(input -> {
            MessageUIDto message = new MessageUIDto();
            message.setId(input.getId());
            message.setTimeOfRead(getTime(input.getTimeOfRead()));
            message.setTimeOfCreate(getTime(input.getTimeOfCreate()));
            message.setTitle(input.getTitle());
            message.setText(input.getText());
            message.setAuthorId(input.getAuthorId());
            return message;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByAuthor(String email) {
        User user = userDao.findByEmail(email);
        return messageDao.findAllByAuthorEmailAndId(user.getId());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Message> findAllByReceiverAndAuthor(Long id, Principal principal) {
        return messageDao.findAllByReceiverAndAuthor(id, userDao.findByEmail(principal.getName()).getId());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void save(MessageDto messageDto, String email, Principal principal) throws EmailAlreadyUsedException {
        Message message = new Message();
        message.setTitle(messageDto.getTitle());
        message.setText(messageDto.getText());

        if (principal == null) {
            if (isEmailFree(email)) {
                User newUser = postponeRegistration(email);
                message.setAuthorId(newUser.getId());
            } else {
                User user = userDao.findByEmail(email);
                if (user.getRole().equals(Role.ROLE_NOT_ACTIVATED)) {
                    message.setAuthorId(user.getId());
                } else {
                    LOGGER.error("User with " + email + " has registered already");
                    throw new EmailAlreadyUsedException("User with " + email + " has registered already");
                }
            }
        } else {
            message.setAuthorId(userDao.findByEmail(principal.getName()).getId());
        }

        message.setTimeOfCreate(DateTime.now().toDate());
        User u = userDao.find(messageDto.getId());
        message.setUser(u);
        List<Message> list = u.getMessages();
        list.add(message);
        u.setMessages(list);
        messageDao.create(message);
        userDao.update(u);
        sendEmailToUser(u, messageDto.getText());
    }

    private void sendEmailToUser(User u, String text) {
        switch (u.getRole()) {
            case ROLE_NOT_ACTIVATED: {
                mailService.sendMail(u.getEmail(), "New message in FA", "You have new message. " +
                        "Please finish registration " + LinkUtils.getLinkForRegistration(u.getEmail(), u.getHash(), true));
                break;
            }
            case ROLE_NOT_CONFIRMED: {
                //todo
                break;
            }
            default: {
                mailService.sendMail(u.getEmail(), "New message in FA", text);
                break;
            }
        }
    }

    private User postponeRegistration(String email) {
        User u = new User();
        u.setEmail(email);
        u.setRole(Role.ROLE_NOT_ACTIVATED);
        u.setDateOfRegistration(DateTime.now().toDate());
        u.setHash(EncryptionUtils.getRandomString());
        return userDao.create(u);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void updateMessageStatus(Long id, String email) {
        Message message = messageDao.find(id);
        if (message.getUser().getEmail().equals(email)) {
            message.setTimeOfRead(DateTime.now().toDate());
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
    public List<MessageUIDto> getHistory(Long id, String email) {
        User user = userDao.findByEmail(email);
        return messageDao.getHistory(id, user.getId()).stream().map(input -> {
            MessageUIDto message = new MessageUIDto();
            message.setId(input.getId());
            message.setTimeOfRead(getTime(input.getTimeOfRead()));
            message.setTimeOfCreate(getTime(input.getTimeOfCreate()));
            message.setTitle(input.getTitle());
            message.setText(input.getText());
            message.setAuthorId(input.getAuthorId());
            return message;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Set<Long> getParticipants(String email) {
        return messageDao.getParticipants(userDao.findByEmail(email).getId());
    }

    private boolean isEmailFree(String email) {
        return userDao.findByEmail(email) == null;
    }

    private static Long getTime(Date dateOfBirth) {
        return dateOfBirth == null ? null : dateOfBirth.getTime();
    }

}
