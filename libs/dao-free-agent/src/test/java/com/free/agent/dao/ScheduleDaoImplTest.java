package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Schedule;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.GregorianCalendar;

/**
 * Created by antonPC on 05.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class ScheduleDaoImplTest extends TestCase {
    @Autowired
    private SportDao sportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ScheduleDao scheduleDao;

    private Sport s1, s2;
    private User u1, u2;
    private Schedule sch1, sch2;

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

        sch1 = new Schedule();
        sch1.setName("sch1");
        sch1.setUser(u1);
        sch1.setSport(s1);

        sch2 = new Schedule();
        sch2.setName("sch2");
        sch2.setUser(u1);
        sch2.setSport(s2);

        u1.getSchedules().add(sch1);
        u1.getSchedules().add(sch2);

        userDao.create(u1);
        userDao.create(u2);
        sportDao.create(s1);
        sportDao.create(s2);
        scheduleDao.create(sch1);
        scheduleDao.create(sch2);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(2, userDao.findAll().size());
        for (Schedule s : userDao.findByEmail(u1.getEmail()).getSchedules()) {
            assertTrue(s.getSport().getName().equals(s1.getName()) || s.getSport().getName().equals(s2.getName()));
        }
    }

    @Test
    public void findAllByUserId() {
        assertEquals(2, scheduleDao.findAllByUserId(u1.getId()).size());
        assertEquals(0, scheduleDao.findAllByUserId(u2.getId()).size());
    }
}
