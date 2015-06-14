package com.prauf.service;

import com.prauf.dao.FilmDao;
import com.prauf.model.Film;
import com.prauf.service.impl.FilmServiceImpl;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


/**
 * Created by antonPC on 13.06.15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao.xml"})
@Transactional(value = "transactionManager")
public class FilmServiceImplTest extends TestCase{

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private FilmServiceImpl service;

    @Mock
    private FilmDao dao;

    @Test
    public void getFilmTest(){
        Mockito.when(dao.read(1)).thenReturn(film(1));
        Film film = service.getFilm(1);
        Assert.assertEquals(film.getDescription(),"desc");
        Assert.assertEquals(film.getName(), "name");
    }

    private Film film(Integer l) {
        return new Film(l,"name","desc");
    }
}
