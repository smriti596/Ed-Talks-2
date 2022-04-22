package com.example.ed_talk.SignIn;

public class User {
    public String fullName,age,email;
    public User() {

    }

    public User(String fullName,String age,String email) {
        this.fullName=fullName;
        this.age=age;
        this.email=email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
