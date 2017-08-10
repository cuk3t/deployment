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
@Table(name = "style", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Style.findAll", query = "SELECT s FROM Style s")
    , @NamedQuery(name = "Style.findByStyleId", query = "SELECT s FROM Style s WHERE s.styleId = :styleId")
    , @NamedQuery(name = "Style.findByName", query = "SELECT s FROM Style s WHERE s.name = :name")})
public class Style implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "style_id", nullable = false)
    private Integer styleId;
    @Column(name = "name", length = 45)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "styleStyleId")
    private Collection<Product> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "styleId")
    private Collection<IdeaBookPhoto> ideaBookPhotoCollection;

    public Style() {
    }

    public Style(Integer styleId) {
        this.styleId = styleId;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
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
        hash += (styleId != null ? styleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Style)) {
            return false;
        }
        Style other = (Style) object;
        if ((this.styleId == null && other.styleId != null) || (this.styleId != null && !this.styleId.equals(other.styleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.Style[ styleId=" + styleId + " ]";
    }
    
}
