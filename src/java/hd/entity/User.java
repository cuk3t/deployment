/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cuk3t
 */
@Entity
@Table(name = "user", catalog = "house_decor", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByFirstname", query = "SELECT u FROM User u WHERE u.firstname = :firstname")
    , @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname")
    , @NamedQuery(name = "User.findByDateOfBirth", query = "SELECT u FROM User u WHERE u.dateOfBirth = :dateOfBirth")
    , @NamedQuery(name = "User.findByRegisterDate", query = "SELECT u FROM User u WHERE u.registerDate = :registerDate")
    , @NamedQuery(name = "User.findByPhoneNumber", query = "SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender")
    , @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status")
    , @NamedQuery(name = "User.findByPrimaryAddress", query = "SELECT u FROM User u WHERE u.primaryAddress = :primaryAddress")
    , @NamedQuery(name = "User.findBySencondAddress", query = "SELECT u FROM User u WHERE u.sencondAddress = :sencondAddress")
    , @NamedQuery(name = "User.loadAccountByStatus", query = "SELECT u.userId, u.firstname, u.lastname, u.phoneNumber, u.email, u.registerDate, u.status, u.roleId.roleId FROM User u WHERE u.status = :status")
    , @NamedQuery(name = "User.loadAccountByStatusAndRole", query = "SELECT u.userId, u.firstname, u.lastname, u.phoneNumber, u.email, u.registerDate, u.status, u.roleId.roleId FROM User u WHERE u.status = :status AND u.roleId.roleId = :roleId")
    , @NamedQuery(name = "User.setStatusAccount", query = "UPDATE User u SET u.status= :status WHERE u.userId = :userId")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 75)
    private String email;
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 55)
    private String password;
    @Column(name = "firstname", length = 40)
    private String firstname;
    @Column(name = "lastname", length = 40)
    private String lastname;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "register_date")
    @Temporal(TemporalType.DATE)
    private Date registerDate;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(name = "gender")
    private Boolean gender;
    @Lob
    @Column(name = "about_me", length = 16777215)
    private String aboutMe;
    @Column(name = "status")
    private Integer status;
    @Column(name = "primary_address", length = 255)
    private String primaryAddress;
    @Column(name = "sencond_address", length = 255)
    private String sencondAddress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Tracking> trackingCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Professional professional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<IdeaBook> ideaBookCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Orders> ordersCollection;
    @JoinColumn(name = "city_code", referencedColumnName = "city_code")
    @ManyToOne
    private City cityCode;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    @ManyToOne(optional = false)
    private Role roleId;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(String primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getSencondAddress() {
        return sencondAddress;
    }

    public void setSencondAddress(String sencondAddress) {
        this.sencondAddress = sencondAddress;
    }

    @XmlTransient
    public Collection<Tracking> getTrackingCollection() {
        return trackingCollection;
    }

    public void setTrackingCollection(Collection<Tracking> trackingCollection) {
        this.trackingCollection = trackingCollection;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    @XmlTransient
    public Collection<IdeaBook> getIdeaBookCollection() {
        return ideaBookCollection;
    }

    public void setIdeaBookCollection(Collection<IdeaBook> ideaBookCollection) {
        this.ideaBookCollection = ideaBookCollection;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    public City getCityCode() {
        return cityCode;
    }

    public void setCityCode(City cityCode) {
        this.cityCode = cityCode;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.User[ userId=" + userId + " ]";
    }
    
}
