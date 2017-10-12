package com.example.celia.attendance;

/**
 * Created by celia on 7/28/16.
 */
public final class ProfileUtil {
    private static String userName;
    private static String name;
    private static String role;
    private static String andrewId;
    public static ProfileUtil instance = null;
    public static ProfileUtil getInstance(){
        if (instance == null) {
            instance = new ProfileUtil();
        }
        return instance;
    }
    public ProfileUtil(){}

    public void setName(String name) {
        this.name = name;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setAndewId(String andrewId) {
        this.andrewId = andrewId;
    }

    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public String getUserName() {
        return userName;
    }
    public String getAndrewId(){
        return andrewId;
    }
}
