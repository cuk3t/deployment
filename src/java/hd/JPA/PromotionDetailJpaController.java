/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.NonexistentEntityException;
import hd.JPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Product;
import hd.entity.Promotion;
import hd.entity.PromotionDetail;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class PromotionDetailJpaController implements Serializable {

    public PromotionDetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PromotionDetail promotionDetail) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = promotionDetail.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                promotionDetail.setProductId(productId);
            }
            Promotion promotionId = promotionDetail.getPromotionId();
            if (promotionId != null) {
                promotionId = em.getReference(promotionId.getClass(), promotionId.getId());
                promotionDetail.setPromotionId(promotionId);
            }
            em.persist(promotionDetail);
            if (productId != null) {
                productId.getPromotionDetailCollection().add(promotionDetail);
                productId = em.merge(productId);
            }
            if (promotionId != null) {
                promotionId.getPromotionDetailCollection().add(promotionDetail);
                promotionId = em.merge(promotionId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPromotionDetail(promotionDetail.getPromotionDetailId()) != null) {
                throw new PreexistingEntityException("PromotionDetail " + promotionDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PromotionDetail promotionDetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PromotionDetail persistentPromotionDetail = em.find(PromotionDetail.class, promotionDetail.getPromotionDetailId());
            Product productIdOld = persistentPromotionDetail.getProductId();
            Product productIdNew = promotionDetail.getProductId();
            Promotion promotionIdOld = persistentPromotionDetail.getPromotionId();
            Promotion promotionIdNew = promotionDetail.getPromotionId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                promotionDetail.setProductId(productIdNew);
            }
            if (promotionIdNew != null) {
                promotionIdNew = em.getReference(promotionIdNew.getClass(), promotionIdNew.getId());
                promotionDetail.setPromotionId(promotionIdNew);
            }
            promotionDetail = em.merge(promotionDetail);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getPromotionDetailCollection().remove(promotionDetail);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getPromotionDetailCollection().add(promotionDetail);
                productIdNew = em.merge(productIdNew);
            }
            if (promotionIdOld != null && !promotionIdOld.equals(promotionIdNew)) {
                promotionIdOld.getPromotionDetailCollection().remove(promotionDetail);
                promotionIdOld = em.merge(promotionIdOld);
            }
            if (promotionIdNew != null && !promotionIdNew.equals(promotionIdOld)) {
                promotionIdNew.getPromotionDetailCollection().add(promotionDetail);
                promotionIdNew = em.merge(promotionIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promotionDetail.getPromotionDetailId();
                if (findPromotionDetail(id) == null) {
                    throw new NonexistentEntityException("The promotionDetail with id " + id + " no longer exists.");
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
            PromotionDetail promotionDetail;
            try {
                promotionDetail = em.getReference(PromotionDetail.class, id);
                promotionDetail.getPromotionDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promotionDetail with id " + id + " no longer exists.", enfe);
            }
            Product productId = promotionDetail.getProductId();
            if (productId != null) {
                productId.getPromotionDetailCollection().remove(promotionDetail);
                productId = em.merge(productId);
            }
            Promotion promotionId = promotionDetail.getPromotionId();
            if (promotionId != null) {
                promotionId.getPromotionDetailCollection().remove(promotionDetail);
                promotionId = em.merge(promotionId);
            }
            em.remove(promotionDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PromotionDetail> findPromotionDetailEntities() {
        return findPromotionDetailEntities(true, -1, -1);
    }

    public List<PromotionDetail> findPromotionDetailEntities(int maxResults, int firstResult) {
        return findPromotionDetailEntities(false, maxResults, firstResult);
    }

    private List<PromotionDetail> findPromotionDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PromotionDetail.class));
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

    public PromotionDetail findPromotionDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PromotionDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromotionDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PromotionDetail> rt = cq.from(PromotionDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
