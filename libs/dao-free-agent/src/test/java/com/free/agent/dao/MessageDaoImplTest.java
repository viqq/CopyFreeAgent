package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.mock.MessageDaoMock;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonPC on 29.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:free-agent-dao-context.xml"})
@Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER_TEST)
public class MessageDaoImplTest extends TestCase {
    @Autowired
    private MessageDaoMock messageDao;
    //TODO

}
