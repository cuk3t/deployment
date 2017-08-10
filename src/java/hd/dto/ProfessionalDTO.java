/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.dto;

import java.io.Serializable;


/**
 *
 * @author cuk3t
 */
public class ProfessionalDTO implements Serializable{
    private String professionalName;
    private String address;
    private Integer userId;
    private String aboutMe;
    private String cityName;
    private String phoneNumber;
    private String url;

    public ProfessionalDTO() {
    }

    public ProfessionalDTO(String professionalName, String address, Integer userId, String aboutMe, String cityName, String phoneNumber, String url) {
        this.professionalName = professionalName;
        this.address = address;
        this.userId = userId;
        this.aboutMe = aboutMe;
        this.cityName = cityName;
        this.phoneNumber = phoneNumber;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
