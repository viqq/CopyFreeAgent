package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Skill;
import com.free.agent.model.Skill_;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.utils.AssertCollectionContains;
import com.free.agent.utils.EntityTemplate;
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

import static com.free.agent.field.SkillLevel.*;
import static com.free.agent.model.Sport_.nameEn;
import static com.free.agent.model.Sport_.nameRu;
import static com.free.agent.model.User_.*;

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
    private List<User> u;

    @Before
    public void init() {
        List<Sport> s = new EntityTemplate<>(new Sport())
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Basketball", "Баскетбол")
                .create();

        u = new EntityTemplate<>(new User())
                .onProperties(email, password, phone, firstName, lastName, dateOfBirth)
                .values("l1", "p1", "11-22-33", "Anton", "Petrov", DateTime.parse("1991-04-03").toDate())
                .values("l2", "p2", "12-34-45", "Alenochka", "Mosenko", DateTime.parse("1992-04-03").toDate())
                .create();

        u.get(0).getSports().add(s.get(0));
        u.get(0).getSports().add(s.get(1));
        u.get(1).getSports().add(s.get(0));

        List<Skill> sk = new EntityTemplate<>(new Skill())
                .onProperties(Skill_.user, Skill_.sport, Skill_.skillLevel)
                .values(u.get(0), s.get(0), BEGINNER)
                .values(u.get(0), s.get(1), PRO)
                .values(u.get(1), s.get(0), MIDDLE)
                .create();

        u.get(0).getSkills().add(sk.get(0));
        u.get(0).getSkills().add(sk.get(1));
        u.get(1).getSkills().add(sk.get(2));

        userDao.saveAll(u);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(3, skillDao.findAll().size());
        assertEquals(2, userDao.find(u.get(0).getId()).getSkills().size());
        assertEquals(1, userDao.find(u.get(1).getId()).getSkills().size());

        AssertCollectionContains.with(skillDao.findAll())
                .onProperties("user.firstName", "sport.nameEn", "sport.nameRu", "skillLevel")
                .values("Anton", "Football", "Футбол", BEGINNER)
                .values("Anton", "Basketball", "Баскетбол", PRO)
                .values("Alenochka", "Football", "Футбол", MIDDLE)
                .assertEquals();

        AssertCollectionContains.with(userDao.find(u.get(0).getId()).getSkills())
                .onProperties("user.firstName", "sport.nameEn", "sport.nameRu", "skillLevel")
                .values("Anton", "Football", "Футбол", BEGINNER)
                .values("Anton", "Basketball", "Баскетбол", PRO)
                .assertEquals();

        AssertCollectionContains.with(userDao.find(u.get(1).getId()).getSkills())
                .onProperties("user.firstName", "sport.nameEn", "sport.nameRu", "skillLevel")
                .values("Alenochka", "Football", "Футбол", MIDDLE)
                .assertEquals();
    }
}
