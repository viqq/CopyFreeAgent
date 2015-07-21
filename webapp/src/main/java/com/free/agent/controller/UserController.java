package com.free.agent.controller;


import com.free.agent.dto.UserDto;
import com.free.agent.model.User;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import com.free.agent.utils.HttpRequestUtil;
import com.google.common.io.ByteStreams;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by antonPC on 21.06.15.
 */
@Controller
public class UserController {
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    @Autowired
    private UserService userService;

    @Autowired
    private SportService sportService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView getFilms() {
        ModelAndView model = new ModelAndView("registration");
        model.addObject("allSports", sportService.getAllSports());
        return model;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String get(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<String> set = HttpRequestUtil.getParams(request, "select");
        userService.save(getUser(userDto), set);
        ModelAndView model = new ModelAndView("login-form");
        return "login-form";
    }

    private User getUser(UserDto userDto) {
        User user = new User(userDto.getLogin(), userDto.getPassword(), userDto.getPhone());
        user.setDescription(userDto.getDescription());
        user.setCity(userDto.getCity());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setDateOfRegistration(new Date());
        return user;
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ModelAndView getInfo(Principal principal) {
        ModelAndView model = new ModelAndView("info");
        model.addObject("user", userService.findByLogin(principal.getName()));
        return model;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable(value = "id") long id) {
        ModelAndView model = new ModelAndView("user");
        model.addObject("user", userService.findById(id));
        return model;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView loginFormDef() {
        return new ModelAndView("login-form");
    }

    @RequestMapping(value = "/user-login", method = RequestMethod.GET)
    public ModelAndView loginForm() {
        return new ModelAndView("login-form");
    }

    @RequestMapping(value = "/error-login", method = RequestMethod.GET)
    public ModelAndView invalidLogin() {
        ModelAndView modelAndView = new ModelAndView("login-form");
        modelAndView.addObject("error", true);
        return modelAndView;
    }


    @RequestMapping(value = "/getImage", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, Principal principal) throws IOException {
        ByteStreams.copy(new FileInputStream(userService.findByLogin(principal.getName()).getImage() + ".jpg"), response.getOutputStream());
    }

    @RequestMapping(value = "/user/setImage", method = RequestMethod.POST)
    public ModelAndView setImage(HttpServletRequest request, Principal principal) throws IOException, FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        @SuppressWarnings("unchecked")
        List<FileItem> multiparts = upload.parseRequest(request);
        userService.addImage(principal.getName(), multiparts);
        ModelAndView model = new ModelAndView("info");
        model.addObject("user", userService.findByLogin(principal.getName()));
        return model;
    }
}
