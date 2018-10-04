package com.sliit.ssd.csrfapp.models;

import java.util.HashMap;

/**
 * Stores CSRF tokens of each sessions
 *
 * Created by ssudheesan on 9/6/18.
 */
public class SessionStore {

    private HashMap<String, String> sessions;
    private static volatile SessionStore sessionStore;

    private SessionStore(){
        sessions = new HashMap<>();
    }

    public static SessionStore getUserCredentialsStore(){
        if (sessionStore == null){
            synchronized (SessionStore.class){
                if (sessionStore == null) {
                    sessionStore = new SessionStore();
                }
            }

        }
        return sessionStore;
    }

    public void addSessionToken(String session, String token){

        if(sessions.get(session) != null){
            sessions.replace(session, token);
        } else {
            sessions.put(session, token);
        }
    }

    public String getTokenFromSession(String sessionId){
        return sessions.get(sessionId);
    }

}
