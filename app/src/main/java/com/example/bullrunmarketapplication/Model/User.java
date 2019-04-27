package com.example.bullrunmarketapplication.Model;

import com.example.bullrunmarketapplication.Email;

public class User {

    private String Username;
    private String Password;
    private String Email;

    public User() {}

    public User(String username, String password, String email){
        this.Username = username;
        this.Password = password;
        this.Email = email;
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

    public String getEmail () { return Email; }

    public void setEmail (String email) {this.Email = email; }

}

