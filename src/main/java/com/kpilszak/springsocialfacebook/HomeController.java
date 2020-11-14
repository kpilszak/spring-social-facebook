package com.kpilszak.springsocialfacebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class HomeController {

    private final static Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private final Facebook facebook;

    @Inject
    public HomeController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        User user = facebook.userOperations().getUserProfile();
        LOG.info("authorized user {}, id: {}", user.getName(), user.getId());
        model.addAttribute("user", user);
        return "home";
    }
}
