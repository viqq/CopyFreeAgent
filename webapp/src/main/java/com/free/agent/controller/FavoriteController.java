package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

import static com.free.agent.FreeAgentAPI.*;

/**
 * Created by antonPC on 06.12.15.
 */
@Controller
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping(value = GET_ALL_FAVORITES, method = RequestMethod.GET)
    public
    @ResponseBody
    String getUserFavorite(Principal principal) {
        return Response.ok(favoriteService.findAllByUserEmail(principal.getName()));
    }

    @RequestMapping(value = SAVE_FAVORITE, method = RequestMethod.POST)
    public
    @ResponseBody
    String addUserToFavorite(Principal principal, @PathVariable("id") Long id) {
        favoriteService.addUserToFavorite(principal.getName(), id);
        return Response.ok();
    }

    @RequestMapping(value = DELETE_FAVORITE, method = RequestMethod.DELETE)
    public
    @ResponseBody
    String removeUserFromFavorite(Principal principal, @PathVariable("id") Long id) {
        favoriteService.removeUserFromFavorite(principal.getName(), id);
        return Response.ok();
    }
}
