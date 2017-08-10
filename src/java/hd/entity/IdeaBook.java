/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

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
@Table(name = "idea_book", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdeaBook.findAll", query = "SELECT i FROM IdeaBook i")
    , @NamedQuery(name = "IdeaBook.findByIdeaBookId", query = "SELECT i FROM IdeaBook i WHERE i.ideaBookId = :ideaBookId")
    , @NamedQuery(name = "IdeaBook.findByTitle", query = "SELECT i FROM IdeaBook i WHERE i.title = :title")
    , @NamedQuery(name = "IdeaBook.findByDescription", query = "SELECT i FROM IdeaBook i WHERE i.description = :description")
    , @NamedQuery(name = "IdeaBook.findByIsPublic", query = "SELECT i FROM IdeaBook i WHERE i.isPublic = :isPublic")
    , @NamedQuery(name = "IdeaBook.findByStatus", query = "SELECT i FROM IdeaBook i WHERE i.status = :status AND i.userId.status != -1")
    , @NamedQuery(name = "IdeaBook.loadIdAndName", query = "SELECT i.ideaBookId, i.title, i.status FROM IdeaBook i WHERE i.userId.userId = :userId AND i.status != 2")})
public class IdeaBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idea_book_id", nullable = false)
    private Integer ideaBookId;
    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 85)
    private String title;
    @Column(name = "description", length = 555)
    private String description;
    @Basic(optional = false)
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private int status;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private User userId;
    @OneToMany(mappedBy = "ideaBookId")
    private Collection<IdeaBookPhoto> ideaBookPhotoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ideaBook")
    private Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollection;

    public IdeaBook() {
    }

    public IdeaBook(Integer ideaBookId) {
        this.ideaBookId = ideaBookId;
    }

    public IdeaBook(Integer ideaBookId, String title, boolean isPublic, int status) {
        this.ideaBookId = ideaBookId;
        this.title = title;
        this.isPublic = isPublic;
        this.status = status;
    }

    public Integer getIdeaBookId() {
        return ideaBookId;
    }

    public void setIdeaBookId(Integer ideaBookId) {
        this.ideaBookId = ideaBookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<IdeaBookPhoto> getIdeaBookPhotoCollection() {
        return ideaBookPhotoCollection;
    }

    public void setIdeaBookPhotoCollection(Collection<IdeaBookPhoto> ideaBookPhotoCollection) {
        this.ideaBookPhotoCollection = ideaBookPhotoCollection;
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
        hash += (ideaBookId != null ? ideaBookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdeaBook)) {
            return false;
        }
        IdeaBook other = (IdeaBook) object;
        if ((this.ideaBookId == null && other.ideaBookId != null) || (this.ideaBookId != null && !this.ideaBookId.equals(other.ideaBookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.IdeaBook[ ideaBookId=" + ideaBookId + " ]";
    }
    
}
