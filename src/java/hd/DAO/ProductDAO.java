/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.entity.Category;
import hd.entity.Product;
import hd.entity.ProductPhoto;
import hd.entity.SellerInfo;
import hd.entity.Style;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author cuk3t
 */
public class ProductDAO implements Serializable{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    public List<Product> getListProduct() {
    
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
             String jpql ="SELECT p FROM Product p WHERE p.status=1";
             Query query = em.createQuery(jpql);
             return (List<Product>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Product> getListProductByCategory(int categoryID) {
    
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
             String jpql = "SELECT p FROM Product p WHERE p.categoryCategoryId.categoryId = "+categoryID+" and p.status=1";
             Query query = em.createQuery(jpql);
             return (List<Product>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Product getProductByID(String id){
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
             Query query = em.createNamedQuery("Product.findByProductId");
             query.setParameter("productId", id);
             return (Product) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Category getCategoryByName(String category){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Category.findByCategoryName");
            query.setParameter("categoryName", category);
            return  (Category) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }        
        
    }
    public List<Product> getListProductBySeller(int id) {
    
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
             String jpql = "SELECT p FROM Product p WHERE p.userId.userId = "+id+"";
             Query query = em.createQuery(jpql);
             
             return (List<Product>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void createProduct(String productName, String barCode, float price,
            int quantity, String size, String material, String warranty, int style, int category, String description, int userID, String productID){
        
        EntityManager em = emf.createEntityManager();
        
        try {            
             
            Product product = new Product();
            product.setProductName(productName);
            product.setBarCode(barCode);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setSize(size);
            product.setMaterial(material);
            product.setWarranty(warranty);
            product.setStatus(0);
            Query query = em.createNamedQuery("Style.findByStyleId");
            query.setParameter("styleId",style);
            Style stl = (Style) query.getSingleResult();
            product.setStyleStyleId(stl);
            Query query2 = em.createNamedQuery("Category.findByCategoryId");
            query2.setParameter("categoryId",category);
            Category cat = (Category) query2.getSingleResult();
            product.setCategoryCategoryId(cat);
            product.setDescripsion(description);
            Query query3 = em.createNamedQuery("SellerInfo.findByUserId");
            query3.setParameter("userId",userID);
            SellerInfo seller = (SellerInfo) query3.getSingleResult();
            product.setUserId(seller);
            product.setProductId(productID);
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public Product getProductOfSeller(String productID, int sellerID) {
    
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
             String jpql = "SELECT p FROM Product p WHERE p.productId like '"+productID+"' and p.userId.userId="+sellerID+"";
             Query query = em.createQuery(jpql);
             return (Product) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean editProduct(String productID, String productName, String barCode,float price, int quantity, String size,
            String material, String warranty, int style, int category, String description){
        EntityManager em = emf.createEntityManager();
        Product entity= em.find(Product.class, productID);         
        if(entity!=null){
            entity.setProductName(productName);
            entity.setBarCode(barCode);
            entity.setPrice(price);
            entity.setQuantity(quantity);
            entity.setSize(size);
            entity.setMaterial(material);
            entity.setWarranty(warranty);
            Query query = em.createNamedQuery("Style.findByStyleId");
            query.setParameter("styleId",style);
            Style stl = (Style) query.getSingleResult();
            entity.setStyleStyleId(stl);
            Query query2 = em.createNamedQuery("Category.findByCategoryId");
            query2.setParameter("categoryId",category);
            Category cat = (Category) query2.getSingleResult();
            entity.setCategoryCategoryId(cat);
            entity.setDescripsion(description);
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }
    public ProductPhoto insertProductPhoto(String title, String descripsion, String url, String productID) {
        EntityManager em = emf.createEntityManager();
             
        Product product= em.find(Product.class, productID);
        try {
            
            ProductPhoto entity = new ProductPhoto();
            entity.setTitle(title);
            entity.setDescription(descripsion);
            entity.setStatus(0);
            entity.setUrl(url);
            entity.setProductId(product);              
            em.getTransaction().begin();
            product.setStatus(0);
            em.merge(product);
            em.persist(entity); 
            em.getTransaction().commit();            
            em.close();           
            
            return entity;

        } catch (Exception e) {
            return null;
        } finally {
            
            
        }

    }
}
