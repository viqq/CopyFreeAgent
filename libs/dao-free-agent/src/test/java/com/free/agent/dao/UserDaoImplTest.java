package com.free.agent.dao;

import com.free.agent.dao.mock.SportDaoUtil;
import com.free.agent.dao.mock.UserDaoUtil;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonPC on 15.06.15.
 */
@Ignore("id is not generated automaticly")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao.xml"})
@Transactional(value = "transactionManagerTest")
public class UserDaoImplTest extends TestCase {
    @Autowired
    private SportDaoUtil sportDao;
    @Autowired
    private UserDaoUtil userDao;

    @Test
    public void createReadUpdateDeleteTest() throws Exception {
        assertEquals(0, sportDao.getAll().size());
        assertEquals(0, userDao.getAll().size());

        Sport s1 = new Sport("Football");
        Sport s2 = new Sport("Basketball");

        User u1 = new User("l1","p1");
        User u2 = new User("l2","p2");

        u1.getSports().add(s1);
        u1.getSports().add(s2);
        u2.getSports().add(s1);

        userDao.getEntityManager().merge(u1);
        userDao.getEntityManager().merge(u2);

        assertEquals(2, userDao.getAll().size());
        assertEquals(2, userDao.read(u1.getId()).getSports().size());
        assertEquals(1, userDao.read(u2.getId()).getSports().size());
        assertTrue(userDao.read(u1.getId()).getSports().contains(s1));
        assertTrue(userDao.read(u1.getId()).getSports().contains(s2));
        assertTrue(userDao.read(u2.getId()).getSports().contains(s1));

        userDao.delete(u1);
        userDao.delete(u2);
        assertEquals(0, userDao.getAll().size());
    }
}
