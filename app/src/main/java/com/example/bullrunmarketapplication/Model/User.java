package com.example.bullrunmarketapplication.Model;

public class User {

    private String Username;
    private String Password;
    private boolean truck;

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

    public void setPassword(String password){
        this.Password = password;
    }

    public boolean isTruck() {
        return truck;
    }

    public void setTruck(boolean truck) {
        this.truck = truck;
    }
}

