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
public class ProjectDTO implements Serializable{
    private Integer projectId;
    private String projectName;
    private String address;
    private String urlPhoto;
    private Integer quantity;
    public ProjectDTO() {
    }

    public ProjectDTO(Integer projectId, String projectName, String address, String urlPhoto, Integer quantity) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.address = address;
        this.urlPhoto = urlPhoto;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
    
    
}
