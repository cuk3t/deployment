/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.IllegalOrphanException;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.controller.IdeabookIdNameDTO;
import hd.entity.IdeaBook;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.User;
import hd.entity.IdeaBookPhoto;
import java.util.ArrayList;
import java.util.Collection;
import hd.entity.IdeaBookPhotoRef;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class IdeaBookJpaController implements Serializable {

    public IdeaBookJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IdeaBook ideaBook) {
        if (ideaBook.getIdeaBookPhotoCollection() == null) {
            ideaBook.setIdeaBookPhotoCollection(new ArrayList<IdeaBookPhoto>());
        }
        if (ideaBook.getIdeaBookPhotoRefCollection() == null) {
            ideaBook.setIdeaBookPhotoRefCollection(new ArrayList<IdeaBookPhotoRef>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = ideaBook.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                ideaBook.setUserId(userId);
            }
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollection = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhotoToAttach : ideaBook.getIdeaBookPhotoCollection()) {
                ideaBookPhotoCollectionIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollection.add(ideaBookPhotoCollectionIdeaBookPhotoToAttach);
            }
            ideaBook.setIdeaBookPhotoCollection(attachedIdeaBookPhotoCollection);
            Collection<IdeaBookPhotoRef> attachedIdeaBookPhotoRefCollection = new ArrayList<IdeaBookPhotoRef>();
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach : ideaBook.getIdeaBookPhotoRefCollection()) {
                ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach = em.getReference(ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach.getClass(), ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach.getIdeaBookPhotoRefPK());
                attachedIdeaBookPhotoRefCollection.add(ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach);
            }
            ideaBook.setIdeaBookPhotoRefCollection(attachedIdeaBookPhotoRefCollection);
            em.persist(ideaBook);
            if (userId != null) {
                userId.getIdeaBookCollection().add(ideaBook);
                userId = em.merge(userId);
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhoto : ideaBook.getIdeaBookPhotoCollection()) {
                IdeaBook oldIdeaBookIdOfIdeaBookPhotoCollectionIdeaBookPhoto = ideaBookPhotoCollectionIdeaBookPhoto.getIdeaBookId();
                ideaBookPhotoCollectionIdeaBookPhoto.setIdeaBookId(ideaBook);
                ideaBookPhotoCollectionIdeaBookPhoto = em.merge(ideaBookPhotoCollectionIdeaBookPhoto);
                if (oldIdeaBookIdOfIdeaBookPhotoCollectionIdeaBookPhoto != null) {
                    oldIdeaBookIdOfIdeaBookPhotoCollectionIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionIdeaBookPhoto);
                    oldIdeaBookIdOfIdeaBookPhotoCollectionIdeaBookPhoto = em.merge(oldIdeaBookIdOfIdeaBookPhotoCollectionIdeaBookPhoto);
                }
            }
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionIdeaBookPhotoRef : ideaBook.getIdeaBookPhotoRefCollection()) {
                IdeaBook oldIdeaBookOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef = ideaBookPhotoRefCollectionIdeaBookPhotoRef.getIdeaBook();
                ideaBookPhotoRefCollectionIdeaBookPhotoRef.setIdeaBook(ideaBook);
                ideaBookPhotoRefCollectionIdeaBookPhotoRef = em.merge(ideaBookPhotoRefCollectionIdeaBookPhotoRef);
                if (oldIdeaBookOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef != null) {
                    oldIdeaBookOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRefCollectionIdeaBookPhotoRef);
                    oldIdeaBookOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef = em.merge(oldIdeaBookOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IdeaBook ideaBook) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdeaBook persistentIdeaBook = em.find(IdeaBook.class, ideaBook.getIdeaBookId());
            User userIdOld = persistentIdeaBook.getUserId();
            User userIdNew = ideaBook.getUserId();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionOld = persistentIdeaBook.getIdeaBookPhotoCollection();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionNew = ideaBook.getIdeaBookPhotoCollection();
            Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollectionOld = persistentIdeaBook.getIdeaBookPhotoRefCollection();
            Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollectionNew = ideaBook.getIdeaBookPhotoRefCollection();
            List<String> illegalOrphanMessages = null;
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionOldIdeaBookPhotoRef : ideaBookPhotoRefCollectionOld) {
                if (!ideaBookPhotoRefCollectionNew.contains(ideaBookPhotoRefCollectionOldIdeaBookPhotoRef)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IdeaBookPhotoRef " + ideaBookPhotoRefCollectionOldIdeaBookPhotoRef + " since its ideaBook field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                ideaBook.setUserId(userIdNew);
            }
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollectionNew = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhotoToAttach : ideaBookPhotoCollectionNew) {
                ideaBookPhotoCollectionNewIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollectionNew.add(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach);
            }
            ideaBookPhotoCollectionNew = attachedIdeaBookPhotoCollectionNew;
            ideaBook.setIdeaBookPhotoCollection(ideaBookPhotoCollectionNew);
            Collection<IdeaBookPhotoRef> attachedIdeaBookPhotoRefCollectionNew = new ArrayList<IdeaBookPhotoRef>();
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach : ideaBookPhotoRefCollectionNew) {
                ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach = em.getReference(ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach.getClass(), ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach.getIdeaBookPhotoRefPK());
                attachedIdeaBookPhotoRefCollectionNew.add(ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach);
            }
            ideaBookPhotoRefCollectionNew = attachedIdeaBookPhotoRefCollectionNew;
            ideaBook.setIdeaBookPhotoRefCollection(ideaBookPhotoRefCollectionNew);
            ideaBook = em.merge(ideaBook);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getIdeaBookCollection().remove(ideaBook);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getIdeaBookCollection().add(ideaBook);
                userIdNew = em.merge(userIdNew);
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionOldIdeaBookPhoto : ideaBookPhotoCollectionOld) {
                if (!ideaBookPhotoCollectionNew.contains(ideaBookPhotoCollectionOldIdeaBookPhoto)) {
                    ideaBookPhotoCollectionOldIdeaBookPhoto.setIdeaBookId(null);
                    ideaBookPhotoCollectionOldIdeaBookPhoto = em.merge(ideaBookPhotoCollectionOldIdeaBookPhoto);
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhoto : ideaBookPhotoCollectionNew) {
                if (!ideaBookPhotoCollectionOld.contains(ideaBookPhotoCollectionNewIdeaBookPhoto)) {
                    IdeaBook oldIdeaBookIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = ideaBookPhotoCollectionNewIdeaBookPhoto.getIdeaBookId();
                    ideaBookPhotoCollectionNewIdeaBookPhoto.setIdeaBookId(ideaBook);
                    ideaBookPhotoCollectionNewIdeaBookPhoto = em.merge(ideaBookPhotoCollectionNewIdeaBookPhoto);
                    if (oldIdeaBookIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto != null && !oldIdeaBookIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.equals(ideaBook)) {
                        oldIdeaBookIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionNewIdeaBookPhoto);
                        oldIdeaBookIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = em.merge(oldIdeaBookIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto);
                    }
                }
            }
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionNewIdeaBookPhotoRef : ideaBookPhotoRefCollectionNew) {
                if (!ideaBookPhotoRefCollectionOld.contains(ideaBookPhotoRefCollectionNewIdeaBookPhotoRef)) {
                    IdeaBook oldIdeaBookOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef = ideaBookPhotoRefCollectionNewIdeaBookPhotoRef.getIdeaBook();
                    ideaBookPhotoRefCollectionNewIdeaBookPhotoRef.setIdeaBook(ideaBook);
                    ideaBookPhotoRefCollectionNewIdeaBookPhotoRef = em.merge(ideaBookPhotoRefCollectionNewIdeaBookPhotoRef);
                    if (oldIdeaBookOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef != null && !oldIdeaBookOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef.equals(ideaBook)) {
                        oldIdeaBookOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRefCollectionNewIdeaBookPhotoRef);
                        oldIdeaBookOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef = em.merge(oldIdeaBookOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ideaBook.getIdeaBookId();
                if (findIdeaBook(id) == null) {
                    throw new NonexistentEntityException("The ideaBook with id " + id + " no longer exists.");
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
            IdeaBook ideaBook;
            try {
                ideaBook = em.getReference(IdeaBook.class, id);
                ideaBook.getIdeaBookId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ideaBook with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollectionOrphanCheck = ideaBook.getIdeaBookPhotoRefCollection();
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionOrphanCheckIdeaBookPhotoRef : ideaBookPhotoRefCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IdeaBook (" + ideaBook + ") cannot be destroyed since the IdeaBookPhotoRef " + ideaBookPhotoRefCollectionOrphanCheckIdeaBookPhotoRef + " in its ideaBookPhotoRefCollection field has a non-nullable ideaBook field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userId = ideaBook.getUserId();
            if (userId != null) {
                userId.getIdeaBookCollection().remove(ideaBook);
                userId = em.merge(userId);
            }
            Collection<IdeaBookPhoto> ideaBookPhotoCollection = ideaBook.getIdeaBookPhotoCollection();
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhoto : ideaBookPhotoCollection) {
                ideaBookPhotoCollectionIdeaBookPhoto.setIdeaBookId(null);
                ideaBookPhotoCollectionIdeaBookPhoto = em.merge(ideaBookPhotoCollectionIdeaBookPhoto);
            }
            em.remove(ideaBook);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IdeaBook> findIdeaBookEntities() {
        return findIdeaBookEntities(true, -1, -1);
    }

    public List<IdeaBook> findIdeaBookEntities(int maxResults, int firstResult) {
        return findIdeaBookEntities(false, maxResults, firstResult);
    }

    private List<IdeaBook> findIdeaBookEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IdeaBook.class));
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

    public IdeaBook findIdeaBook(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IdeaBook.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdeaBookCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IdeaBook> rt = cq.from(IdeaBook.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<IdeaBook> getIdeabooksByStatus(int status) {
        EntityManager em = getEntityManager();
        List<IdeaBook> list = new ArrayList<IdeaBook>();
        try {
            Query query = em.createNamedQuery("IdeaBook.findByStatus");
            query.setParameter("status", status);
            list= query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }  
    
    public List<IdeaBook> getPhotoByStatus(int status) {
        EntityManager em = getEntityManager();
        List<IdeaBook> list = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBook.findByStatus");
            query.setParameter("status", status);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
    
    public List<IdeabookIdNameDTO> getIdeabookIdAndNameByUserId(int userId) {
        EntityManager em = getEntityManager();
        List<IdeabookIdNameDTO> list = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBook.loadIdAndName");
            query.setParameter("userId", userId);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
