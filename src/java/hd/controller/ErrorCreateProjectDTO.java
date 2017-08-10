/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import java.io.Serializable;

/**
 *
 * @author Dell
 */
public class ErrorCreateProjectDTO implements Serializable{
    private String message;
    private String name;
    private String address;
    private String cost;
    private String website;
    private String year;
    private String key;

    public ErrorCreateProjectDTO() {
    }

    public ErrorCreateProjectDTO(String name, String address, String cost, String website, String year, String key) {
        this.message = "Trùng tên dự án";
        this.name = name;
        this.address = address;
        this.cost = cost;
        this.website = website;
        this.year = year;
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    
}
