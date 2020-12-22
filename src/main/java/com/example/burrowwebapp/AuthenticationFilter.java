package com.example.burrowwebapp;

import com.example.burrowwebapp.controller.AuthenticationController;
import com.example.burrowwebapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

}
