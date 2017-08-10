package hd.DTO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Lê Đại An
 */
public class ProjectIdNameDTO {
    private int projectId;
    private String projectName;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProjectIdNameDTO() {
    }

    public ProjectIdNameDTO(int projectId, String projectName, int status) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.status = status;
    }
    
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
}
