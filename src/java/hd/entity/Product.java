/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "product", catalog = "house_decor", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId")
    , @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName")
    , @NamedQuery(name = "Product.findByBarCode", query = "SELECT p FROM Product p WHERE p.barCode = :barCode")
    , @NamedQuery(name = "Product.findByDescripsion", query = "SELECT p FROM Product p WHERE p.descripsion = :descripsion")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByQuantity", query = "SELECT p FROM Product p WHERE p.quantity = :quantity")
    , @NamedQuery(name = "Product.findBySize", query = "SELECT p FROM Product p WHERE p.size = :size")
    , @NamedQuery(name = "Product.findByMaterial", query = "SELECT p FROM Product p WHERE p.material = :material")
    , @NamedQuery(name = "Product.findByWarranty", query = "SELECT p FROM Product p WHERE p.warranty = :warranty")
    , @NamedQuery(name = "Product.findByStatus", query = "SELECT p FROM Product p WHERE p.status = :status")
    , @NamedQuery(name = "Product.loadProductByStatus", query = "SELECT p FROM Product p WHERE p.status = :status")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "product_id", nullable = false, length = 20)
    private String productId;
    @Column(name = "product_name", length = 145)
    private String productName;
    @Column(name = "bar_code", length = 45)
    private String barCode;
    @Column(name = "descripsion", length = 545)
    private String descripsion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price", precision = 12)
    private Float price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "size", length = 100)
    private String size;
    @Column(name = "material", length = 45)
    private String material;
    @Column(name = "warranty", length = 45)
    private String warranty;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "category_category_id", referencedColumnName = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryCategoryId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private SellerInfo userId;
    @JoinColumn(name = "style_style_id", referencedColumnName = "style_id", nullable = false)
    @ManyToOne(optional = false)
    private Style styleStyleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<PromotionDetail> promotionDetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<OrderDetail> orderDetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductPhoto> productPhotoCollection;

    public Product() {
    }

    public Product(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getDescripsion() {
        return descripsion;
    }

    public void setDescripsion(String descripsion) {
        this.descripsion = descripsion;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Category getCategoryCategoryId() {
        return categoryCategoryId;
    }

    public void setCategoryCategoryId(Category categoryCategoryId) {
        this.categoryCategoryId = categoryCategoryId;
    }

    public SellerInfo getUserId() {
        return userId;
    }

    public void setUserId(SellerInfo userId) {
        this.userId = userId;
    }

    public Style getStyleStyleId() {
        return styleStyleId;
    }

    public void setStyleStyleId(Style styleStyleId) {
        this.styleStyleId = styleStyleId;
    }

    @XmlTransient
    public Collection<PromotionDetail> getPromotionDetailCollection() {
        return promotionDetailCollection;
    }

    public void setPromotionDetailCollection(Collection<PromotionDetail> promotionDetailCollection) {
        this.promotionDetailCollection = promotionDetailCollection;
    }

    @XmlTransient
    public Collection<OrderDetail> getOrderDetailCollection() {
        return orderDetailCollection;
    }

    public void setOrderDetailCollection(Collection<OrderDetail> orderDetailCollection) {
        this.orderDetailCollection = orderDetailCollection;
    }

    @XmlTransient
    public Collection<ProductPhoto> getProductPhotoCollection() {
        return productPhotoCollection;
    }

    public void setProductPhotoCollection(Collection<ProductPhoto> productPhotoCollection) {
        this.productPhotoCollection = productPhotoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hd.entity.Product[ productId=" + productId + " ]";
    }
    
}
