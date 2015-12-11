package com.free.agent.service.impl;

import com.free.agent.Filter;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.UserDto;
import com.free.agent.dto.UserRegistrationDto;
import com.free.agent.dto.UserWithSportUIDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.exception.WrongLinkException;
import com.free.agent.field.Gender;
import com.free.agent.field.Role;
import com.free.agent.model.User;
import com.free.agent.service.MailService;
import com.free.agent.service.UserService;
import com.free.agent.util.EncryptionUtils;
import com.free.agent.util.ExtractFunction;
import com.free.agent.util.LinkUtils;
import com.google.common.collect.Lists;
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
import java.util.Date;
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

    @Autowired
    private MailService mailService;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public User save(UserRegistrationDto userDto) throws EmailAlreadyUsedException {
        User user = userDao.findByEmail(userDto.getEmail());
        if (user != null) {
            if (!user.getRole().equals(Role.ROLE_NOT_ACTIVATED)) {
                LOGGER.error("User with " + userDto.getEmail() + " has registered already");
                throw new EmailAlreadyUsedException("User with " + userDto.getEmail() + " has registered already");
            } else {
                User newUser = ExtractFunction.getUser(userDto);
                newUser.setId(user.getId());
                userDao.update(newUser);
                sendLinkForConfirm(newUser.getEmail(), newUser.getHash());
                LOGGER.info("New user " + newUser.getEmail() + "was added ");
                return newUser;
            }
        }
        User createdUser = userDao.create(ExtractFunction.getUser(userDto));
        sendLinkForConfirm(createdUser.getEmail(), createdUser.getHash());
        LOGGER.info("New user " + userDto.getEmail() + "was added ");
        return createdUser;
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
        List<FileItem> multiparts = upload.parseRequest(request);
        User user = userDao.findByEmail(email);
        user.setImage(saveImage(multiparts, user.getEmail()));
        userDao.update(user);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Collection<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public User findById(Long id) {
        return userDao.find(id);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Collection<UserWithSportUIDto> findByFilter(Filter filter) {
        List<UserWithSportUIDto> list = Lists.newArrayList();
        for (User user : userDao.findByFilter(filter)) {
            list.add(ExtractFunction.getUserForUI(user));
        }
        return list;
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void deleteUser(Long id) {
        User user = userDao.find(id);
        userDao.delete(user);
        LOGGER.info("User " + user.getEmail() + "was deleted");
        deleteImage(user.getEmail());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void editUser(Long id, UserDto userDto, Set<String> sports) {
        User editedUser = getUser(userDao.find(id), userDto, sports);
        userDao.update(editedUser);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public UserWithSportUIDto getInfoAboutUser(String email) {
        return ExtractFunction.getUserForUI(userDao.findByEmail(email));
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public UserWithSportUIDto getInfoAboutUserById(Long id) {
        return ExtractFunction.getUserForUI(userDao.find(id));
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public UserWithSportUIDto activateUser(String hash, String key) throws WrongLinkException {
        User user = findUserByHash(hash, key);
        user.setRole(Role.ROLE_MODERATOR);
        return ExtractFunction.getUserForUI(userDao.update(user));
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void resetPassword(String email) throws IllegalArgumentException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " didn't existed");
        }
        String password = EncryptionUtils.getRandomString();
        user.setPassword(EncryptionUtils.encrypt(password));
        userDao.update(user);
        mailService.sendMail(email, "New password", "Your new password - " + password);
    }

    @Override
    public String getPostponeEmail(String hash, String key) {
        User user = findUserByHash(hash, key);
        return user.getEmail();
    }

    private User findUserByHash(String hash, String key) {
        checkCondition(hash == null || key == null, "Hash or key is null");
        User user = userDao.findByHash(hash);
        checkCondition(user == null, "User with " + hash + " didn't register");
        checkCondition(!EncryptionUtils.md5(user.getEmail()).equals(key), "Hash and password don't correspond");
        return user;
    }

    private void checkCondition(boolean condition, String message) {
        if (condition) {
            throw new WrongLinkException(message);
        }
    }

    private void sendLinkForConfirm(String email, String hash) {
        String link = LinkUtils.getLinkForRegistration(email, hash, false);
        mailService.sendMail(email, "Activate yor profile", "Go to the link " + link);
        LOGGER.info("Email was sent for " + email);
    }

    private User getUser(User user, UserDto userDto, Set<String> names) {
        user.setPhone(userDto.getPhone());
        user.setDescription(userDto.getDescription());
        user.setCity(userDto.getCity());
        user.setDateOfBirth(userDto.getDateOfBirth() == null ? null : new Date(userDto.getDateOfBirth()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender() == null ? null : Gender.valueOf(userDto.getGender()));
        user.setSports(sportDao.findByNames(names));
        return user;
    }

    private void deleteImage(String email) {
        File fileSaveDir = new File(SAVE_PATH + File.separator + email);
        if (fileSaveDir.exists()) {
            if (deleteDirectory(fileSaveDir)) {
                LOGGER.info("Image for " + email + "was deleted");
            } else {
                LOGGER.error("Image for " + email + "can not be deleted");
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return (path.delete());
    }

    private String saveImage(List<FileItem> image, String email) throws Exception {
        for (FileItem item : image) {
            if (!item.isFormField()) {
                File fileSaveDir = new File(SAVE_PATH + File.separator + email);
                if (!fileSaveDir.exists()) {
                    if (!fileSaveDir.mkdir()) {
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
