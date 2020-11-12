package com.kpilszak.springsocialfacebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final static Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private final Facebook facebook;


}
