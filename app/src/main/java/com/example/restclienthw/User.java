package com.example.restclienthw;

public class User {

    private int id; //user Id
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private String geo;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername(String username) { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String body) {
        this.geo = geo;
    }

}
