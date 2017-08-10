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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cuk3t
 */
@Entity
@Table(name = "professional", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Professional.findAll", query = "SELECT p FROM Professional p")
    , @NamedQuery(name = "Professional.findByProfessionalName", query = "SELECT p FROM Professional p WHERE p.professionalName = :professionalName")
    , @NamedQuery(name = "Professional.findByAddress", query = "SELECT p FROM Professional p WHERE p.address = :address")
    , @NamedQuery(name = "Professional.findByUserId", query = "SELECT p FROM Professional p WHERE p.userId = :userId")})
public class Professional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "professional_name", length = 45)
    private String professionalName;
    @Column(name = "address", length = 245)
    private String address;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professionalUserId")
    private Collection<Project> projectCollection;
    @JoinColumn(name = "type_of_work_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TypeOfWork typeOfWorkId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "professional")
    private SellerInfo sellerInfo;

    public Professional() {
    }

    public Professional(Integer userId) {
        this.userId = userId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    public TypeOfWork getTypeOfWorkId() {
        return typeOfWorkId;
    }

    public void setTypeOfWorkId(TypeOfWork typeOfWorkId) {
        this.typeOfWorkId = typeOfWorkId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SellerInfo getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(SellerInfo sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professional)) {
            return false;
        }
        Professional other = (Professional) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.Professional[ userId=" + userId + " ]";
    }
    
}
