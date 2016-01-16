package com.free.agent.dao;

import com.free.agent.FilterNew;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.field.DayOfWeek;
import com.free.agent.model.*;
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
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;

import static com.free.agent.field.DayOfWeek.*;

/**
 * Created by antonPC on 07.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class FilerTest extends TestCase {
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

    private FilterNew filterNew;
    private Sport s1, s2, s3, s4;
    private User u1, u2, u3, u4;
    private Schedule sch1, sch2, sch3, sch4, sch5, sch6;
    private Day day1, day2, day3, day4, day5, day6;

    @Before
    public void init() {
        s1 = new Sport("Football");
        s2 = new Sport("Basketball");
        s3 = new Sport("Pref");
        s4 = new Sport("Golf");

        u1 = new User("l1", "p1", "11-22-33");
        u1.setFirstName("Anton");
        u1.setLastName("Petrov");
        u1.setDateOfBirth(new GregorianCalendar(1991, 4, 3).getTime());

        u2 = new User("l2", "p2", "12-34-45");
        u2.setFirstName("Alenochka");
        u2.setLastName("Mosenko");
        u2.setDateOfBirth(new GregorianCalendar(1992, 4, 3).getTime());

        u3 = new User("l3", "p3", "113-223-333");
        u3.setFirstName("Diana");
        u3.setLastName("Vasilivna");
        u3.setDateOfBirth(new GregorianCalendar(1993, 5, 2).getTime());

        u4 = new User("l4", "p4", "124-344-454");
        u4.setFirstName("Yana");
        u4.setLastName("Grigbnina");
        u4.setDateOfBirth(new GregorianCalendar(1994, 4, 3).getTime());

        u1.getSports().add(s1);
        u1.getSports().add(s2);
        u2.getSports().add(s2);
        u2.getSports().add(s3);
        u2.getSports().add(s4);
        u3.getSports().add(s3);

        s1.getUsers().add(u1);
        s2.getUsers().add(u1);
        s2.getUsers().add(u2);
        s3.getUsers().add(u2);
        s3.getUsers().add(u3);
        s4.getUsers().add(u2);

        sch1 = new Schedule();
        sch2 = new Schedule();
        sch3 = new Schedule();
        sch4 = new Schedule();
        sch5 = new Schedule();
        sch6 = new Schedule();

        day1 = createDay(2016, 5, 6); //FRIDAY
        day1.setSchedule(sch1);
        day2 = createDay(2016, 5, 7); //SATURDAY
        day2.setSchedule(sch1);
        day3 = createDay(2016, 5, 8); //SUNDAY
        day3.setSchedule(sch2);
        day4 = createDay(2016, 5, 10); //TUESDAY
        day4.setSchedule(sch3);
        day5 = createDay(2016, 5, 12); //THURSDAY
        day5.setSchedule(sch3);
        day6 = createDay(2016, 5, 20); //FRIDAY
        day6.setSchedule(sch5);

        sch1.setUser(u1);
        sch1.setSport(s1);
        sch1.setWeekdays(getWeekday(sch1, FRIDAY, MONDAY));
        sch1.setStartTime(Time.valueOf("10:10:00"));
        sch1.setEndTime(Time.valueOf("11:10:00"));
        sch1.setDays(Sets.newHashSet(day1, day2));

        sch2.setUser(u1);
        sch2.setSport(s2);
        sch2.setWeekdays(getWeekday(sch2, WEDNESDAY));
        sch2.setStartTime(Time.valueOf("10:10:00"));
        sch2.setEndTime(Time.valueOf("12:10:00"));
        sch2.setDays(Sets.newHashSet(day3));

        sch3.setUser(u2);
        sch3.setSport(s3);
        sch3.setWeekdays(getWeekday(sch3, SATURDAY, SUNDAY, WEDNESDAY));
        sch3.setStartTime(Time.valueOf("10:10:00"));
        sch3.setEndTime(Time.valueOf("13:10:00"));
        sch3.setDays(Sets.newHashSet(day4, day5));

        sch4.setUser(u2);
        sch4.setSport(s4);
        sch4.setWeekdays(getWeekday(sch4, MONDAY, THURSDAY));
        sch4.setStartTime(Time.valueOf("10:10:00"));
        sch4.setEndTime(Time.valueOf("14:10:00"));
        sch4.setDays(Sets.<Day>newHashSet());

        sch5.setUser(u2);
        sch5.setSport(s2);
        sch5.setWeekdays(Sets.<Weekday>newHashSet());
        sch5.setStartTime(Time.valueOf("10:10:00"));
        sch5.setEndTime(Time.valueOf("15:10:00"));
        sch5.setDays(Sets.newHashSet(day6));

        sch6.setUser(u3);
        sch6.setSport(s3);
        sch6.setWeekdays(getWeekday(sch6, THURSDAY, TUESDAY, FRIDAY, SATURDAY));
        sch6.setStartTime(Time.valueOf("10:10:00"));
        sch6.setEndTime(Time.valueOf("16:10:00"));
        sch6.setDays(Sets.<Day>newHashSet());

        u1.getSchedules().add(sch1);
        u1.getSchedules().add(sch2);
        u2.getSchedules().add(sch3);
        u2.getSchedules().add(sch4);
        u2.getSchedules().add(sch5);
        u3.getSchedules().add(sch6);

        userDao.create(u1);
        userDao.create(u2);
        userDao.create(u3);
        userDao.create(u4);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(6, scheduleDao.findAll().size());
        filterNew = new FilterNew();
        filterNew.setFirstName("Yana");
        assertEquals(1, userDao.findByFilter(filterNew).size());

        filterNew = new FilterNew();
        filterNew.setSports(Sets.newHashSet(s1.getName(), s2.getName()));
        assertEquals(2, userDao.findByFilter(filterNew).size());

        filterNew = new FilterNew();
        filterNew.setWeekdays(Sets.newHashSet(FRIDAY.name()));
        assertEquals(3, userDao.findByFilter(filterNew).size());
    }

    private Day createDay(int year, int month, int day) {
        DateTime dateTime = new DateTime(year, month, day, 10, 10);
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dateTime.dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase());
        return new Day(dateTime.toDate(), dayOfWeek);
    }

    private Set<Weekday> getWeekday(Schedule schedule, DayOfWeek... dayOfWeeks) {
        Set<Weekday> set = Sets.newHashSet();
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            Weekday weekday = new Weekday(dayOfWeek, schedule);
            set.add(weekday);
        }
        return set;

    }
}
