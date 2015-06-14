package com.prauf.dao;

import com.prauf.model.Film;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonPC on 12.06.15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao.xml"})
@Transactional(value = "transactionManagerTest")
public class FilmDaoImplTest extends TestCase {

@Autowired
FilmDaoUtil dao;



    @Test
    public void createReadUpdateDeleteTest() throws Exception {
        Thread.sleep(1000);
        Assert.assertEquals(0, 0);
        Assert.assertEquals(0, dao.getAll().size());
        Film film = dao.getEntityManager().merge(new Film(20, "1", "1"));
        dao.getEntityManager().flush();
        Assert.assertEquals(1, dao.getAll().size());
        film.setDescription("desc");
        dao.update(film);
        Assert.assertEquals("desc", dao.read(film.getId()).getDescription());
        dao.delete(film);
       Assert.assertEquals(0, dao.getAll().size());
    }


    }

