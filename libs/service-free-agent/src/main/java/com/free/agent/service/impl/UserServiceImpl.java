package com.free.agent.service.impl;

import com.free.agent.Filter;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.dao.UserDao;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 21.06.15.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final String SAVE_PATH = "/var/free-agent/images";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 20; // 20MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 30; // 30MB

    @Autowired
    private UserDao userDao;

    @Autowired
    private SportDao sportDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public User save(User user, Set<String> names) {
        Set<Sport> sports = sportDao.findByNames(names);
        user.setSports(sports);
        LOGGER.info("New user " + user.getEmail() + "was added ");
        return userDao.create(user);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void addImage(String email, HttpServletRequest request) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        @SuppressWarnings("unchecked")
        List<FileItem> multiparts = upload.parseRequest(request);
        User user = userDao.findByEmail(email);
        user.setImage(saveImage(multiparts, user.getEmail()));
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Collection<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public User findById(long id) {
        return userDao.find(id);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Collection<User> findByFilter(Filter filter) {
        return userDao.findByFilter(filter);
    }


    private String saveImage(List<FileItem> image, String email) throws Exception {
        for (FileItem item : image) {
            if (!item.isFormField()) {
                File fileSaveDir = new File(SAVE_PATH + File.separator + email);
                if (!fileSaveDir.exists()) {
                    boolean isCreated = fileSaveDir.mkdir();
                    if (!isCreated) {
                        LOGGER.error("Don't have permission. Use sudo chmod 777 /var/free-agent/images ");
                    }
                }
                item.write(new File(SAVE_PATH + File.separator + email + File.separator + email + ".jpg"));
                break;
            }
        }
        return SAVE_PATH + File.separator + email + File.separator + email;
    }

}
