package com.sliit.ssd.csrfapp.controllers;

import com.sliit.ssd.csrfapp.models.Credentials;
import com.sliit.ssd.csrfapp.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for handling login requests
 *
 * Created by ssudheesan on 9/5/18.
 */

@Controller
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute Credentials credentials, HttpServletResponse response){
        String username = credentials.getUsername();
        logger.debug("username = " +credentials.getUsername());
        if(authService.isUserAuthenticated(username, credentials.getPassword())){
            String sessionId = authService.generateSessionId(username);
            Cookie sessionCookie = new Cookie("sessionID", sessionId );
            Cookie userCookie = new Cookie("username", username);
            Cookie csrfTokenCookie = new Cookie("Csrf-token", authService.generateToken(sessionId));
            response.addCookie(sessionCookie);
            response.addCookie(userCookie);
            response.addCookie(csrfTokenCookie);
            return "redirect:/home";
        }
        return "redirect:/login?status=failed";
    }
}
