package com.example.bullrunmarketapplication.Model;

public class User {

    private String Username;
    private String Password;

    public User() {}

    public User(String username, String password){
        this.Username = username;
        this.Password = password;
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

    public void setPassword(){
        this.Password = Password;
    }

}

