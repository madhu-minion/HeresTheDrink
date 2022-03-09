package com.greymatter.heresthedrink.model;

public class User {

    private String name,phone, email,password,uid;

    public User() {
    }

    public User(String name, String phone, String mail, String password, String uid) {
        this.name = name;
        this.phone = phone;
        this.email = mail;
        this.password = password;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
