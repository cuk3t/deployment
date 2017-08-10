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
@Table(name = "type_of_work", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeOfWork.findAll", query = "SELECT t FROM TypeOfWork t")
    , @NamedQuery(name = "TypeOfWork.findById", query = "SELECT t FROM TypeOfWork t WHERE t.id = :id")
    , @NamedQuery(name = "TypeOfWork.findByName", query = "SELECT t FROM TypeOfWork t WHERE t.name = :name")})
public class TypeOfWork implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 85)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeOfWorkId")
    private Collection<Professional> professionalCollection;

    public TypeOfWork() {
    }

    public TypeOfWork(Integer id) {
        this.id = id;
    }

    public TypeOfWork(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Professional> getProfessionalCollection() {
        return professionalCollection;
    }

    public void setProfessionalCollection(Collection<Professional> professionalCollection) {
        this.professionalCollection = professionalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeOfWork)) {
            return false;
        }
        TypeOfWork other = (TypeOfWork) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.TypeOfWork[ id=" + id + " ]";
    }
    
}
