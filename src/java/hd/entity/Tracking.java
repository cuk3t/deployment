/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuk3t
 */
@Entity
@Table(name = "tracking", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tracking.findAll", query = "SELECT t FROM Tracking t")
    , @NamedQuery(name = "Tracking.findByCount", query = "SELECT t FROM Tracking t WHERE t.count = :count")
    , @NamedQuery(name = "Tracking.findByLastUpdate", query = "SELECT t FROM Tracking t WHERE t.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "Tracking.findByUserId", query = "SELECT t FROM Tracking t WHERE t.trackingPK.userId = :userId")
    , @NamedQuery(name = "Tracking.findOutDateTracking", query = "SELECT t FROM Tracking t WHERE FUNCTION('DATEDIFF', :today, t.lastUpdate ) > :outdateDays AND t.user.userId = :userId")
    , @NamedQuery(name = "Tracking.findByCategoryId", query = "SELECT t FROM Tracking t WHERE t.trackingPK.categoryId = :categoryId")})
public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TrackingPK trackingPK;
    @Basic(optional = false)
    @Column(name = "count", nullable = false)
    private int count;
    @Basic(optional = false)
    @Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Category category;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Tracking() {
    }

    public Tracking(TrackingPK trackingPK) {
        this.trackingPK = trackingPK;
    }

    public Tracking(TrackingPK trackingPK, int count, Date lastUpdate) {
        this.trackingPK = trackingPK;
        this.count = count;
        this.lastUpdate = lastUpdate;
    }

    public Tracking(int userId, int categoryId) {
        this.trackingPK = new TrackingPK(userId, categoryId);
    }

    public TrackingPK getTrackingPK() {
        return trackingPK;
    }

    public void setTrackingPK(TrackingPK trackingPK) {
        this.trackingPK = trackingPK;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trackingPK != null ? trackingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tracking)) {
            return false;
        }
        Tracking other = (Tracking) object;
        if ((this.trackingPK == null && other.trackingPK != null) || (this.trackingPK != null && !this.trackingPK.equals(other.trackingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.Tracking[ trackingPK=" + trackingPK + " ]";
    }
    
}
