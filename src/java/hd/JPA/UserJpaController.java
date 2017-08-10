/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.IllegalOrphanException;
import hd.JPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Professional;
import hd.entity.City;
import hd.entity.Role;
import hd.entity.Tracking;
import java.util.ArrayList;
import java.util.Collection;
import hd.entity.IdeaBook;
import hd.entity.Orders;
import hd.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getTrackingCollection() == null) {
            user.setTrackingCollection(new ArrayList<Tracking>());
        }
        if (user.getIdeaBookCollection() == null) {
            user.setIdeaBookCollection(new ArrayList<IdeaBook>());
        }
        if (user.getOrdersCollection() == null) {
            user.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professional professional = user.getProfessional();
            if (professional != null) {
                professional = em.getReference(professional.getClass(), professional.getUserId());
                user.setProfessional(professional);
            }
            City cityCode = user.getCityCode();
            if (cityCode != null) {
                cityCode = em.getReference(cityCode.getClass(), cityCode.getCityCode());
                user.setCityCode(cityCode);
            }
            Role roleId = user.getRoleId();
            if (roleId != null) {
                roleId = em.getReference(roleId.getClass(), roleId.getRoleId());
                user.setRoleId(roleId);
            }
            Collection<Tracking> attachedTrackingCollection = new ArrayList<Tracking>();
            for (Tracking trackingCollectionTrackingToAttach : user.getTrackingCollection()) {
                trackingCollectionTrackingToAttach = em.getReference(trackingCollectionTrackingToAttach.getClass(), trackingCollectionTrackingToAttach.getTrackingPK());
                attachedTrackingCollection.add(trackingCollectionTrackingToAttach);
            }
            user.setTrackingCollection(attachedTrackingCollection);
            Collection<IdeaBook> attachedIdeaBookCollection = new ArrayList<IdeaBook>();
            for (IdeaBook ideaBookCollectionIdeaBookToAttach : user.getIdeaBookCollection()) {
                ideaBookCollectionIdeaBookToAttach = em.getReference(ideaBookCollectionIdeaBookToAttach.getClass(), ideaBookCollectionIdeaBookToAttach.getIdeaBookId());
                attachedIdeaBookCollection.add(ideaBookCollectionIdeaBookToAttach);
            }
            user.setIdeaBookCollection(attachedIdeaBookCollection);
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : user.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getOrderId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            user.setOrdersCollection(attachedOrdersCollection);
            em.persist(user);
            if (professional != null) {
                User oldUserOfProfessional = professional.getUser();
                if (oldUserOfProfessional != null) {
                    oldUserOfProfessional.setProfessional(null);
                    oldUserOfProfessional = em.merge(oldUserOfProfessional);
                }
                professional.setUser(user);
                professional = em.merge(professional);
            }
            if (cityCode != null) {
                cityCode.getUserCollection().add(user);
                cityCode = em.merge(cityCode);
            }
            if (roleId != null) {
                roleId.getUserCollection().add(user);
                roleId = em.merge(roleId);
            }
            for (Tracking trackingCollectionTracking : user.getTrackingCollection()) {
                User oldUserOfTrackingCollectionTracking = trackingCollectionTracking.getUser();
                trackingCollectionTracking.setUser(user);
                trackingCollectionTracking = em.merge(trackingCollectionTracking);
                if (oldUserOfTrackingCollectionTracking != null) {
                    oldUserOfTrackingCollectionTracking.getTrackingCollection().remove(trackingCollectionTracking);
                    oldUserOfTrackingCollectionTracking = em.merge(oldUserOfTrackingCollectionTracking);
                }
            }
            for (IdeaBook ideaBookCollectionIdeaBook : user.getIdeaBookCollection()) {
                User oldUserIdOfIdeaBookCollectionIdeaBook = ideaBookCollectionIdeaBook.getUserId();
                ideaBookCollectionIdeaBook.setUserId(user);
                ideaBookCollectionIdeaBook = em.merge(ideaBookCollectionIdeaBook);
                if (oldUserIdOfIdeaBookCollectionIdeaBook != null) {
                    oldUserIdOfIdeaBookCollectionIdeaBook.getIdeaBookCollection().remove(ideaBookCollectionIdeaBook);
                    oldUserIdOfIdeaBookCollectionIdeaBook = em.merge(oldUserIdOfIdeaBookCollectionIdeaBook);
                }
            }
            for (Orders ordersCollectionOrders : user.getOrdersCollection()) {
                User oldUserIdOfOrdersCollectionOrders = ordersCollectionOrders.getUserId();
                ordersCollectionOrders.setUserId(user);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldUserIdOfOrdersCollectionOrders != null) {
                    oldUserIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldUserIdOfOrdersCollectionOrders = em.merge(oldUserIdOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            Professional professionalOld = persistentUser.getProfessional();
            Professional professionalNew = user.getProfessional();
            City cityCodeOld = persistentUser.getCityCode();
            City cityCodeNew = user.getCityCode();
            Role roleIdOld = persistentUser.getRoleId();
            Role roleIdNew = user.getRoleId();
            Collection<Tracking> trackingCollectionOld = persistentUser.getTrackingCollection();
            Collection<Tracking> trackingCollectionNew = user.getTrackingCollection();
            Collection<IdeaBook> ideaBookCollectionOld = persistentUser.getIdeaBookCollection();
            Collection<IdeaBook> ideaBookCollectionNew = user.getIdeaBookCollection();
            Collection<Orders> ordersCollectionOld = persistentUser.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = user.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            if (professionalOld != null && !professionalOld.equals(professionalNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Professional " + professionalOld + " since its user field is not nullable.");
            }
            for (Tracking trackingCollectionOldTracking : trackingCollectionOld) {
                if (!trackingCollectionNew.contains(trackingCollectionOldTracking)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tracking " + trackingCollectionOldTracking + " since its user field is not nullable.");
                }
            }
            for (IdeaBook ideaBookCollectionOldIdeaBook : ideaBookCollectionOld) {
                if (!ideaBookCollectionNew.contains(ideaBookCollectionOldIdeaBook)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IdeaBook " + ideaBookCollectionOldIdeaBook + " since its userId field is not nullable.");
                }
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (professionalNew != null) {
                professionalNew = em.getReference(professionalNew.getClass(), professionalNew.getUserId());
                user.setProfessional(professionalNew);
            }
            if (cityCodeNew != null) {
                cityCodeNew = em.getReference(cityCodeNew.getClass(), cityCodeNew.getCityCode());
                user.setCityCode(cityCodeNew);
            }
            if (roleIdNew != null) {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getRoleId());
                user.setRoleId(roleIdNew);
            }
            Collection<Tracking> attachedTrackingCollectionNew = new ArrayList<Tracking>();
            for (Tracking trackingCollectionNewTrackingToAttach : trackingCollectionNew) {
                trackingCollectionNewTrackingToAttach = em.getReference(trackingCollectionNewTrackingToAttach.getClass(), trackingCollectionNewTrackingToAttach.getTrackingPK());
                attachedTrackingCollectionNew.add(trackingCollectionNewTrackingToAttach);
            }
            trackingCollectionNew = attachedTrackingCollectionNew;
            user.setTrackingCollection(trackingCollectionNew);
            Collection<IdeaBook> attachedIdeaBookCollectionNew = new ArrayList<IdeaBook>();
            for (IdeaBook ideaBookCollectionNewIdeaBookToAttach : ideaBookCollectionNew) {
                ideaBookCollectionNewIdeaBookToAttach = em.getReference(ideaBookCollectionNewIdeaBookToAttach.getClass(), ideaBookCollectionNewIdeaBookToAttach.getIdeaBookId());
                attachedIdeaBookCollectionNew.add(ideaBookCollectionNewIdeaBookToAttach);
            }
            ideaBookCollectionNew = attachedIdeaBookCollectionNew;
            user.setIdeaBookCollection(ideaBookCollectionNew);
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getOrderId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            user.setOrdersCollection(ordersCollectionNew);
            user = em.merge(user);
            if (professionalNew != null && !professionalNew.equals(professionalOld)) {
                User oldUserOfProfessional = professionalNew.getUser();
                if (oldUserOfProfessional != null) {
                    oldUserOfProfessional.setProfessional(null);
                    oldUserOfProfessional = em.merge(oldUserOfProfessional);
                }
                professionalNew.setUser(user);
                professionalNew = em.merge(professionalNew);
            }
            if (cityCodeOld != null && !cityCodeOld.equals(cityCodeNew)) {
                cityCodeOld.getUserCollection().remove(user);
                cityCodeOld = em.merge(cityCodeOld);
            }
            if (cityCodeNew != null && !cityCodeNew.equals(cityCodeOld)) {
                cityCodeNew.getUserCollection().add(user);
                cityCodeNew = em.merge(cityCodeNew);
            }
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew)) {
                roleIdOld.getUserCollection().remove(user);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld)) {
                roleIdNew.getUserCollection().add(user);
                roleIdNew = em.merge(roleIdNew);
            }
            for (Tracking trackingCollectionNewTracking : trackingCollectionNew) {
                if (!trackingCollectionOld.contains(trackingCollectionNewTracking)) {
                    User oldUserOfTrackingCollectionNewTracking = trackingCollectionNewTracking.getUser();
                    trackingCollectionNewTracking.setUser(user);
                    trackingCollectionNewTracking = em.merge(trackingCollectionNewTracking);
                    if (oldUserOfTrackingCollectionNewTracking != null && !oldUserOfTrackingCollectionNewTracking.equals(user)) {
                        oldUserOfTrackingCollectionNewTracking.getTrackingCollection().remove(trackingCollectionNewTracking);
                        oldUserOfTrackingCollectionNewTracking = em.merge(oldUserOfTrackingCollectionNewTracking);
                    }
                }
            }
            for (IdeaBook ideaBookCollectionNewIdeaBook : ideaBookCollectionNew) {
                if (!ideaBookCollectionOld.contains(ideaBookCollectionNewIdeaBook)) {
                    User oldUserIdOfIdeaBookCollectionNewIdeaBook = ideaBookCollectionNewIdeaBook.getUserId();
                    ideaBookCollectionNewIdeaBook.setUserId(user);
                    ideaBookCollectionNewIdeaBook = em.merge(ideaBookCollectionNewIdeaBook);
                    if (oldUserIdOfIdeaBookCollectionNewIdeaBook != null && !oldUserIdOfIdeaBookCollectionNewIdeaBook.equals(user)) {
                        oldUserIdOfIdeaBookCollectionNewIdeaBook.getIdeaBookCollection().remove(ideaBookCollectionNewIdeaBook);
                        oldUserIdOfIdeaBookCollectionNewIdeaBook = em.merge(oldUserIdOfIdeaBookCollectionNewIdeaBook);
                    }
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    User oldUserIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getUserId();
                    ordersCollectionNewOrders.setUserId(user);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldUserIdOfOrdersCollectionNewOrders != null && !oldUserIdOfOrdersCollectionNewOrders.equals(user)) {
                        oldUserIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldUserIdOfOrdersCollectionNewOrders = em.merge(oldUserIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Professional professionalOrphanCheck = user.getProfessional();
            if (professionalOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Professional " + professionalOrphanCheck + " in its professional field has a non-nullable user field.");
            }
            Collection<Tracking> trackingCollectionOrphanCheck = user.getTrackingCollection();
            for (Tracking trackingCollectionOrphanCheckTracking : trackingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Tracking " + trackingCollectionOrphanCheckTracking + " in its trackingCollection field has a non-nullable user field.");
            }
            Collection<IdeaBook> ideaBookCollectionOrphanCheck = user.getIdeaBookCollection();
            for (IdeaBook ideaBookCollectionOrphanCheckIdeaBook : ideaBookCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the IdeaBook " + ideaBookCollectionOrphanCheckIdeaBook + " in its ideaBookCollection field has a non-nullable userId field.");
            }
            Collection<Orders> ordersCollectionOrphanCheck = user.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            City cityCode = user.getCityCode();
            if (cityCode != null) {
                cityCode.getUserCollection().remove(user);
                cityCode = em.merge(cityCode);
            }
            Role roleId = user.getRoleId();
            if (roleId != null) {
                roleId.getUserCollection().remove(user);
                roleId = em.merge(roleId);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<User> loadAccountsByStatus(int status) {
        List<User> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("User.loadAccountByStatus");
            query.setParameter("status", status);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public List<User> loadAccountsByStatusAndRole(int status, int role) {
        List<User> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("User.loadAccountByStatusAndRole");
            query.setParameter("status", status);
            query.setParameter("roleId", role);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
    
    public void banUser (Integer userId) throws NonexistentEntityException, IllegalOrphanException, Exception{
        EntityManager em = getEntityManager();
        try {
            User user = findUser(userId);
            if (user == null) {
                throw new NonexistentEntityException("This user is not exist");
            } else {
                user.setStatus(-1);
                edit(user);
            }
        } finally {
            em.close();
        }
    }
    
    public void activeUser (Integer userId) throws NonexistentEntityException, IllegalOrphanException, Exception{
        EntityManager em = getEntityManager();
        try {
            User user = findUser(userId);
            if (user == null) {
                throw new NonexistentEntityException("This user is not exist");
            } else {
                user.setStatus(1);
                edit(user);
            }
        } finally {
            em.close();
        }
    }
}
