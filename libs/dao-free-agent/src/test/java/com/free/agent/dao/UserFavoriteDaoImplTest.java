package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.User;
import com.free.agent.utils.AssertCollectionContains;
import com.free.agent.utils.EntityTemplate;
import com.google.common.collect.Sets;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.free.agent.model.User_.*;

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
    private Set<User> set1, set3, set5;
    private List<User> u;

    @Before
    public void init() {
        u = new EntityTemplate<>(new User())
                .onProperties(email, password, phone, firstName, lastName, dateOfBirth)
                .values("l1", "p1", "11-22-33", "Anton", "Petrov", DateTime.parse("1991-04-03").toDate())
                .values("l2", "p2", "12-34-45", "Alenochka", "Mosenko", DateTime.parse("1992-04-03").toDate())
                .values("l3", "p3", "112-224-353", "Yana", "Zemlina", DateTime.parse("1993-05-02").toDate())
                .values("l4", "p4", "129-394-495", "Yulia", "Taburetkina", DateTime.parse("1994-04-03").toDate())
                .values("l5", "p5", "111-222-333", "Katia", "Ribina", DateTime.parse("1994-04-03").toDate())
                .create();

        set1 = Sets.newHashSet(u.get(1), u.get(2), u.get(4));
        u.get(0).setFavorites(set1);
        set3 = Sets.newHashSet(u.get(1), u.get(3), u.get(4), u.get(0));
        u.get(2).setFavorites(set3);
        set5 = Sets.newHashSet(u.get(0));
        u.get(4).setFavorites(set5);

        userDao.saveAll(u);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(5, userDao.findAll().size());
        assertEquals(3, userDao.findByEmail(u.get(0).getEmail()).getFavorites().size());
        assertEquals(4, userDao.findByEmail(u.get(2).getEmail()).getFavorites().size());
        assertEquals(1, userDao.findByEmail(u.get(4).getEmail()).getFavorites().size());
        assertEquals(0, userDao.findByEmail(u.get(1).getEmail()).getFavorites().size());

        AssertCollectionContains.with(userDao.findByEmail(u.get(0).getEmail()).getFavorites())
                .onProperties(firstName, lastName)
                .values("Alenochka", "Mosenko")
                .values("Yana", "Zemlina")
                .values("Katia", "Ribina")
                .assertEquals();

        AssertCollectionContains.with(userDao.findByEmail(u.get(2).getEmail()).getFavorites())
                .onProperties(firstName, lastName)
                .values("Anton", "Petrov")
                .values("Alenochka", "Mosenko")
                .values("Yulia", "Taburetkina")
                .values("Katia", "Ribina")
                .assertEquals();

        AssertCollectionContains.with(userDao.findByEmail(u.get(4).getEmail()).getFavorites())
                .onProperties(firstName, lastName)
                .values("Anton", "Petrov")
                .assertEquals();

        assertSetEquals(userDao.find(u.get(0).getId()).getFavorites(), set1);
        assertSetEquals(userDao.find(u.get(2).getId()).getFavorites(), set3);
        assertSetEquals(userDao.find(u.get(4).getId()).getFavorites(), set5);
    }

    @Test
    public void findAllByUserId() {
        assertSetEquals(userDao.findFavoritesByUserId(u.get(0).getId()), set1);
        assertSetEquals(userDao.findFavoritesByUserId(u.get(2).getId()), set3);
        assertSetEquals(userDao.findFavoritesByUserId(u.get(4).getId()), set5);
    }

    private void assertSetEquals(Set<User> favorites, Set<User> user) {
        Set<Long> favoritesIds = favorites.stream().map(User::getId).collect(Collectors.toSet());
        Set<Long> userIds = user.stream().map(User::getId).collect(Collectors.toSet());
        assertEquals(0, Sets.difference(favoritesIds, userIds).size());
    }

}
