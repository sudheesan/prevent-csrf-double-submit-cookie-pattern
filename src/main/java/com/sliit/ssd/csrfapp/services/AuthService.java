package com.sliit.ssd.csrfapp.services;

import com.sliit.ssd.csrfapp.models.Credentials;
import com.sliit.ssd.csrfapp.models.UserCredentialsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles authentication related tasks
 *
 * Created by ssudheesan on 9/5/18.
 */

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    UserCredentialsStore userCredentialsStore = UserCredentialsStore.getUserCredentialsStore();

    /**
     * Authenticates user using username and password
     *
     * @param username
     * @param password
     * @return
     */
    public boolean isUserAuthenticated(String username, String password){


            logger.debug("Authenticating user...");
            return (username.equalsIgnoreCase("user")
                    && password.equalsIgnoreCase("abc"));


    }

    /**
     * Authenticates user's session and csrf token
     *
     * @param cookies
     * @param csrf
     *
     * @return
     */
    public boolean isAuthenticated(Cookie[] cookies, String csrf){
        Map<String, String> cookieStore = getCookies(cookies);

        // Check if the user session is valid and if the csrf token
        if(isUserSessionValid(cookieStore.get("username"), cookieStore.get("sessionID"))
                && validateCSRFToken(cookieStore.get("Csrf-token"), csrf)){
            logger.info("Token validated...");
            return true;
        }

        return false;
    }




    /**
     * Get all the cookies from cookiestore
     * @param cookies
     * @return
     */

    public Map<String, String> getCookies(Cookie[] cookies){

        Map<String, String> cookieStore = new HashMap<>();
        if (null != cookies && cookies.length > 0){
            for (Cookie cookie : cookies) {
                cookieStore.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieStore;
    }


    /**
     * Authenticates user using cookies
     *
     * @param cookies
     * @return
     */
    /**
     * Authenticates users session
     *
     * @param cookies
     * @return
     */
    public boolean isAuthenticated(Cookie[] cookies){
        Map<String, String> cookieStore = getCookies(cookies);

        // Check if the user session is valid and if the csrf token
        if(isUserSessionValid(cookieStore.get("username"), cookieStore.get("sessionID"))){
            logger.info("Token validated...");
            return true;
        }

        return false;
    }

    /**
     * Retrieves the session ID from cookies
     *
     * @param cookies
     * @return
     */
    public String sessionIdFromCookies(Cookie[] cookies){
        if (null != cookies && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionID")){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Checks if the sessionID is valid for the user
     *
     * @param username
     * @param sessionId
     * @return
     */
    public boolean isUserSessionValid(String username, String sessionId){
        if (UserCredentialsStore.getUserCredentialsStore().findCredentials(username) != null){
            logger.debug("Validating user session...");
            return sessionId.equals(userCredentialsStore
                    .findCredentials(username)
                    .getSessionID());
        }
        return false;
    }

    /**
     * Generates a new session ID
     *
     * @param username
     * @return
     */
    public String generateSessionId(String username){

        logger.debug("Generating user session...");

        // Generate new sessionID for user
        String sessionId = UUID.randomUUID().toString();

        // Store sessionID in UserCredentialsStore
        Credentials credentials = userCredentialsStore.findCredentials(username);
        credentials.setSessionID(sessionId);
        userCredentialsStore.saveCredentials(username, credentials);

        logger.debug("Storing user session...");

        return sessionId;
    }

    /**
     * Generates a new anti-CSRF token
     *
     * @param session
     * @return
     */
    public String generateToken(String session){
        logger.debug("Generating user anti-CSRF token...");

        // Generate new token for user
        return UUID.randomUUID().toString();
    }

    /**
     * Validates if the CSRF token is valid
     *
     * @param sessionID
     * @param token
     * @return
     */
    public boolean validateCSRFToken(String tokenFromCookie, String tokenFromRequestForm){

            return tokenFromCookie.equals(tokenFromRequestForm);

    }

}
