package com.example.mylastchatapp.model;

public class UserModel {
    String email;
    String username;
    String password;
    String reenteredpassword;

    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserModel() {
    }

    public UserModel(String email, String username, String password, String userID) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReenteredpassword() {
        return reenteredpassword;
    }

    public void setReenteredpassword(String reenteredpassword) {
        this.reenteredpassword = reenteredpassword;
    }
}
