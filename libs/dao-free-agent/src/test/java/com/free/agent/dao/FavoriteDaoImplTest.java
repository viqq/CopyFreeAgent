package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Favorite;
import com.free.agent.model.User;
import com.google.common.collect.Lists;
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
import java.util.List;

/**
 * Created by antonPC on 06.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class FavoriteDaoImplTest extends TestCase {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FavoriteDao favoriteDao;

    private User u1, u2, u3, u4, u5;
    private Favorite f12, f13, f15, f32, f34, f35, f31, f51;

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

        f12 = new Favorite(u1, u2, "best friend1");
        f13 = new Favorite(u1, u3, "best friend2");
        f15 = new Favorite(u1, u5, "best friend3");
        u1.setFavorites(Lists.newArrayList(f12, f13, f15));

        f32 = new Favorite(u3, u2, "best friend11");
        f34 = new Favorite(u3, u4, "best friend12");
        f35 = new Favorite(u3, u5, "best friend13");
        f31 = new Favorite(u3, u1, "best friend13");
        u3.setFavorites(Lists.newArrayList(f31, f32, f34, f35));

        f51 = new Favorite(u5, u1, "best friend23");
        u5.setFavorites(Lists.newArrayList(f51));

        createAllUsers(u1, u2, u3, u4, u5);
        createAllFavorites(f12, f13, f15, f32, f34, f35, f31, f51);
    }

    private void createAllFavorites(Favorite... favorite) {
        for (Favorite f : favorite) {
            favoriteDao.create(f);
        }
    }

    private void createAllUsers(User... user) {
        for (User u : user) {
            userDao.create(u);
        }
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(8, favoriteDao.findAll().size());
        assertEquals(5, userDao.findAll().size());
        assertEquals(3, userDao.findByEmail(u1.getEmail()).getFavorites().size());
        assertTrue(userDao.findByEmail(u1.getEmail()).getFavorites().contains(f12));
        assertTrue(userDao.findByEmail(u1.getEmail()).getFavorites().contains(f13));
        assertTrue(userDao.findByEmail(u1.getEmail()).getFavorites().contains(f15));
        assertEquals(4, userDao.findByEmail(u3.getEmail()).getFavorites().size());
        assertEquals(1, userDao.findByEmail(u5.getEmail()).getFavorites().size());
        assertEquals(u1, userDao.findByEmail(u5.getEmail()).getFavorites().iterator().next().getFavoriteUser());
        assertEquals(0, userDao.findByEmail(u2.getEmail()).getFavorites().size());
    }

    @Test
    public void findAllByUserId() {
        assertEquals(u1, favoriteDao.findAllByUserId(u5.getId()).iterator().next().getFavoriteUser());
        assertEquals(u5, favoriteDao.findAllByUserId(u5.getId()).iterator().next().getUser());
        List<User> favorites = Lists.newArrayList(u2, u3, u5);
        for (Favorite favorite : favoriteDao.findAllByUserId(u1.getId())) {
            assertTrue(favorites.contains(favorite.getFavoriteUser()));
        }
    }

    @Test
    public void findAllByUserAndFollower() {
        assertEquals(f51, favoriteDao.findAllByUserAndFollower(u5.getId(), u1.getId()));
        assertEquals(f12, favoriteDao.findAllByUserAndFollower(u1.getId(), u2.getId()));
        assertEquals(f13, favoriteDao.findAllByUserAndFollower(u1.getId(), u3.getId()));
        assertEquals(f15, favoriteDao.findAllByUserAndFollower(u1.getId(), u5.getId()));
    }

}
