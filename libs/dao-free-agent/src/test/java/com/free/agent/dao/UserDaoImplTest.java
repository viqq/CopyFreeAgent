package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.mock.SportDaoMock;
import com.free.agent.dao.mock.UserDaoMock;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by antonPC on 15.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER_TEST)
public class UserDaoImplTest extends TestCase {

    @Autowired
    private SportDaoMock sportDao;
    @Autowired
    private UserDaoMock userDao;

    @Test
    public void createReadUpdateDeleteTest() throws Exception {
        assertEquals(0, sportDao.findAll().size());
        assertEquals(0, userDao.findAll().size());

        Sport s1 = new Sport("Football");
        Sport s2 = new Sport("Basketball");

        User u1 = new User("l1", "p1");
        User u2 = new User("l2", "p2");

        u1.getSports().add(s1);
        u1.getSports().add(s2);
        u2.getSports().add(s1);

        userDao.getEntityManager().merge(u1);
        userDao.getEntityManager().merge(u2);

        assertEquals(2, userDao.findAll().size());
        assertEquals(2, userDao.findByLogin(u1.getLogin()).getSports().size());
        assertEquals(1, userDao.findByLogin(u2.getLogin()).getSports().size());
        assertContains(userDao.findByLogin(u1.getLogin()).getSports(), Lists.newArrayList(s1.getName(), s2.getName()));
        assertContains(userDao.findByLogin(u2.getLogin()).getSports(), Lists.newArrayList(s1.getName()));
        userDao.removeAll();
        assertEquals(0, userDao.findAll().size());
    }

    private void assertContains(Set<Sport> usersSports, ArrayList<String> sports) {
        for (Sport usersSport : usersSports) {
            assertTrue(sports.contains(usersSport.getName()));
        }
    }
}
