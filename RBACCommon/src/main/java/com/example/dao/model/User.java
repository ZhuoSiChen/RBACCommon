package com.example.dao.model;


public class User {

    private Integer id;
    private String name;
    private int age;
    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", Age=" + age +
                '}';
    }
}