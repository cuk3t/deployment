/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.entity.IdeaBook;
import hd.entity.IdeaBookPhoto;
import java.io.Serializable;

/**
 *
 * @author Lê Đại An
 */
public class IdeabookDTO implements Serializable {

    private IdeaBook ideabook;
    private String urlPhoto;
    String category;
    String style;

    public IdeabookDTO() {
        urlPhoto = "";
        category = "";
        style = "";
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

    public IdeaBook getIdeabook() {
        return ideabook;
    }

    public void setIdeabook(IdeaBook ideabook) {
        this.ideabook = ideabook;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    

}
