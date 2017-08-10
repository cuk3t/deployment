/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.IllegalOrphanException;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.entity.Promotion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.PromotionDetail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class PromotionJpaController implements Serializable {

    public PromotionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promotion promotion) {
        if (promotion.getPromotionDetailCollection() == null) {
            promotion.setPromotionDetailCollection(new ArrayList<PromotionDetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PromotionDetail> attachedPromotionDetailCollection = new ArrayList<PromotionDetail>();
            for (PromotionDetail promotionDetailCollectionPromotionDetailToAttach : promotion.getPromotionDetailCollection()) {
                promotionDetailCollectionPromotionDetailToAttach = em.getReference(promotionDetailCollectionPromotionDetailToAttach.getClass(), promotionDetailCollectionPromotionDetailToAttach.getPromotionDetailId());
                attachedPromotionDetailCollection.add(promotionDetailCollectionPromotionDetailToAttach);
            }
            promotion.setPromotionDetailCollection(attachedPromotionDetailCollection);
            em.persist(promotion);
            for (PromotionDetail promotionDetailCollectionPromotionDetail : promotion.getPromotionDetailCollection()) {
                Promotion oldPromotionIdOfPromotionDetailCollectionPromotionDetail = promotionDetailCollectionPromotionDetail.getPromotionId();
                promotionDetailCollectionPromotionDetail.setPromotionId(promotion);
                promotionDetailCollectionPromotionDetail = em.merge(promotionDetailCollectionPromotionDetail);
                if (oldPromotionIdOfPromotionDetailCollectionPromotionDetail != null) {
                    oldPromotionIdOfPromotionDetailCollectionPromotionDetail.getPromotionDetailCollection().remove(promotionDetailCollectionPromotionDetail);
                    oldPromotionIdOfPromotionDetailCollectionPromotionDetail = em.merge(oldPromotionIdOfPromotionDetailCollectionPromotionDetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promotion promotion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promotion persistentPromotion = em.find(Promotion.class, promotion.getId());
            Collection<PromotionDetail> promotionDetailCollectionOld = persistentPromotion.getPromotionDetailCollection();
            Collection<PromotionDetail> promotionDetailCollectionNew = promotion.getPromotionDetailCollection();
            List<String> illegalOrphanMessages = null;
            for (PromotionDetail promotionDetailCollectionOldPromotionDetail : promotionDetailCollectionOld) {
                if (!promotionDetailCollectionNew.contains(promotionDetailCollectionOldPromotionDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PromotionDetail " + promotionDetailCollectionOldPromotionDetail + " since its promotionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PromotionDetail> attachedPromotionDetailCollectionNew = new ArrayList<PromotionDetail>();
            for (PromotionDetail promotionDetailCollectionNewPromotionDetailToAttach : promotionDetailCollectionNew) {
                promotionDetailCollectionNewPromotionDetailToAttach = em.getReference(promotionDetailCollectionNewPromotionDetailToAttach.getClass(), promotionDetailCollectionNewPromotionDetailToAttach.getPromotionDetailId());
                attachedPromotionDetailCollectionNew.add(promotionDetailCollectionNewPromotionDetailToAttach);
            }
            promotionDetailCollectionNew = attachedPromotionDetailCollectionNew;
            promotion.setPromotionDetailCollection(promotionDetailCollectionNew);
            promotion = em.merge(promotion);
            for (PromotionDetail promotionDetailCollectionNewPromotionDetail : promotionDetailCollectionNew) {
                if (!promotionDetailCollectionOld.contains(promotionDetailCollectionNewPromotionDetail)) {
                    Promotion oldPromotionIdOfPromotionDetailCollectionNewPromotionDetail = promotionDetailCollectionNewPromotionDetail.getPromotionId();
                    promotionDetailCollectionNewPromotionDetail.setPromotionId(promotion);
                    promotionDetailCollectionNewPromotionDetail = em.merge(promotionDetailCollectionNewPromotionDetail);
                    if (oldPromotionIdOfPromotionDetailCollectionNewPromotionDetail != null && !oldPromotionIdOfPromotionDetailCollectionNewPromotionDetail.equals(promotion)) {
                        oldPromotionIdOfPromotionDetailCollectionNewPromotionDetail.getPromotionDetailCollection().remove(promotionDetailCollectionNewPromotionDetail);
                        oldPromotionIdOfPromotionDetailCollectionNewPromotionDetail = em.merge(oldPromotionIdOfPromotionDetailCollectionNewPromotionDetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promotion.getId();
                if (findPromotion(id) == null) {
                    throw new NonexistentEntityException("The promotion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promotion promotion;
            try {
                promotion = em.getReference(Promotion.class, id);
                promotion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promotion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PromotionDetail> promotionDetailCollectionOrphanCheck = promotion.getPromotionDetailCollection();
            for (PromotionDetail promotionDetailCollectionOrphanCheckPromotionDetail : promotionDetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Promotion (" + promotion + ") cannot be destroyed since the PromotionDetail " + promotionDetailCollectionOrphanCheckPromotionDetail + " in its promotionDetailCollection field has a non-nullable promotionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(promotion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promotion> findPromotionEntities() {
        return findPromotionEntities(true, -1, -1);
    }

    public List<Promotion> findPromotionEntities(int maxResults, int firstResult) {
        return findPromotionEntities(false, maxResults, firstResult);
    }

    private List<Promotion> findPromotionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promotion.class));
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

    public Promotion findPromotion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promotion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromotionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promotion> rt = cq.from(Promotion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
