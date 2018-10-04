package com.sliit.ssd.csrfapp.models;

import java.util.HashMap;

/**
 * Stores user data such as sessionIDs, CSRF Tokens, username, password against username
 *
 * Created by ssudheesan on 9/5/18.
 */
public class UserCredentialsStore {

    private HashMap<String, Credentials> credentialsStore;
    private static volatile UserCredentialsStore userCredentialsStore;

    private UserCredentialsStore(){
        credentialsStore = new HashMap<>();
    }

    public static UserCredentialsStore getUserCredentialsStore(){
        if (userCredentialsStore == null){
            synchronized (UserCredentialsStore.class){
                if (userCredentialsStore == null) {
                    userCredentialsStore = new UserCredentialsStore();
                }
            }

        }
        return userCredentialsStore;
    }

    public void addInitialData(){
        // Create initial user in store
        Credentials credentials = new Credentials();

        String username = "user";
        String password = "abc";
        credentials.setUsername(username);
        credentials.setPassword(password);
        UserCredentialsStore.getUserCredentialsStore().saveCredentials(username, credentials);
    }

    public Credentials findCredentials(String username){
        return credentialsStore.get(username);
    }

    public void saveCredentials(String username, Credentials credentials){
        if (credentials!= null && credentialsStore.get(username) != null){
            credentialsStore.replace(username, credentials);
        } else {
            credentialsStore.put(username, credentials);
        }
    }

    @Override
    public String toString() {
        return "UserCredentialsStore{" +
                "credentialsStore=" + credentialsStore +
                '}';
    }

}
