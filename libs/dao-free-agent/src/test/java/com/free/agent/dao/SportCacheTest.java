package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.mock.SportDaoMock;
import com.free.agent.model.Sport;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonPC on 26.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER_TEST)
public class SportCacheTest extends TestCase {
    @Autowired
    private SportDaoMock sportDao;

    @Test
    public void cacheTest() {
        for (int i = 0; i < 1000; i++) {
            sportDao.create(new Sport(String.valueOf(i)));
        }

        long start = System.nanoTime();
        sportDao.findAll();
        long end = System.nanoTime();
        long totalTimeWithoutCache = end - start;
        System.out.println("Search without cache: " + totalTimeWithoutCache);

        start = System.nanoTime();
        sportDao.findAll();
        end = System.nanoTime();
        long totalTimeWithCache = end - start;
        System.out.println("Search from cache: " + totalTimeWithCache);

        Assert.assertTrue(totalTimeWithoutCache > totalTimeWithCache);
    }
}
