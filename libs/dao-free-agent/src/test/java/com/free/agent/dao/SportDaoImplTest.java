package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Sport;
import com.google.common.collect.Sets;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by antonPC on 27.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class SportDaoImplTest extends TestCase {
    @Autowired
    private SportDao sportDao;

    @Test
    public void findByName() throws Exception {
        assertEquals(0, sportDao.findAll().size());

        Sport s1 = sportDao.create(new Sport("Football"));
        Sport s2 = sportDao.create(new Sport("Basketball"));
        Sport s3 = sportDao.create(new Sport("Pref"));
        Sport s4 = sportDao.create(new Sport("Tennis"));

        assertEquals(4, sportDao.findAll().size());
        assertEquals(2, sportDao.findByNames(Sets.newHashSet("Football", "Pref")).size());
        assertContains(Sets.newHashSet(s1, s3), sportDao.findByNames(Sets.newHashSet("Football", "Pref")));
        assertEquals(1, sportDao.findByNames(Sets.newHashSet("Football", "Preferance")).size());
        assertContains(Sets.newHashSet(s1), sportDao.findByNames(Sets.newHashSet("Football", "Preferance")));
        sportDao.deleteAll();
        assertEquals(0, sportDao.findAll().size());
    }


    private void assertContains(Set<Sport> actual, Set<Sport> expected) {
        assertEquals(0, Sets.difference(actual, expected).size());
    }
}
