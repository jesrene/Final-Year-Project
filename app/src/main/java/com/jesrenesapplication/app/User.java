package com.jesrenesapplication.app;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // For demonstration purposes, we'll have a static list of users
    private static List<User> userList = new ArrayList<>();


    private User getUserByEmail(String email) {
        // You can directly access the userList from the User class
        List<User> userList = User.userList;

        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user; // Found a matching user
            }
        }

        return null; // User not found
    }
}
