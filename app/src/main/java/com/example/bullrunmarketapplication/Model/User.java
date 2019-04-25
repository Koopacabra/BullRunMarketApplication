package com.example.bullrunmarketapplication.Model;

public class User {

    private String Username;
    private String Password;
    private String Number;

    public User() {}

    public User(String username, String password, String number){
        this.Username = username;
        this.Password = password;
        this.Number = number;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public String getNumber () { return Number; }

    public void setNumber (String number) {this.Number = number; }

}

