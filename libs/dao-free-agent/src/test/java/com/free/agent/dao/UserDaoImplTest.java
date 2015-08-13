package com.free.agent.dao;

import com.free.agent.Filter;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.mock.MessageDaoMock;
import com.free.agent.dao.mock.SportDaoMock;
import com.free.agent.dao.mock.UserDaoMock;
import com.free.agent.model.Message;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER_TEST)
public class UserDaoImplTest extends TestCase {
    private Sport s1, s2;
    private User u1, u2;
    private Message m1, m2;
    @Autowired
    private SportDaoMock sportDao;
    @Autowired
    private UserDaoMock userDao;
    @Autowired
    private MessageDaoMock messageDao;


    @Before
    public void init() {
        s1 = new Sport("Football");
        s2 = new Sport("Basketball");

        m1 = new Message("Vania", "Learning", "Hello, I am learning");
        m2 = new Message("Karina", "Play", "I like play");

        u1 = new User("l1", "p1", "11-22-33");
        u1.setFirstName("Anton");
        u1.setLastName("Petrov");
        u1.setDateOfBirth(new GregorianCalendar(1991, 4, 3).getTime());
        u2 = new User("l2", "p2", "12-34-45");
        u2.setFirstName("Alenochka");
        u2.setLastName("Mosenko");
        u2.setDateOfBirth(new GregorianCalendar(1992, 4, 3).getTime());

        u1.getSports().add(s1);
        u1.getSports().add(s2);
        u2.getSports().add(s1);

        u1.getMessages().add(m1);
        u1.getMessages().add(m2);
        m1.setUser(u1);
        m2.setUser(u1);

        userDao.create(u1);
        userDao.create(u2);
        sportDao.create(s1);
        sportDao.create(s2);

        messageDao.create(m1);
        messageDao.create(m2);


    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(2, userDao.findAll().size());
        assertEquals(2, sportDao.findAll().size());
        assertEquals(2, messageDao.findAll().size());
        assertEquals(2, userDao.findByLogin(u1.getLogin()).getMessages().size());
        assertEquals(0, userDao.findByLogin(u2.getLogin()).getMessages().size());
        assertEquals(2, userDao.findByLogin(u1.getLogin()).getSports().size());
        assertEquals(1, userDao.findByLogin(u2.getLogin()).getSports().size());
        assertContainsMessage(userDao.findByLogin(u1.getLogin()).getMessages(), Lists.newArrayList(m1.getText(), m2.getText()));
        assertContainsSport(userDao.findByLogin(u1.getLogin()).getSports(), Lists.newArrayList(s1.getName(), s2.getName()));
        assertContainsSport(userDao.findByLogin(u2.getLogin()).getSports(), Lists.newArrayList(s1.getName()));
        userDao.deleteAll();
        assertEquals(0, userDao.findAll().size());
    }

    @Test
    public void findByFilter() {
        assertEquals(2, userDao.findAll().size());

        Filter filter = new Filter();
        assertEquals(2, userDao.findByFilter(filter).size());

        filter.setFirstName("Anton");
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Petrov", userDao.findByFilter(filter).iterator().next().getLastName());

        filter = new Filter();
        filter.setFirstName("Anton");
        filter.setLastName("Mosenko");
        assertEquals(0, userDao.findByFilter(filter).size());

        filter = new Filter();
        filter.setSport("Football");
        assertEquals(2, userDao.findByFilter(filter).size());

        filter = new Filter();
        filter.setSport("Basketball");
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Petrov", userDao.findByFilter(filter).iterator().next().getLastName());

        filter = new Filter();
        filter.setDateOfBirthFrom(new GregorianCalendar(1992, 4, 2).getTime());
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Alenochka", userDao.findByFilter(filter).iterator().next().getFirstName());

        filter = new Filter();
        filter.setDateOfBirthTo(new GregorianCalendar(1992, 4, 2).getTime());
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Anton", userDao.findByFilter(filter).iterator().next().getFirstName());

        filter = new Filter();
        filter.setDateOfBirthFrom(new GregorianCalendar(1992, 4, 2).getTime());
        filter.setDateOfBirthTo(new GregorianCalendar(1992, 4, 4).getTime());
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Alenochka", userDao.findByFilter(filter).iterator().next().getFirstName());
    }

    private void assertContainsSport(Set<Sport> usersSports, ArrayList<String> sports) {
        for (Sport usersSport : usersSports) {
            assertTrue(sports.contains(usersSport.getName()));
        }
    }

    private void assertContainsMessage(List<Message> usersMessages, ArrayList<String> messages) {
        for (Message usersMessage : usersMessages) {
            assertTrue(messages.contains(usersMessage.getText()));
        }
    }
}
