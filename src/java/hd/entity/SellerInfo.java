/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "seller_info", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SellerInfo.findAll", query = "SELECT s FROM SellerInfo s ORDER BY s.dueDate")
    , @NamedQuery(name = "SellerInfo.findByTaxNumber", query = "SELECT s FROM SellerInfo s WHERE s.taxNumber = :taxNumber")
    , @NamedQuery(name = "SellerInfo.findByStoreAddress", query = "SELECT s FROM SellerInfo s WHERE s.storeAddress = :storeAddress")
    , @NamedQuery(name = "SellerInfo.findBySellerName", query = "SELECT s FROM SellerInfo s WHERE s.sellerName = :sellerName")
    , @NamedQuery(name = "SellerInfo.findByStartDate", query = "SELECT s FROM SellerInfo s WHERE s.startDate = :startDate")
    , @NamedQuery(name = "SellerInfo.findByDueDate", query = "SELECT s FROM SellerInfo s WHERE s.dueDate = :dueDate")
    , @NamedQuery(name = "SellerInfo.findByPhone", query = "SELECT s FROM SellerInfo s WHERE s.phone = :phone")
    , @NamedQuery(name = "SellerInfo.findByUserId", query = "SELECT s FROM SellerInfo s WHERE s.userId = :userId")})
public class SellerInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "tax_number", length = 45)
    private String taxNumber;
    @Column(name = "store_address", length = 245)
    private String storeAddress;
    @Column(name = "seller_name", length = 245)
    private String sellerName;
    @Basic(optional = false)
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "phone", length = 15)
    private String phone;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Product> productCollection;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Professional professional;

    public SellerInfo() {
    }

    public SellerInfo(Integer userId) {
        this.userId = userId;
    }

    public SellerInfo(Integer userId, Date startDate, Date dueDate) {
        this.userId = userId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    public String getStartDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(startDate);
    }
    
    public String getStartDateString2() {
        return new SimpleDateFormat("dd/MM/yyyy").format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getDueDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(dueDate);
    }
    
    public String getDueDateString2() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dueDate);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
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
        if (!(object instanceof SellerInfo)) {
            return false;
        }
        SellerInfo other = (SellerInfo) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.SellerInfo[ userId=" + userId + " ]";
    }
    
}
