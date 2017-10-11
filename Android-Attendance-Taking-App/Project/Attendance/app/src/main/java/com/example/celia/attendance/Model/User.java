package com.example.celia.attendance.Model;

/**
 * Created by celia on 7/31/16.
 */


import com.example.celia.attendance.Constants;

public class User {

    private String userName;

    private String name;

    private String andrewId;

    private Constants.Role role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAndrewId() {
        return andrewId;
    }

    public void setAndrewId(String andrewId) {
        this.andrewId = andrewId;
    }

    public Constants.Role getRole() {
        return role;
    }

    public void setRole(Constants.Role role) {
        this.role = role;
    }

}
