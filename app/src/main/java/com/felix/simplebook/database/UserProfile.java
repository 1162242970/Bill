package com.felix.simplebook.database;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by android on 18-5-24.
 */

public class UserProfile extends DataSupport implements Serializable{

    //账号
    private int phone = 0;
    //密码
    private String password = null;
    //邮箱
    private String address = null;
    //个人名字
    private String name = null;
    //头像
    private String headPhoto = null;
    //性别
    private String gender = null;
    //生日
    private String birthday = null;



    public int getPhone() {
        return phone;
    }

    public UserProfile setPhone(int phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserProfile setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserProfile setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserProfile setName(String name) {
        this.name = name;
        return this;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public UserProfile setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserProfile setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public UserProfile setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

}

