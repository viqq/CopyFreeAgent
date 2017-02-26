package com.free.agent.dao;

import com.free.agent.FilterNew;
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
import java.util.Locale;
import java.util.Set;

import static com.free.agent.field.DayOfWeek.*;
import static com.free.agent.model.Sport_.nameEn;
import static com.free.agent.model.Sport_.nameRu;
import static com.free.agent.model.User_.*;

/**
 * Created by antonPC on 07.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class FilerTest extends TestCase {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ScheduleDao scheduleDao;
    private List<Sport> s;

    @Before
    public void init() {
        s = new EntityTemplate<>(new Sport())
                .onProperties(nameEn, nameRu)
                .values("Football", "Футбол")
                .values("Basketball", "Баскетбол")
                .values("Pref", "Преферанс")
                .values("Golf", "Гольф")
                .create();

        List<User> u = new EntityTemplate<>(new User())
                .onProperties(email, password, phone, firstName, lastName, dateOfBirth)
                .values("l1", "p1", "11-22-33", "Anton", "Petrov", DateTime.parse("1991-04-03").toDate())
                .values("l2", "p2", "12-34-45", "Alenochka", "Mosenko", DateTime.parse("1992-04-03").toDate())
                .values("l3", "p3", "113-223-333", "Diana", "Vasilivna", DateTime.parse("1993-05-02").toDate())
                .values("l4", "p4", "124-344-454", "Yana", "Grigbnina", DateTime.parse("1994-04-03").toDate())
                .create();

        u.get(0).getSports().add(s.get(0));
        u.get(0).getSports().add(s.get(1));
        u.get(1).getSports().add(s.get(1));
        u.get(1).getSports().add(s.get(2));
        u.get(1).getSports().add(s.get(3));
        u.get(2).getSports().add(s.get(2));

        s.get(0).getUsers().add(u.get(0));
        s.get(1).getUsers().add(u.get(0));
        s.get(1).getUsers().add(u.get(1));
        s.get(2).getUsers().add(u.get(1));
        s.get(2).getUsers().add(u.get(2));
        s.get(3).getUsers().add(u.get(1));

        Schedule sch1 = new Schedule();
        Schedule sch2 = new Schedule();
        Schedule sch3 = new Schedule();
        Schedule sch4 = new Schedule();
        Schedule sch5 = new Schedule();
        Schedule sch6 = new Schedule();

        Day day1 = createDay(sch1, 2016, 5, 6);
        Day day2 = createDay(sch1, 2016, 5, 7);
        Day day3 = createDay(sch2, 2016, 5, 8); //SUNDAY
        Day day4 = createDay(sch3, 2016, 5, 10); //TUESDAY
        Day day5 = createDay(sch3, 2016, 5, 12); //THURSDAY
        Day day6 = createDay(sch5, 2016, 5, 20); //FRIDAY

        sch1.setUser(u.get(0));
        sch1.setSport(s.get(0));
        sch1.setWeekdays(getWeekday(sch1, FRIDAY, MONDAY));
        sch1.setStartTime(Time.valueOf("10:10:00"));
        sch1.setEndTime(Time.valueOf("11:10:00"));
        sch1.setDays(Sets.newHashSet(day1, day2));

        sch2.setUser(u.get(0));
        sch2.setSport(s.get(1));
        sch2.setWeekdays(getWeekday(sch2, WEDNESDAY));
        sch2.setStartTime(Time.valueOf("10:10:00"));
        sch2.setEndTime(Time.valueOf("12:10:00"));
        sch2.setDays(Sets.newHashSet(day3));

        sch3.setUser(u.get(1));
        sch3.setSport(s.get(2));
        sch3.setWeekdays(getWeekday(sch3, SATURDAY, SUNDAY, WEDNESDAY));
        sch3.setStartTime(Time.valueOf("10:10:00"));
        sch3.setEndTime(Time.valueOf("13:10:00"));
        sch3.setDays(Sets.newHashSet(day4, day5));

        sch4.setUser(u.get(1));
        sch4.setSport(s.get(3));
        sch4.setWeekdays(getWeekday(sch4, MONDAY, THURSDAY));
        sch4.setStartTime(Time.valueOf("10:10:00"));
        sch4.setEndTime(Time.valueOf("14:10:00"));
        sch4.setDays(Sets.<Day>newHashSet());

        sch5.setUser(u.get(1));
        sch5.setSport(s.get(1));
        sch5.setWeekdays(Sets.<Weekday>newHashSet());
        sch5.setStartTime(Time.valueOf("10:10:00"));
        sch5.setEndTime(Time.valueOf("15:10:00"));
        sch5.setDays(Sets.newHashSet(day6));

        sch6.setUser(u.get(2));
        sch6.setSport(s.get(2));
        sch6.setWeekdays(getWeekday(sch6, THURSDAY, TUESDAY, FRIDAY, SATURDAY));
        sch6.setStartTime(Time.valueOf("10:10:00"));
        sch6.setEndTime(Time.valueOf("16:10:00"));
        sch6.setDays(Sets.<Day>newHashSet());

        u.get(0).getSchedules().add(sch1);
        u.get(0).getSchedules().add(sch2);
        u.get(1).getSchedules().add(sch3);
        u.get(1).getSchedules().add(sch4);
        u.get(1).getSchedules().add(sch5);
        u.get(2).getSchedules().add(sch6);

        userDao.saveAll(u);
    }

    @Test
    public void createReadUpdateDeleteTest() {
        assertEquals(6, scheduleDao.findAll().size());
        FilterNew filterNew = new FilterNew();
        filterNew.setFirstName("Yana");
        assertEquals(1, userDao.findByFilter(filterNew).size());

        AssertCollectionContains.with(userDao.findByFilter(filterNew))
                .onProperties(firstName, lastName)
                .values("Yana", "Grigbnina")
                .assertEquals();

        filterNew = new FilterNew();
        filterNew.setSports(Sets.newHashSet(s.get(0).getNameEn(), s.get(1).getNameEn()));
        assertEquals(2, userDao.findByFilter(filterNew).size());

        AssertCollectionContains.with(userDao.findByFilter(filterNew))
                .onProperties(firstName, lastName)
                .values("Anton", "Petrov")
                .values("Alenochka", "Mosenko")
                .assertEquals();

        filterNew = new FilterNew();
        filterNew.setWeekdays(Sets.newHashSet(FRIDAY.name()));
        assertEquals(3, userDao.findByFilter(filterNew).size());

        AssertCollectionContains.with(userDao.findByFilter(filterNew))
                .onProperties(firstName, lastName)
                .values("Anton", "Petrov")
                .values("Alenochka", "Mosenko")
                .values("Diana", "Vasilivna")
                .assertEquals();
    }

    private Day createDay(Schedule sch, int year, int month, int day) {
        DateTime dateTime = new DateTime(year, month, day, 10, 10);
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dateTime.dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase());
        Day d = new Day();
        d.setDate(dateTime.toDate());
        d.setDayOfWeek(dayOfWeek);
        d.setSchedule(sch);
        return d;
    }

    private Set<Weekday> getWeekday(Schedule schedule, DayOfWeek... dayOfWeeks) {
        Set<Weekday> set = Sets.newHashSet();
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            Weekday weekday = new Weekday();
            weekday.setDayOfWeek(dayOfWeek);
            weekday.setSchedule(schedule);
            set.add(weekday);
        }
        return set;
    }
}
