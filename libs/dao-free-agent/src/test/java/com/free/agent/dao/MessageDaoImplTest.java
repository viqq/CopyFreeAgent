package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Message;
import com.free.agent.model.User;
import com.free.agent.utils.AssertCollectionContains;
import com.free.agent.utils.EntityTemplate;
import com.google.common.collect.Sets;
import junit.framework.TestCase;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.free.agent.model.Message_.*;
import static com.free.agent.model.User_.*;

/**
 * Created by antonPC on 29.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class MessageDaoImplTest extends TestCase {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    private List<Message> m;
    private List<User> u;

    @Before
    public void init() {
        m = new EntityTemplate<>(new Message())
                .onProperties(authorId, title, text, timeOfCreate, timeOfRead)
                .values(1L, "Learning", "Hello, I am learning", DateTime.parse("2000-01-01").toDate(), DateTime.now().toDate())
                .values(2L, "Play", "I like play", DateTime.parse("2000-01-02").toDate(), null)
                .values(2L, "Play", "I like play again", DateTime.parse("2000-01-03").toDate(), null)
                .create();

        u = new EntityTemplate<>(new User())
                .onProperties(email, password, phone, firstName, lastName, dateOfBirth)
                .values("l1", "p1", "11-22-33", "Anton", "Petrov", DateTime.parse("1991-04-03").toDate())
                .values("l2", "p2", "12-34-45", "Alenochka", "Mosenko", DateTime.parse("1992-04-03").toDate())
                .create();

        u.get(0).getMessages().add(m.get(0));
        u.get(0).getMessages().add(m.get(1));
        u.get(0).getMessages().add(m.get(2));
        m.get(0).setUser(u.get(0));
        m.get(1).setUser(u.get(0));
        m.get(2).setUser(u.get(0));

        userDao.saveAll(u);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        messageDao.deleteAll();
        assertEquals(0, messageDao.findAll().size());
        messageDao.create(new EntityTemplate<>(new Message()).onProperties(authorId, title, text)
                .createOne(5L, "Title", "Text"));
        assertEquals(1, messageDao.findAll().size());
        Message m = messageDao.findAll().get(0);
        m.setText("Text2");
        messageDao.update(m);
        assertEquals("Text2", messageDao.findAll().get(0).getText());
        messageDao.deleteAll();
        assertEquals(0, messageDao.findAll().size());
    }

    @Test
    public void findAllByReceiver() {
        assertEquals(3, messageDao.findAllByReceiver(u.get(0).getEmail()).size());
        assertEquals(0, messageDao.findAllByReceiver(u.get(1).getEmail()).size());

        AssertCollectionContains.with(messageDao.findAllByReceiver(u.get(0).getEmail()))
                .onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .values(2L, "Play", "I like play")
                .values(2L, "Play", "I like play again")
                .assertEquals();
    }

    @Test
    public void findAllByAuthor() {
        assertEquals(1, messageDao.findAllByAuthorEmailAndId(1L).size());
        assertEquals(2, messageDao.findAllByAuthorEmailAndId(2L).size());

        AssertCollectionContains.with(messageDao.findAllByAuthorEmailAndId(1L))
                .onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .assertEquals();

        AssertCollectionContains.with(messageDao.findAllByAuthorEmailAndId(2L))
                .onProperties(authorId, title, text)
                .values(2L, "Play", "I like play")
                .values(2L, "Play", "I like play again")
                .assertEquals();
    }

    @Test
    public void findAllByReceiverAndAuthor() {
        assertEquals(1, messageDao.findAllByReceiverAndAuthor(u.get(0).getId(), 1L).size());
        assertEquals(2, messageDao.findAllByReceiverAndAuthor(u.get(0).getId(), 2L).size());
        assertEquals(0, messageDao.findAllByReceiverAndAuthor(u.get(1).getId(), 1L).size());

        AssertCollectionContains.with(messageDao.findAllByReceiverAndAuthor(u.get(0).getId(), 1L))
                .onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .assertEquals();

        AssertCollectionContains.with(messageDao.findAllByReceiverAndAuthor(u.get(0).getId(), 2L))
                .onProperties(authorId, title, text)
                .values(2L, "Play", "I like play")
                .values(2L, "Play", "I like play again")
                .assertEquals();
    }

    @Test
    public void findOlderThen() {
        assertEquals(3, messageDao.findOlderThen(DateTime.parse("2000-01-04").toDate()).size());
        assertEquals(3, messageDao.findOlderThen(DateTime.parse("2000-01-03").toDate()).size());
        assertEquals(2, messageDao.findOlderThen(DateTime.parse("2000-01-02").toDate()).size());
        assertEquals(1, messageDao.findOlderThen(DateTime.parse("2000-01-01").toDate()).size());
        assertEquals(0, messageDao.findOlderThen(DateTime.parse("1999-12-12").toDate()).size());

        AssertCollectionContains.with(messageDao.findOlderThen(DateTime.parse("2000-01-03").toDate()))
                .onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .values(2L, "Play", "I like play")
                .values(2L, "Play", "I like play again")
                .assertEquals();

        AssertCollectionContains.with(messageDao.findOlderThen(DateTime.parse("2000-01-02").toDate()))
                .onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .values(2L, "Play", "I like play")
                .assertEquals();

        AssertCollectionContains.with(messageDao.findOlderThen(DateTime.parse("2000-01-01").toDate()))
                .onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .assertEquals();
    }

    @Test
    public void getParticipants() {
        assertEquals(2, messageDao.getParticipants(u.get(0).getId()).size());
        assertEquals(0, messageDao.getParticipants(u.get(1).getId()).size());
        assertContainsMessage(Sets.newHashSet(m.get(0).getAuthorId(), m.get(1).getAuthorId(), m.get(2).getAuthorId()), messageDao.getParticipants(u.get(0).getId()));
    }

    @Test
    public void countUnreadMessages() {
        assertEquals(2, messageDao.countUnreadMessages(u.get(0).getEmail()));
        assertEquals(0, messageDao.countUnreadMessages(u.get(1).getEmail()));
    }

    private void assertContainsMessage(Set<Long> users, Set<Long> findParticipants) {
        for (Long participant : findParticipants) {
            assertTrue(users.contains(participant));
        }
    }

}
