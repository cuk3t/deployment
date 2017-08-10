/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author cuk3t
 */
@Embeddable
public class IdeaBookPhotoRefPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idea_book_id", nullable = false)
    private int ideaBookId;
    @Basic(optional = false)
    @Column(name = "photo_id", nullable = false)
    private int photoId;

    public IdeaBookPhotoRefPK() {
    }

    public IdeaBookPhotoRefPK(int ideaBookId, int photoId) {
        this.ideaBookId = ideaBookId;
        this.photoId = photoId;
    }

    public int getIdeaBookId() {
        return ideaBookId;
    }

    public void setIdeaBookId(int ideaBookId) {
        this.ideaBookId = ideaBookId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ideaBookId;
        hash += (int) photoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdeaBookPhotoRefPK)) {
            return false;
        }
        IdeaBookPhotoRefPK other = (IdeaBookPhotoRefPK) object;
        if (this.ideaBookId != other.ideaBookId) {
            return false;
        }
        if (this.photoId != other.photoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.IdeaBookPhotoRefPK[ ideaBookId=" + ideaBookId + ", photoId=" + photoId + " ]";
    }
    
}
