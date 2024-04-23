package org.example.heaventfx;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserSession {
    private static UserSession instance;

    private String username;
    private String mailUser;

    private User user;



    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    // Ajoutez cette méthode
    public void setUser(User user) {
        this.user = user;
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    private String getEmailFromJson(String username) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<User> users = Arrays.asList(mapper.readValue(new File("src/main/resources/org/example/heaventfx/users.json"), User[].class));
            return users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .map(User::getEmail)
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}