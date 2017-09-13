package com.example.retrofitcache.bean;

/**
 * Created by tk on 17-9-3.
 */

public class UserBean {

    private String name;
    private String age;
    private String phone;
    private String city;

    public UserBean() {
    }

    public UserBean(String name, String age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
