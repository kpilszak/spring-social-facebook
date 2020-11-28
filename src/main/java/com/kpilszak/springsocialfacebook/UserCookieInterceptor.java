package com.kpilszak.springsocialfacebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserCookieInterceptor extends HandlerInterceptorAdapter {

    private final static Logger LOG =
            LoggerFactory.getLogger(UserCookieInterceptor.class);
}
