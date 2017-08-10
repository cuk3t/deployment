/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import hd.controller.Constant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cuk3t
 */
@Entity
@Table(name = "project", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId")
    , @NamedQuery(name = "Project.findByProjectName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName")
    , @NamedQuery(name = "Project.findByAddress", query = "SELECT p FROM Project p WHERE p.address = :address")
    , @NamedQuery(name = "Project.findByCost", query = "SELECT p FROM Project p WHERE p.cost = :cost")
    , @NamedQuery(name = "Project.findByWebsite", query = "SELECT p FROM Project p WHERE p.website = :website")
    , @NamedQuery(name = "Project.findByYear", query = "SELECT p FROM Project p WHERE p.year = :year")
    , @NamedQuery(name = "Project.findByKeywords", query = "SELECT p FROM Project p WHERE p.keywords = :keywords")
    , @NamedQuery(name = "Project.findByStatus", query = "SELECT p FROM Project p WHERE p.status = :status")
    , @NamedQuery(name = "Project.loadProjectByStatus", query = "SELECT p FROM Project p WHERE p.status = :status AND p.professionalUserId.user.status != -1 AND p.status != 2")
    , @NamedQuery(name = "Project.isExistedInMyProjects", query = "SELECT p FROM Project p WHERE p.professionalUserId.userId = :userId AND p.projectName = :projectName AND p.status != 2")
//    , @NamedQuery(name = "Project.findByUserId", query = "SELECT p.projectId, p.projectName, p.address, p.cost, p.website, p.year, p.keywords, p.status FROM Project p WHERE p.userId = :userId")
    , @NamedQuery(name = "Project.loadIdAndName", query = "SELECT p.projectId, p.projectName, p.status FROM Project p WHERE p.professionalUserId.userId = :userId AND p.professionalUserId.user.status != -1 AND p.status != 2")
    , @NamedQuery(name = "Project.findProjectByUserId", query = "SELECT p FROM Project p WHERE p.professionalUserId.userId = :userId AND p.status != 2 ORDER BY p.projectId DESC ")
    , @NamedQuery(name = "Project.Project.findOtherProjectByUserId", query = "SELECT p FROM Project p WHERE p.professionalUserId.userId = :userId ORDER BY p.projectId DESC ")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "project_id", nullable = false)
    private Integer projectId;
    @Basic(optional = false)
    @Column(name = "project_name", nullable = false, length = 155)
    private String projectName;
    @Column(name = "address", length = 255)
    private String address;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cost", precision = 12)
    private Float cost;
    @Column(name = "website", length = 145)
    private String website;
    @Column(name = "year")
    private Integer year;
    @Column(name = "keywords", length = 145)
    private String keywords;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private int status;
    @JoinColumn(name = "professional_user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Professional professionalUserId;
    @OneToMany(mappedBy = "projectId")
    private Collection<IdeaBookPhoto> ideaBookPhotoCollection;

    public Project() {
    }

    public Project(String projectName, String address, Float cost, String website, int year, String keywords, int status, Professional professionalId) {
        this.projectName = projectName;
        this.address = address;
        this.cost = cost;
        this.website = website;
        this.year = year;
        this.keywords = keywords;
        this.status = status;
        this.professionalUserId = professionalId;
    }

    public Project(Integer projectId, String projectName, String address, Float cost, String website, int year, String keywords, int status, Professional professionalId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.address = address;
        this.cost = cost;
        this.website = website;
        this.year = year;
        this.keywords = keywords;
        this.status = status;
        this.professionalUserId = professionalId;
    }

    public Project(Integer projectId) {
        this.projectId = projectId;
    }

    public Project(Integer projectId, String projectName, int status) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.status = status;
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

    public Float getCost() {
        return cost;
    }

    public String getCostString() {
        String flag = String.format("%,.0f", cost) + " vnd";
        if (cost == 0) {
            flag = "Không có";
        }
        return flag;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Professional getProfessionalUserId() {
        return professionalUserId;
    }

    public void setProfessionalUserId(Professional professionalUserId) {
        this.professionalUserId = professionalUserId;
    }

    @XmlTransient
    public Collection<IdeaBookPhoto> getIdeaBookPhotoCollection() {
        return ideaBookPhotoCollection;
    }

    public void setIdeaBookPhotoCollection(Collection<IdeaBookPhoto> ideaBookPhotoCollection) {
        this.ideaBookPhotoCollection = ideaBookPhotoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.Project[ projectId=" + projectId + " ]";
    }
}
