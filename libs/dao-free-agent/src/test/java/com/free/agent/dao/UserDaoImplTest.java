package com.free.agent.dao;

import com.free.agent.Filter;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.mock.SportDaoMock;
import com.free.agent.dao.mock.UserDaoMock;
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
    @Autowired
    private SportDaoMock sportDao;
    @Autowired
    private UserDaoMock userDao;

    @Before
    public void init() {
        s1 = new Sport("Football");
        s2 = new Sport("Basketball");

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

        userDao.create(u1);
        userDao.create(u2);
        sportDao.create(s1);
        sportDao.create(s2);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(2, userDao.findAll().size());
        assertEquals(2, sportDao.findAll().size());
        assertEquals(2, userDao.findByLogin(u1.getLogin()).getSports().size());
        assertEquals(1, userDao.findByLogin(u2.getLogin()).getSports().size());
        assertContains(userDao.findByLogin(u1.getLogin()).getSports(), Lists.newArrayList(s1.getName(), s2.getName()));
        assertContains(userDao.findByLogin(u2.getLogin()).getSports(), Lists.newArrayList(s1.getName()));
        userDao.removeAll();
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

    private void assertContains(Set<Sport> usersSports, ArrayList<String> sports) {
        for (Sport usersSport : usersSports) {
            assertTrue(sports.contains(usersSport.getName()));
        }
    }
}
