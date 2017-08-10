/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import hd.controller.Constant;
import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cuk3t
 */
@Entity
@Table(name = "idea_book_photo", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdeaBookPhoto.findAll", query = "SELECT i FROM IdeaBookPhoto i")
    , @NamedQuery(name = "IdeaBookPhoto.findByPhotoId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.photoId = :photoId")
    , @NamedQuery(name = "IdeaBookPhoto.findByUrl", query = "SELECT i FROM IdeaBookPhoto i WHERE i.url = :url")
    , @NamedQuery(name = "IdeaBookPhoto.findByTilte", query = "SELECT i FROM IdeaBookPhoto i WHERE i.tilte = :tilte")
    , @NamedQuery(name = "IdeaBookPhoto.findByDescription", query = "SELECT i FROM IdeaBookPhoto i WHERE i.description = :description")
    , @NamedQuery(name = "IdeaBookPhoto.findByStatus", query = "SELECT i FROM IdeaBookPhoto i WHERE i.status = :status")
//    , @NamedQuery(name = "IdeaBookPhoto.loadIdeabookPhotoByStatus", query = "SELECT i.ideaBookId.ideaBookId FROM IdeaBookPhoto i WHERE i.status = :status AND i.ideaBookId.ideaBookId != null AND i.ideaBookId.userId.status != -1 GROUP BY i.ideaBookId.ideaBookId")
//    , @NamedQuery(name = "IdeaBookPhoto.loadURLIdeabookPhotoByStatus", query = "SELECT i.url FROM IdeaBookPhoto i WHERE i.status = :status AND i.ideaBookId.ideaBookId != null GROUP BY i.ideaBookId.ideaBookId")
    , @NamedQuery(name = "IdeaBookPhoto.loadFirstPhotoByStatusAndIdeabookId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.status = :status AND i.ideaBookId.ideaBookId = :ideaBookId")
    , @NamedQuery(name = "IdeaBookPhoto.loadFirstPhotoByIdeabookId", query = "SELECT i.url FROM IdeaBookPhoto i WHERE i.ideaBookId.ideaBookId = :ideaBookId AND i.status != 2")
    , @NamedQuery(name = "IdeaBookPhoto.loadFirstPhotoByProjectId", query = "SELECT i.url FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status != 2")
    , @NamedQuery(name = "IdeaBookPhoto.loadFirstApprovedPhotoByProjectId", query = "SELECT i.url FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status = 1")
    , @NamedQuery(name = "IdeaBookPhoto.loadApprovedPhotosByProjectId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status = 1")
    , @NamedQuery(name = "IdeaBookPhoto.loadPhotosByProjectId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status != 2")
    , @NamedQuery(name = "IdeaBookPhoto.loadPhotosByIdeabookId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.ideaBookId.ideaBookId = :ideaBookId AND i.status != 2")
    , @NamedQuery(name = "IdeaBookPhoto.loadCategoryByIdeabookId", query = "SELECT i.categoryId.categoryName FROM IdeaBookPhoto i WHERE i.ideaBookId.ideaBookId = :ideaBookId AND i.status != 2 GROUP BY i.categoryId")
    , @NamedQuery(name = "IdeaBookPhoto.loadCategoryByProjectId", query = "SELECT i.categoryId.categoryName FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status != 2 GROUP BY i.categoryId")
    , @NamedQuery(name = "IdeaBookPhoto.loadStyleByIdeabookId", query = "SELECT i.styleId.name FROM IdeaBookPhoto i WHERE i.ideaBookId.ideaBookId = :ideaBookId AND i.status != 2 GROUP BY i.styleId")
    , @NamedQuery(name = "IdeaBookPhoto.loadStyleByProjectId", query = "SELECT i.styleId.name FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status != 2 GROUP BY i.styleId")
    , @NamedQuery(name = "IdeaBookPhoto.checkAllPhotoByIdeabookId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.ideaBookId.ideaBookId = :ideaBookId AND i.status = 0")
    , @NamedQuery(name = "IdeaBookPhoto.checkAllPhotoByProjectId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.projectId.projectId = :projectId AND i.status = 0")
    , @NamedQuery(name = "IdeaBookPhoto.findByProjectId", query = "SELECT i FROM IdeaBookPhoto i WHERE i.projectId = :id AND i.status != 2")
    , @NamedQuery(name = "IdeaBookPhoto.findProjectsHavePhotoApprovedByUserId", query = "SELECT i.projectId.projectId FROM IdeaBookPhoto i WHERE i.status = 1 AND i.projectId.projectId != null AND i.projectId.professionalUserId.userId = :userId GROUP BY i.projectId.projectId")})
public class IdeaBookPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "photo_id", nullable = false)
    private Integer photoId;
    @Column(name = "url", length = 355)
    private String url;
    @Column(name = "tilte", length = 145)
    private String tilte;
    @Column(name = "description", length = 555)
    private String description;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "idea_book_id", referencedColumnName = "idea_book_id")
    @ManyToOne
    private IdeaBook ideaBookId;
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne
    private Project projectId;
    @JoinColumn(name = "style_id", referencedColumnName = "style_id", nullable = false)
    @ManyToOne(optional = false)
    private Style styleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ideaBookPhoto")
    private Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollection;

    public IdeaBookPhoto() {
    }

    public IdeaBookPhoto(String tilte, String url, String description, Category categoryId, Style styleId, Project projectId) {
        this.url = url;
        this.tilte = tilte;
        this.description = description;
        this.categoryId = categoryId;
        this.projectId = projectId;
        this.styleId = styleId;
        this.status = Constant.STATUS_WAIT;
    }

    public IdeaBookPhoto(Integer photoId) {
        this.photoId = photoId;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public IdeaBook getIdeaBookId() {
        return ideaBookId;
    }

    public void setIdeaBookId(IdeaBook ideaBookId) {
        this.ideaBookId = ideaBookId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public Style getStyleId() {
        return styleId;
    }

    public void setStyleId(Style styleId) {
        this.styleId = styleId;
    }

    @XmlTransient
    public Collection<IdeaBookPhotoRef> getIdeaBookPhotoRefCollection() {
        return ideaBookPhotoRefCollection;
    }

    public void setIdeaBookPhotoRefCollection(Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollection) {
        this.ideaBookPhotoRefCollection = ideaBookPhotoRefCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (photoId != null ? photoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdeaBookPhoto)) {
            return false;
        }
        IdeaBookPhoto other = (IdeaBookPhoto) object;
        if ((this.photoId == null && other.photoId != null) || (this.photoId != null && !this.photoId.equals(other.photoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.IdeaBookPhoto[ photoId=" + photoId + " ]";
    }
    
}
