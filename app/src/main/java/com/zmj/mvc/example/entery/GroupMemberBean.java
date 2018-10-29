package com.zmj.mvc.example.entery;

import java.io.Serializable;

/**
 * @author Zmj
 * @date 2018/10/26
 */
public class GroupMemberBean implements Serializable {

    private String name;        //显示数据
    private String phoneNumber; //电话
    private String sortLetters; //显示数据的首字母
    private String value;       //代码


    public GroupMemberBean() {
    }

    public GroupMemberBean(String name, String phoneNumber, String value) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.value = value;
    }

    public GroupMemberBean(String name, String phoneNumber, String sortLetters, String value) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sortLetters = sortLetters;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public String toString() {
        return name;
    }
}
