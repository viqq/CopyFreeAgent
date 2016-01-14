package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.field.DayOfWeek;
import com.free.agent.model.*;
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

import java.sql.Time;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Created by antonPC on 05.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class ScheduleDaoImplTest extends TestCase {
    @Autowired
    private SportDao sportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DayDao dayDao;
    @Autowired
    private WeekdayDao weekdayDao;
    @Autowired
    private ScheduleDao scheduleDao;

    private Sport s1, s2;
    private User u1, u2;
    private Schedule sch1, sch2;
    private Day day1, day2, day3;

    @Before
    public void init() {
        s1 = new Sport("Football");
        s2 = new Sport("Basketball");

        u1 = new User("l1", "p1", "11-22-33");
        u1.setFirstName("Anton");
        u1.setLastName("Petrov");
        u1.setDateOfBirth(new GregorianCalendar(1991, 4, 3).getTime());

        u2 = new User("l2", "p2", "12-34-45");
        u2.setFirstName("Alenochka");
        u2.setLastName("Mosenko");
        u2.setDateOfBirth(new GregorianCalendar(1992, 4, 3).getTime());

        u1.getSports().add(s1);
        u1.getSports().add(s2);
        u2.getSports().add(s1);

        sch1 = new Schedule();
        sch2 = new Schedule();

        day1 = new Day(sch1, new GregorianCalendar(2016, 5, 5, 10, 10, 10).getTime(), DayOfWeek.SATURDAY);
        day2 = new Day(sch1, new GregorianCalendar(2016, 5, 6, 10, 10, 10).getTime(), DayOfWeek.THURSDAY);
        day3 = new Day(sch2, new GregorianCalendar(2016, 5, 7, 10, 10, 10).getTime(), DayOfWeek.TUESDAY);

        sch1.setUser(u1);
        sch1.setSport(s1);
        sch1.setWeekdays(getWeekday(sch1, DayOfWeek.FRIDAY, DayOfWeek.MONDAY));
        sch1.setStartTime(Time.valueOf("10:10:00"));
        sch1.setEndTime(Time.valueOf("10:10:00"));
        sch1.setDays(Sets.newHashSet(day1, day2));

        sch2.setUser(u1);
        sch2.setSport(s2);
        sch2.setWeekdays(getWeekday(sch2, DayOfWeek.WEDNESDAY));
        sch2.setStartTime(Time.valueOf("10:10:00"));
        sch2.setEndTime(Time.valueOf("10:10:00"));
        sch2.setDays(Sets.newHashSet(day3));

        u1.getSchedules().add(sch1);
        u1.getSchedules().add(sch2);

        userDao.create(u1);
        userDao.create(u2);
        sportDao.create(s1);
        sportDao.create(s2);
        dayDao.create(day1);
        dayDao.create(day2);
        dayDao.create(day3);
        scheduleDao.create(sch1);
        scheduleDao.create(sch2);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(2, userDao.findAll().size());
        User user = userDao.findByEmail(u1.getEmail());
        for (Schedule s : user.getSchedules()) {
            assertTrue(s.getSport().getName().equals(s1.getName()) || s.getSport().getName().equals(s2.getName()));
        }
        assertEquals(2, user.getSchedules().size());
        assertEquals(2, scheduleDao.findAll().size());
        assertEquals(3, dayDao.findAll().size());
        user.getSchedules().remove(sch1);
        sch1.setSport(null);
        sch1.setUser(null);
        userDao.update(user);
        scheduleDao.delete(sch1);
        assertEquals(1, user.getSchedules().size());
        assertEquals(1, scheduleDao.findAll().size());
        assertEquals(1, dayDao.findAll().size());

    }

    @Test
    public void findAllByUserId() {
        assertEquals(2, scheduleDao.findAllByUserId(u1.getId()).size());
        assertEquals(0, scheduleDao.findAllByUserId(u2.getId()).size());
    }

    private Set<Weekday> getWeekday(Schedule schedule, DayOfWeek... dayOfWeeks) {
        Set<Weekday> set = Sets.newHashSet();
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            Weekday weekday = new Weekday(dayOfWeek, schedule);
            weekdayDao.create(weekday);
            set.add(weekday);
        }
        return set;

    }
}
