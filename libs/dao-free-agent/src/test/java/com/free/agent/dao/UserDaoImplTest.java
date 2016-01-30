package com.free.agent.dao;

import com.free.agent.Filter;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Message;
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

import static com.free.agent.model.Message_.*;
import static com.free.agent.model.Sport_.nameEn;
import static com.free.agent.model.Sport_.nameRu;
import static com.free.agent.model.User_.*;

/**
 * Created by antonPC on 15.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class UserDaoImplTest extends TestCase {
    @Autowired
    private SportDao sportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;
    private List<User> u;

    @Before
    public void init() {
        List<Sport> s = new EntityTemplate<>(new Sport())
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Basketball", "Баскетбол")
                .create();

        List<Message> m = new EntityTemplate<>(new Message()).onProperties(authorId, title, text)
                .values(1L, "Learning", "Hello, I am learning")
                .values(1L, "Play", "I like play")
                .create();

        u = new EntityTemplate<>(new User())
                .onProperties(email, password, phone, firstName, lastName, dateOfBirth)
                .values("l1", "p1", "11-22-33", "Anton", "Petrov", DateTime.parse("1991-04-03").toDate())
                .values("l2", "p2", "12-34-45", "Alenochka", "Mosenko", DateTime.parse("1992-04-03").toDate())
                .create();

        u.get(0).getSports().add(s.get(0));
        u.get(0).getSports().add(s.get(1));
        u.get(1).getSports().add(s.get(0));

        u.get(0).getMessages().add(m.get(0));
        u.get(0).getMessages().add(m.get(1));
        m.get(0).setUser(u.get(0));
        m.get(1).setUser(u.get(0));

        userDao.saveAll(u);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(2, userDao.findAll().size());
        assertEquals(2, sportDao.findAll().size());
        assertEquals(2, messageDao.findAll().size());
        assertEquals(2, userDao.findByEmail(u.get(0).getEmail()).getMessages().size());
        assertEquals(0, userDao.findByEmail(u.get(1).getEmail()).getMessages().size());
        assertEquals(2, userDao.findByEmail(u.get(0).getEmail()).getSports().size());
        assertEquals(1, userDao.findByEmail(u.get(1).getEmail()).getSports().size());

        AssertCollectionContains.with(userDao.findAll())
                .onProperties(email, password, phone, firstName, lastName, dateOfBirth)
                .values("l1", "p1", "11-22-33", "Anton", "Petrov", "1991-04-03")
                .values("l2", "p2", "12-34-45", "Alenochka", "Mosenko", "1992-04-03")
                .assertEquals();

        AssertCollectionContains.with(userDao.findByEmail(u.get(0).getEmail()).getMessages())
                .onProperties(title, text)
                .values("Learning", "Hello, I am learning")
                .values("Play", "I like play")
                .assertEquals();

        AssertCollectionContains.with(userDao.findByEmail(u.get(0).getEmail()).getSports())
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Basketball", "Баскетбол")
                .assertEquals();

        AssertCollectionContains.with(userDao.findByEmail(u.get(1).getEmail()).getSports())
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .assertEquals();

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
        filter.setDateOfBirthFrom(DateTime.parse("1992-04-02").toDate());
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Alenochka", userDao.findByFilter(filter).iterator().next().getFirstName());

        filter = new Filter();
        filter.setDateOfBirthTo(DateTime.parse("1992-04-02").toDate());
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Anton", userDao.findByFilter(filter).iterator().next().getFirstName());

        filter = new Filter();
        filter.setDateOfBirthFrom(DateTime.parse("1992-04-02").toDate());
        filter.setDateOfBirthTo(DateTime.parse("1992-04-04").toDate());
        assertEquals(1, userDao.findByFilter(filter).size());
        assertEquals("Alenochka", userDao.findByFilter(filter).iterator().next().getFirstName());
    }
}
