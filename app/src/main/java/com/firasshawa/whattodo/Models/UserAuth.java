package com.firasshawa.whattodo.Models;

public class UserAuth {
    private String Email;
    private String Password;
    private String Name;

    public UserAuth(String email, String password, String name) {
        Email = email;
        Password = password;
        Name = name;
    }

    public UserAuth(String email, String password) {
        Email = email;
        Password = password;
        Name="";
    }

    public UserAuth() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
