/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Product;
import hd.entity.ProductPhoto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class ProductPhotoJpaController implements Serializable {

    public ProductPhotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductPhoto productPhoto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = productPhoto.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                productPhoto.setProductId(productId);
            }
            em.persist(productPhoto);
            if (productId != null) {
                productId.getProductPhotoCollection().add(productPhoto);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductPhoto productPhoto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductPhoto persistentProductPhoto = em.find(ProductPhoto.class, productPhoto.getPhotoId());
            Product productIdOld = persistentProductPhoto.getProductId();
            Product productIdNew = productPhoto.getProductId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                productPhoto.setProductId(productIdNew);
            }
            productPhoto = em.merge(productPhoto);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getProductPhotoCollection().remove(productPhoto);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getProductPhotoCollection().add(productPhoto);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productPhoto.getPhotoId();
                if (findProductPhoto(id) == null) {
                    throw new NonexistentEntityException("The productPhoto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductPhoto productPhoto;
            try {
                productPhoto = em.getReference(ProductPhoto.class, id);
                productPhoto.getPhotoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productPhoto with id " + id + " no longer exists.", enfe);
            }
            Product productId = productPhoto.getProductId();
            if (productId != null) {
                productId.getProductPhotoCollection().remove(productPhoto);
                productId = em.merge(productId);
            }
            em.remove(productPhoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductPhoto> findProductPhotoEntities() {
        return findProductPhotoEntities(true, -1, -1);
    }

    public List<ProductPhoto> findProductPhotoEntities(int maxResults, int firstResult) {
        return findProductPhotoEntities(false, maxResults, firstResult);
    }

    private List<ProductPhoto> findProductPhotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductPhoto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ProductPhoto findProductPhoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductPhoto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductPhotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductPhoto> rt = cq.from(ProductPhoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<ProductPhoto> getProductPhotosByProductId(String productId) {
        EntityManager em = getEntityManager();
        List<ProductPhoto> list = new ArrayList<ProductPhoto>();
        try {
            Query query = em.createNamedQuery("ProductPhoto.loadProductPhotosByProductId");
            query.setParameter("productId", productId);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public String getFirstUrlProductPhotoByProductId(String productId) {
        EntityManager em = getEntityManager();
        String url = "";
        try {
            Query query = em.createNamedQuery("ProductPhoto.loadUrlPhotosByProductId");
            query.setMaxResults(1);
            query.setParameter("productId", productId);
            url = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return url;
    }
 
}
