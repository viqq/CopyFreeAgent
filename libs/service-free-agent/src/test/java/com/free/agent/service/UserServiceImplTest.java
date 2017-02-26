package com.free.agent.service;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.UserRegistrationDto;
import com.free.agent.field.Language;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.impl.UserServiceImpl;
import com.free.agent.util.EncryptionUtils;
import com.free.agent.util.FunctionUtils;
import com.google.common.collect.Sets;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonPC on 28.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml",
        "classpath*:free-agent-services-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
@ActiveProfiles("test")
public class UserServiceImplTest extends TestCase {
    private static final String FOOTBALL_EN = "FOOTBALL";
    private static final String FOOTBALL_RU = "ФУТБОЛ";

    @Mock
    private SportDao sportDao;

    @Mock
    private UserDao userDao;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUserTest() {
        UserRegistrationDto user = new UserRegistrationDto();
        Mockito.when(sportDao.findByName(FOOTBALL_EN, Language.ENG)).thenReturn(sports());
        Mockito.when(userDao.create(FunctionUtils.getUser(user))).thenReturn(user());
        User savedUser = service.save(user);
        Assert.assertEquals(1, savedUser.getSports().size());
        Assert.assertEquals(FOOTBALL_EN, savedUser.getSports().iterator().next().getNameEn());
    }

    private Sport sports() {
        Sport sport = new Sport();
        sport.setNameEn(FOOTBALL_EN);
        sport.setNameRu(FOOTBALL_RU);
        return sport;
    }

    private User user() {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setPassword(EncryptionUtils.encrypt("12345"));
        user.setSports(Sets.newHashSet(sports()));
        return user;
    }
}
