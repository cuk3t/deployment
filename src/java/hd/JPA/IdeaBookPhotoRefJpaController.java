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
import hd.entity.IdeaBook;
import hd.entity.IdeaBookPhoto;
import hd.entity.IdeaBookPhotoRef;
import hd.entity.IdeaBookPhotoRefPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class IdeaBookPhotoRefJpaController implements Serializable {

    public IdeaBookPhotoRefJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IdeaBookPhotoRef ideaBookPhotoRef) throws PreexistingEntityException, Exception {
        if (ideaBookPhotoRef.getIdeaBookPhotoRefPK() == null) {
            ideaBookPhotoRef.setIdeaBookPhotoRefPK(new IdeaBookPhotoRefPK());
        }
        ideaBookPhotoRef.getIdeaBookPhotoRefPK().setIdeaBookId(ideaBookPhotoRef.getIdeaBook().getIdeaBookId());
        ideaBookPhotoRef.getIdeaBookPhotoRefPK().setPhotoId(ideaBookPhotoRef.getIdeaBookPhoto().getPhotoId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdeaBook ideaBook = ideaBookPhotoRef.getIdeaBook();
            if (ideaBook != null) {
                ideaBook = em.getReference(ideaBook.getClass(), ideaBook.getIdeaBookId());
                ideaBookPhotoRef.setIdeaBook(ideaBook);
            }
            IdeaBookPhoto ideaBookPhoto = ideaBookPhotoRef.getIdeaBookPhoto();
            if (ideaBookPhoto != null) {
                ideaBookPhoto = em.getReference(ideaBookPhoto.getClass(), ideaBookPhoto.getPhotoId());
                ideaBookPhotoRef.setIdeaBookPhoto(ideaBookPhoto);
            }
            em.persist(ideaBookPhotoRef);
            if (ideaBook != null) {
                ideaBook.getIdeaBookPhotoRefCollection().add(ideaBookPhotoRef);
                ideaBook = em.merge(ideaBook);
            }
            if (ideaBookPhoto != null) {
                ideaBookPhoto.getIdeaBookPhotoRefCollection().add(ideaBookPhotoRef);
                ideaBookPhoto = em.merge(ideaBookPhoto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIdeaBookPhotoRef(ideaBookPhotoRef.getIdeaBookPhotoRefPK()) != null) {
                throw new PreexistingEntityException("IdeaBookPhotoRef " + ideaBookPhotoRef + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IdeaBookPhotoRef ideaBookPhotoRef) throws NonexistentEntityException, Exception {
        ideaBookPhotoRef.getIdeaBookPhotoRefPK().setIdeaBookId(ideaBookPhotoRef.getIdeaBook().getIdeaBookId());
        ideaBookPhotoRef.getIdeaBookPhotoRefPK().setPhotoId(ideaBookPhotoRef.getIdeaBookPhoto().getPhotoId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdeaBookPhotoRef persistentIdeaBookPhotoRef = em.find(IdeaBookPhotoRef.class, ideaBookPhotoRef.getIdeaBookPhotoRefPK());
            IdeaBook ideaBookOld = persistentIdeaBookPhotoRef.getIdeaBook();
            IdeaBook ideaBookNew = ideaBookPhotoRef.getIdeaBook();
            IdeaBookPhoto ideaBookPhotoOld = persistentIdeaBookPhotoRef.getIdeaBookPhoto();
            IdeaBookPhoto ideaBookPhotoNew = ideaBookPhotoRef.getIdeaBookPhoto();
            if (ideaBookNew != null) {
                ideaBookNew = em.getReference(ideaBookNew.getClass(), ideaBookNew.getIdeaBookId());
                ideaBookPhotoRef.setIdeaBook(ideaBookNew);
            }
            if (ideaBookPhotoNew != null) {
                ideaBookPhotoNew = em.getReference(ideaBookPhotoNew.getClass(), ideaBookPhotoNew.getPhotoId());
                ideaBookPhotoRef.setIdeaBookPhoto(ideaBookPhotoNew);
            }
            ideaBookPhotoRef = em.merge(ideaBookPhotoRef);
            if (ideaBookOld != null && !ideaBookOld.equals(ideaBookNew)) {
                ideaBookOld.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRef);
                ideaBookOld = em.merge(ideaBookOld);
            }
            if (ideaBookNew != null && !ideaBookNew.equals(ideaBookOld)) {
                ideaBookNew.getIdeaBookPhotoRefCollection().add(ideaBookPhotoRef);
                ideaBookNew = em.merge(ideaBookNew);
            }
            if (ideaBookPhotoOld != null && !ideaBookPhotoOld.equals(ideaBookPhotoNew)) {
                ideaBookPhotoOld.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRef);
                ideaBookPhotoOld = em.merge(ideaBookPhotoOld);
            }
            if (ideaBookPhotoNew != null && !ideaBookPhotoNew.equals(ideaBookPhotoOld)) {
                ideaBookPhotoNew.getIdeaBookPhotoRefCollection().add(ideaBookPhotoRef);
                ideaBookPhotoNew = em.merge(ideaBookPhotoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IdeaBookPhotoRefPK id = ideaBookPhotoRef.getIdeaBookPhotoRefPK();
                if (findIdeaBookPhotoRef(id) == null) {
                    throw new NonexistentEntityException("The ideaBookPhotoRef with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IdeaBookPhotoRefPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdeaBookPhotoRef ideaBookPhotoRef;
            try {
                ideaBookPhotoRef = em.getReference(IdeaBookPhotoRef.class, id);
                ideaBookPhotoRef.getIdeaBookPhotoRefPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ideaBookPhotoRef with id " + id + " no longer exists.", enfe);
            }
            IdeaBook ideaBook = ideaBookPhotoRef.getIdeaBook();
            if (ideaBook != null) {
                ideaBook.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRef);
                ideaBook = em.merge(ideaBook);
            }
            IdeaBookPhoto ideaBookPhoto = ideaBookPhotoRef.getIdeaBookPhoto();
            if (ideaBookPhoto != null) {
                ideaBookPhoto.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRef);
                ideaBookPhoto = em.merge(ideaBookPhoto);
            }
            em.remove(ideaBookPhotoRef);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IdeaBookPhotoRef> findIdeaBookPhotoRefEntities() {
        return findIdeaBookPhotoRefEntities(true, -1, -1);
    }

    public List<IdeaBookPhotoRef> findIdeaBookPhotoRefEntities(int maxResults, int firstResult) {
        return findIdeaBookPhotoRefEntities(false, maxResults, firstResult);
    }

    private List<IdeaBookPhotoRef> findIdeaBookPhotoRefEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IdeaBookPhotoRef.class));
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

    public IdeaBookPhotoRef findIdeaBookPhotoRef(IdeaBookPhotoRefPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IdeaBookPhotoRef.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdeaBookPhotoRefCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IdeaBookPhotoRef> rt = cq.from(IdeaBookPhotoRef.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
