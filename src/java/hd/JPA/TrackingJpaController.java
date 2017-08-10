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
import hd.entity.Category;
import hd.entity.Tracking;
import hd.entity.TrackingPK;
import hd.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class TrackingJpaController implements Serializable {

    public TrackingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tracking tracking) throws PreexistingEntityException, Exception {
        if (tracking.getTrackingPK() == null) {
            tracking.setTrackingPK(new TrackingPK());
        }
        tracking.getTrackingPK().setUserId(tracking.getUser().getUserId());
        tracking.getTrackingPK().setCategoryId(tracking.getCategory().getCategoryId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category category = tracking.getCategory();
            if (category != null) {
                category = em.getReference(category.getClass(), category.getCategoryId());
                tracking.setCategory(category);
            }
            User user = tracking.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                tracking.setUser(user);
            }
            em.persist(tracking);
            if (category != null) {
                category.getTrackingCollection().add(tracking);
                category = em.merge(category);
            }
            if (user != null) {
                user.getTrackingCollection().add(tracking);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTracking(tracking.getTrackingPK()) != null) {
                throw new PreexistingEntityException("Tracking " + tracking + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tracking tracking) throws NonexistentEntityException, Exception {
        tracking.getTrackingPK().setUserId(tracking.getUser().getUserId());
        tracking.getTrackingPK().setCategoryId(tracking.getCategory().getCategoryId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tracking persistentTracking = em.find(Tracking.class, tracking.getTrackingPK());
            Category categoryOld = persistentTracking.getCategory();
            Category categoryNew = tracking.getCategory();
            User userOld = persistentTracking.getUser();
            User userNew = tracking.getUser();
            if (categoryNew != null) {
                categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getCategoryId());
                tracking.setCategory(categoryNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                tracking.setUser(userNew);
            }
            tracking = em.merge(tracking);
            if (categoryOld != null && !categoryOld.equals(categoryNew)) {
                categoryOld.getTrackingCollection().remove(tracking);
                categoryOld = em.merge(categoryOld);
            }
            if (categoryNew != null && !categoryNew.equals(categoryOld)) {
                categoryNew.getTrackingCollection().add(tracking);
                categoryNew = em.merge(categoryNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getTrackingCollection().remove(tracking);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getTrackingCollection().add(tracking);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TrackingPK id = tracking.getTrackingPK();
                if (findTracking(id) == null) {
                    throw new NonexistentEntityException("The tracking with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TrackingPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tracking tracking;
            try {
                tracking = em.getReference(Tracking.class, id);
                tracking.getTrackingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tracking with id " + id + " no longer exists.", enfe);
            }
            Category category = tracking.getCategory();
            if (category != null) {
                category.getTrackingCollection().remove(tracking);
                category = em.merge(category);
            }
            User user = tracking.getUser();
            if (user != null) {
                user.getTrackingCollection().remove(tracking);
                user = em.merge(user);
            }
            em.remove(tracking);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tracking> findTrackingEntities() {
        return findTrackingEntities(true, -1, -1);
    }

    public List<Tracking> findTrackingEntities(int maxResults, int firstResult) {
        return findTrackingEntities(false, maxResults, firstResult);
    }

    private List<Tracking> findTrackingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tracking.class));
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

    public Tracking findTracking(TrackingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tracking.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrackingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tracking> rt = cq.from(Tracking.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int enterCategory(int userId, int categoryId) throws Exception {
        TrackingPK trackingPK = new TrackingPK(userId, categoryId);
        Tracking tracking = findTracking(trackingPK);

        EntityManager em = getEntityManager();
        try {
            if (tracking == null) {
                tracking.setCount(1);
                create(tracking);

                return 1;
            } else {
                int count = tracking.getCount();
                tracking.setCount(count + 1);

                edit(tracking);

                return count + 1;
            }
        } finally {
            em.close();
        }
    }

    public void removeOutdateTracking(int outdateDays, Date today, int userId) throws Exception {

        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Tracking.findOutDateTracking");
            query.setParameter("outdateDays", outdateDays);
            query.setParameter("today", today);
            query.setParameter("userId", userId);
            List<Tracking> outdateList = query.getResultList();
            if (outdateList != null) {
                for (Tracking tracking : outdateList) {
                    destroy(tracking.getTrackingPK());
                }
            }
        } finally {
            em.close();
        }
    }

    public List<Tracking> findTrackingByCategoryId(int categoryId) throws Exception {
        List<Tracking> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Tracking.findByCategoryId");
            query.setParameter("categoryId", categoryId);
            list = query.getResultList();
        } finally {
            em.close();
        }
        return list;
    }

}
