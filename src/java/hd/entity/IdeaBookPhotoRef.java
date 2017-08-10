/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuk3t
 */
@Entity
@Table(name = "idea_book_photo_ref", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdeaBookPhotoRef.findAll", query = "SELECT i FROM IdeaBookPhotoRef i")
    , @NamedQuery(name = "IdeaBookPhotoRef.findByIdeaBookId", query = "SELECT i FROM IdeaBookPhotoRef i WHERE i.ideaBookPhotoRefPK.ideaBookId = :ideaBookId")
    , @NamedQuery(name = "IdeaBookPhotoRef.findByPhotoId", query = "SELECT i FROM IdeaBookPhotoRef i WHERE i.ideaBookPhotoRefPK.photoId = :photoId")
    , @NamedQuery(name = "IdeaBookPhotoRef.findByComment", query = "SELECT i FROM IdeaBookPhotoRef i WHERE i.comment = :comment")})
public class IdeaBookPhotoRef implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IdeaBookPhotoRefPK ideaBookPhotoRefPK;
    @Column(name = "comment", length = 555)
    private String comment;
    @JoinColumn(name = "idea_book_id", referencedColumnName = "idea_book_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private IdeaBook ideaBook;
    @JoinColumn(name = "photo_id", referencedColumnName = "photo_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private IdeaBookPhoto ideaBookPhoto;

    public IdeaBookPhotoRef() {
    }

    public IdeaBookPhotoRef(IdeaBookPhotoRefPK ideaBookPhotoRefPK) {
        this.ideaBookPhotoRefPK = ideaBookPhotoRefPK;
    }

    public IdeaBookPhotoRef(int ideaBookId, int photoId) {
        this.ideaBookPhotoRefPK = new IdeaBookPhotoRefPK(ideaBookId, photoId);
    }

    public IdeaBookPhotoRefPK getIdeaBookPhotoRefPK() {
        return ideaBookPhotoRefPK;
    }

    public void setIdeaBookPhotoRefPK(IdeaBookPhotoRefPK ideaBookPhotoRefPK) {
        this.ideaBookPhotoRefPK = ideaBookPhotoRefPK;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IdeaBook getIdeaBook() {
        return ideaBook;
    }

    public void setIdeaBook(IdeaBook ideaBook) {
        this.ideaBook = ideaBook;
    }

    public IdeaBookPhoto getIdeaBookPhoto() {
        return ideaBookPhoto;
    }

    public void setIdeaBookPhoto(IdeaBookPhoto ideaBookPhoto) {
        this.ideaBookPhoto = ideaBookPhoto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideaBookPhotoRefPK != null ? ideaBookPhotoRefPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdeaBookPhotoRef)) {
            return false;
        }
        IdeaBookPhotoRef other = (IdeaBookPhotoRef) object;
        if ((this.ideaBookPhotoRefPK == null && other.ideaBookPhotoRefPK != null) || (this.ideaBookPhotoRefPK != null && !this.ideaBookPhotoRefPK.equals(other.ideaBookPhotoRefPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.IdeaBookPhotoRef[ ideaBookPhotoRefPK=" + ideaBookPhotoRefPK + " ]";
    }
    
}
