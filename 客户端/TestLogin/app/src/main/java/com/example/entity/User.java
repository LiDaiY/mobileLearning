package com.example.entity;

import java.io.Serializable;

/**
 * Created by 李宗豪 on 2018/4/2.
 */

public class User implements Serializable{
    private int userId;

    private String username;

    private String password;

    private String sex;

    private String email;

    private String photo;

    private String signature;

    private int type;
    private String birth;
//    private String url;

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

    public User(int userId, String username, String password, String sex,
                String email, String photo, String signature, int type,
                String birth) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.photo = photo;
        this.signature = signature;
        this.type = type;
        this.birth = birth;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", signature='" + signature + '\'' +
                ", type=" + type +
                ", birth='" + birth + '\'' +
                '}';
    }
}
