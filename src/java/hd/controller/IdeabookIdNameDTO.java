/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import java.io.Serializable;

/**
 *
 * @author Lê Đại An
 */
public class IdeabookIdNameDTO implements Serializable {

    private int ideabookId;
    private String title;
    private int status;

    public IdeabookIdNameDTO() {
    }

    public int getIdeabookId() {
        return ideabookId;
    }

    public void setIdeabookId(int ideabookId) {
        this.ideabookId = ideabookId;
    }

    public String getIdeabookName() {
        return title;
    }

    public void setIdeabookName(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
