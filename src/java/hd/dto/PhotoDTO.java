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
public class PhotoDTO implements Serializable{
    private Integer photoID;
    private String url;
    private Integer projectID;
    private String projectName;
    private Integer userID;
    private String email;
    private int styleId;

    public PhotoDTO() {
    }

    public PhotoDTO(Integer photoID, String url, Integer projectID, String projectName, Integer userID, String email, int styleId) {
        this.photoID = photoID;
        this.url = url;
        this.projectID = projectID;
        this.projectName = projectName;
        this.userID = userID;
        this.email = email;
        this.styleId = styleId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }  

    public Integer getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Integer photoID) {
        this.photoID = photoID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
}
