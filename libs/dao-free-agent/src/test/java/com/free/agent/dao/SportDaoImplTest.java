package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.model.Sport;
import com.free.agent.utils.AssertCollectionContains;
import com.free.agent.utils.EntityTemplate;
import com.google.common.collect.Lists;
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

import java.util.List;

import static com.free.agent.field.Language.ENG;
import static com.free.agent.field.Language.RU;
import static com.free.agent.model.Sport_.nameEn;
import static com.free.agent.model.Sport_.nameRu;

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
    private List<Sport> s;

    @Before
    public void init() {
        s = new EntityTemplate<>(new Sport())
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Basketball", "Баскетбол")
                .values("Pref", "Преферанс")
                .values("Tennis", "Теннис")
                .create();

        sportDao.saveAll(s);
    }

    @Test
    public void findByName() {
        assertEquals(4, sportDao.findAll().size());
        assertEquals(2, sportDao.findByNames(Sets.newHashSet("Football", "Pref"), ENG).size());
        assertEquals(2, sportDao.findByNames(Sets.newHashSet("Футбол", "Преферанс"), RU).size());

        AssertCollectionContains.with(sportDao.findByNames(Sets.newHashSet("Football", "Pref"), ENG))
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Pref", "Преферанс")
                .assertEquals();

        AssertCollectionContains.with(sportDao.findByNames(Sets.newHashSet("Футбол", "Преферанс"), RU))
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Pref", "Преферанс")
                .assertEquals();

        AssertCollectionContains.with(Lists.newArrayList(sportDao.findByName("Tennis", ENG)))
                .onProperties(nameEn, nameRu)
                .values("Tennis", "Теннис")
                .assertEquals();

        AssertCollectionContains.with(Lists.newArrayList(sportDao.findByName("Баскетбол", RU)))
                .onProperties(nameEn, nameRu)
                .values("Basketball", "Баскетбол")
                .assertEquals();

        sportDao.deleteAll();
        assertEquals(0, sportDao.findAll().size());
    }

    @Test
    public void findByIds() {
        assertEquals(1, sportDao.findByIds(Sets.newHashSet(s.get(0).getId())).size());
        assertEquals(2, sportDao.findByIds(Sets.newHashSet(s.get(0).getId(), s.get(2).getId())).size());
        assertEquals(3, sportDao.findByIds(Sets.newHashSet(s.get(1).getId(), s.get(2).getId(), s.get(3).getId())).size());

        AssertCollectionContains.with(sportDao.findByIds(Sets.newHashSet(s.get(0).getId())))
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .assertEquals();

        AssertCollectionContains.with(sportDao.findByIds(Sets.newHashSet(s.get(0).getId(), s.get(2).getId())))
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Pref", "Преферанс")
                .assertEquals();

        AssertCollectionContains.with(sportDao.findByIds(Sets.newHashSet(s.get(1).getId(), s.get(2).getId(), s.get(3).getId())))
                .onProperties(nameEn, nameRu)
                .values("Basketball", "Баскетбол")
                .values("Pref", "Преферанс")
                .values("Tennis", "Теннис")
                .assertEquals();
    }
}
