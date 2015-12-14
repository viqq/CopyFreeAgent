package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.User;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
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
import java.util.Set;

/**
 * Created by antonPC on 06.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class UserFavoriteDaoImplTest extends TestCase {

    @Autowired
    private UserDao userDao;

    private final Function<User, Long> getId = new Function<User, Long>() {
        @Override
        public Long apply(User input) {
            return input.getId();
        }
    };

    private User u1, u2, u3, u4, u5;
    private Set<User> set1, set3, set5;

    @Before
    public void init() {
        u1 = new User("l1", "p1", "11-22-33");
        u1.setFirstName("Anton");
        u1.setLastName("Petrov");
        u1.setDateOfBirth(new GregorianCalendar(1991, 4, 3).getTime());

        u2 = new User("l2", "p2", "12-34-45");
        u2.setFirstName("Alenochka");
        u2.setLastName("Mosenko");
        u2.setDateOfBirth(new GregorianCalendar(1992, 4, 3).getTime());

        u3 = new User("l3", "p3", "112-224-353");
        u3.setFirstName("Yana");
        u3.setLastName("Zemlina");
        u3.setDateOfBirth(new GregorianCalendar(1993, 4, 3).getTime());

        u4 = new User("l4", "p4", "129-394-495");
        u4.setFirstName("Yulia");
        u4.setLastName("Taburetkina");
        u4.setDateOfBirth(new GregorianCalendar(1994, 4, 3).getTime());

        u5 = new User("l5", "p5", "111-222-333");
        u5.setFirstName("Katia");
        u5.setLastName("Ribina");
        u5.setDateOfBirth(new GregorianCalendar(1995, 4, 3).getTime());

        set1 = Sets.newHashSet(u2, u3, u5);
        u1.setFavorites(set1);
        set3 = Sets.newHashSet(u2, u4, u5, u1);
        u3.setFavorites(set3);
        set5 = Sets.newHashSet(u1);
        u5.setFavorites(set5);

        createAllUsers(u1, u2, u3, u4, u5);
    }

    private void createAllUsers(User... user) {
        for (User u : user) {
            userDao.create(u);
        }
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(5, userDao.findAll().size());
        assertEquals(3, userDao.findByEmail(u1.getEmail()).getFavorites().size());
        assertEquals(4, userDao.findByEmail(u3.getEmail()).getFavorites().size());
        assertEquals(1, userDao.findByEmail(u5.getEmail()).getFavorites().size());
        assertEquals(0, userDao.findByEmail(u2.getEmail()).getFavorites().size());
        assertSetEquals(userDao.find(u1.getId()).getFavorites(), set1);
        assertSetEquals(userDao.find(u3.getId()).getFavorites(), set3);
        assertSetEquals(userDao.find(u5.getId()).getFavorites(), set5);
    }

    @Test
    public void findAllByUserId() {
        assertSetEquals(userDao.findFavoritesByUserId(u1.getId()), set1);
        assertSetEquals(userDao.findFavoritesByUserId(u3.getId()), set3);
        assertSetEquals(userDao.findFavoritesByUserId(u5.getId()), set5);

    }

    private void assertSetEquals(Set<User> favorites, Set<User> user) {
        Set<Long> favoritesIds = FluentIterable.from(favorites).transform(getId).toSet();
        Set<Long> userIds = FluentIterable.from(user).transform(getId).toSet();
        assertEquals(0, Sets.difference(favoritesIds, userIds).size());
    }

}
