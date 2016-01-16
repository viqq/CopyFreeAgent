package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Skill;
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

import static com.free.agent.field.SkillLevel.*;

/**
 * Created by antonPC on 12.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class SkillDaoImplTest extends TestCase {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SkillDao skillDao;
    private Sport s1, s2;
    private User u1, u2;
    private Skill sk1, sk2, sk3;


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

        sk1 = new Skill(u1, s1, BEGINNER);
        u1.getSkills().add(sk1);
        sk2 = new Skill(u1, s2, PRO);
        u1.getSkills().add(sk2);
        sk3 = new Skill(u2, s1, MIDDLE);
        u2.getSkills().add(sk3);

        userDao.create(u1);
        userDao.create(u2);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(3, skillDao.findAll().size());
        assertEquals(2, userDao.find(u1.getId()).getSkills().size());
        assertEquals(1, userDao.find(u2.getId()).getSkills().size());
        assertEquals(MIDDLE, userDao.find(u2.getId()).getSkills().iterator().next().getSkillLevel());

    }
}
