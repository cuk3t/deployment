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
 * @author Lê Đại An
 */
public class ProjectDTO implements Serializable{
    private Project project;
    private String category;
    private String style;

    public ProjectDTO() {
        category = "";
        style = "";
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    
}
