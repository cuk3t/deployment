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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "product_photo", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductPhoto.findAll", query = "SELECT p FROM ProductPhoto p")
    , @NamedQuery(name = "ProductPhoto.findByPhotoId", query = "SELECT p FROM ProductPhoto p WHERE p.photoId = :photoId")
    , @NamedQuery(name = "ProductPhoto.findByUrl", query = "SELECT p FROM ProductPhoto p WHERE p.url = :url")
    , @NamedQuery(name = "ProductPhoto.findByTitle", query = "SELECT p FROM ProductPhoto p WHERE p.title = :title")
    , @NamedQuery(name = "ProductPhoto.findByDescription", query = "SELECT p FROM ProductPhoto p WHERE p.description = :description")
    , @NamedQuery(name = "ProductPhoto.findByStatus", query = "SELECT p FROM ProductPhoto p WHERE p.status = :status")
    , @NamedQuery(name = "ProductPhoto.loadProductPhotosByProductId", query = "SELECT p FROM ProductPhoto p WHERE p.productId.productId = :productId")
    , @NamedQuery(name = "ProductPhoto.loadUrlPhotosByProductId", query = "SELECT p.url FROM ProductPhoto p WHERE p.productId.productId = :productId")
    , @NamedQuery(name = "ProductPhoto.findByUploadTime", query = "SELECT p FROM ProductPhoto p WHERE p.uploadTime = :uploadTime")})
public class ProductPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "photo_id", nullable = false)
    private Integer photoId;
    @Column(name = "url", length = 245)
    private String url;
    @Column(name = "title", length = 115)
    private String title;
    @Column(name = "description", length = 545)
    private String description;
    @Column(name = "status")
    private Integer status;
    @Basic(optional = false)
    @Column(name = "upload_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadTime;
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @ManyToOne(optional = false)
    private Product productId;

    public ProductPhoto() {
    }

    public ProductPhoto(Integer photoId) {
        this.photoId = photoId;
    }

    public ProductPhoto(Integer photoId, Date uploadTime) {
        this.photoId = photoId;
        this.uploadTime = uploadTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
        if (!(object instanceof ProductPhoto)) {
            return false;
        }
        ProductPhoto other = (ProductPhoto) object;
        if ((this.photoId == null && other.photoId != null) || (this.photoId != null && !this.photoId.equals(other.photoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.ProductPhoto[ photoId=" + photoId + " ]";
    }
    
}
