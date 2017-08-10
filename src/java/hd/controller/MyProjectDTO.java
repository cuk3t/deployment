/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.entity.Project;
import java.io.Serializable;

/**
 *
 * @author An Lee
 */
public class MyProjectDTO implements Serializable{
    private Project project;
    private String urlPhoto;

    public MyProjectDTO(Project project, String urlPhoto) {
        this.project = project;
        this.urlPhoto = urlPhoto;
    }

    public MyProjectDTO() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
    
    
}
