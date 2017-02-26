package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.field.DayOfWeek;
import com.free.agent.model.*;
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

import java.sql.Time;
import java.util.List;
import java.util.Set;

import static com.free.agent.field.DayOfWeek.*;
import static com.free.agent.model.Day_.*;
import static com.free.agent.model.Sport_.nameEn;
import static com.free.agent.model.Sport_.nameRu;
import static com.free.agent.model.User_.*;

/**
 * Created by antonPC on 05.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class ScheduleDaoImplTest extends TestCase {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DayDao dayDao;
    @Autowired
    private WeekdayDao weekdayDao;
    @Autowired
    private ScheduleDao scheduleDao;
    private Schedule sch1, sch2;
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

        sch1 = new Schedule();
        sch2 = new Schedule();

        List<Day> day = new EntityTemplate<>(new Day())
                .onProperties(schedule, date, dayOfWeek)
                .values(sch1, DateTime.parse("2016-05-05T10:10:10").toDate(), THURSDAY)
                .values(sch1, DateTime.parse("2016-05-06T10:10:10").toDate(), FRIDAY)
                .values(sch2, DateTime.parse("2016-05-07T10:10:10").toDate(), SATURDAY)
                .create();


        sch1.setUser(u.get(0));
        sch1.setSport(s.get(0));
        sch1.setWeekdays(getWeekday(sch1, FRIDAY, MONDAY));
        sch1.setStartTime(Time.valueOf("10:10:00"));
        sch1.setEndTime(Time.valueOf("10:10:00"));
        sch1.setDays(Sets.newHashSet(day.get(0), day.get(1)));

        sch2.setUser(u.get(0));
        sch2.setSport(s.get(1));
        sch2.setWeekdays(getWeekday(sch2, WEDNESDAY));
        sch2.setStartTime(Time.valueOf("10:10:00"));
        sch2.setEndTime(Time.valueOf("10:10:00"));
        sch2.setDays(Sets.newHashSet(day.get(2)));

        u.get(0).getSchedules().add(sch1);
        u.get(0).getSchedules().add(sch2);

        userDao.saveAll(u);

    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(2, userDao.findAll().size());
        User user = userDao.findByEmail(u.get(0).getEmail());

        assertEquals(2, user.getSchedules().size());
        assertEquals(2, scheduleDao.findAll().size());
        assertEquals(3, dayDao.findAll().size());

        AssertCollectionContains.with(scheduleDao.findAll())
                .onProperties("user.firstName", "sport.nameEn", "sport.nameRu")
                .values("Anton", "Football", "Футбол")
                .values("Anton", "Basketball", "Баскетбол")
                .assertEquals();

        AssertCollectionContains.with(dayDao.findAll())
                .onProperties("schedule.id", "date", "dayOfWeek")
                .values(sch1.getId(), DateTime.parse("2016-05-05T10:10:10").toDate(), THURSDAY)
                .values(sch1.getId(), DateTime.parse("2016-05-06T10:10:10").toDate(), FRIDAY)
                .values(sch2.getId(), DateTime.parse("2016-05-07T10:10:10").toDate(), SATURDAY)
                .assertEquals();

        user.getSchedules().remove(sch1);
        sch1.setSport(null);
        sch1.setUser(null);
        userDao.update(user);
        scheduleDao.delete(sch1);
        assertEquals(1, user.getSchedules().size());
        assertEquals(1, scheduleDao.findAll().size());
        assertEquals(1, dayDao.findAll().size());

        AssertCollectionContains.with(scheduleDao.findAll())
                .onProperties("user.firstName", "sport.nameEn", "sport.nameRu")
                .values("Anton", "Basketball", "Баскетбол")
                .assertEquals();

        AssertCollectionContains.with(dayDao.findAll())
                .onProperties("schedule.id", "date", "dayOfWeek")
                .values(sch2.getId(), DateTime.parse("2016-05-07T10:10:10").toDate(), SATURDAY)
                .assertEquals();

    }

    @Test
    public void findAllByUserId() {
        assertEquals(2, scheduleDao.findAllByUserId(u.get(0).getId()).size());
        assertEquals(0, scheduleDao.findAllByUserId(u.get(1).getId()).size());
        AssertCollectionContains.with(scheduleDao.findAllByUserId(u.get(0).getId()))
                .onProperties("user.firstName", "sport.nameEn", "sport.nameRu")
                .values("Anton", "Football", "Футбол")
                .values("Anton", "Basketball", "Баскетбол")
                .assertEquals();
    }

    private Set<Weekday> getWeekday(Schedule schedule, DayOfWeek... dayOfWeeks) {
        Set<Weekday> set = Sets.newHashSet();
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            Weekday weekday = new Weekday();
            weekday.setDayOfWeek(dayOfWeek);
            weekday.setSchedule(schedule);
            weekdayDao.create(weekday);
            set.add(weekday);
        }
        return set;

    }
}
