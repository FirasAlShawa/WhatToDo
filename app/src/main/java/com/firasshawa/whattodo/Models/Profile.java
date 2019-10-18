package com.firasshawa.whattodo.Models;

public class Profile {
    private String key;
    private String name;
    private String email;
    private String imgLink;
    private int count;

    public Profile(String key, String name, String email, String imgLink, int count) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.imgLink = imgLink;
        this.count = count;
    }

    public Profile() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", count=" + count +
                '}';
    }
}
