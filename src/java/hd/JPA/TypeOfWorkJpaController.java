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
import hd.entity.TypeOfWork;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class TypeOfWorkJpaController implements Serializable {

    public TypeOfWorkJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypeOfWork typeOfWork) {
        if (typeOfWork.getProfessionalCollection() == null) {
            typeOfWork.setProfessionalCollection(new ArrayList<Professional>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Professional> attachedProfessionalCollection = new ArrayList<Professional>();
            for (Professional professionalCollectionProfessionalToAttach : typeOfWork.getProfessionalCollection()) {
                professionalCollectionProfessionalToAttach = em.getReference(professionalCollectionProfessionalToAttach.getClass(), professionalCollectionProfessionalToAttach.getUserId());
                attachedProfessionalCollection.add(professionalCollectionProfessionalToAttach);
            }
            typeOfWork.setProfessionalCollection(attachedProfessionalCollection);
            em.persist(typeOfWork);
            for (Professional professionalCollectionProfessional : typeOfWork.getProfessionalCollection()) {
                TypeOfWork oldTypeOfWorkIdOfProfessionalCollectionProfessional = professionalCollectionProfessional.getTypeOfWorkId();
                professionalCollectionProfessional.setTypeOfWorkId(typeOfWork);
                professionalCollectionProfessional = em.merge(professionalCollectionProfessional);
                if (oldTypeOfWorkIdOfProfessionalCollectionProfessional != null) {
                    oldTypeOfWorkIdOfProfessionalCollectionProfessional.getProfessionalCollection().remove(professionalCollectionProfessional);
                    oldTypeOfWorkIdOfProfessionalCollectionProfessional = em.merge(oldTypeOfWorkIdOfProfessionalCollectionProfessional);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TypeOfWork typeOfWork) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TypeOfWork persistentTypeOfWork = em.find(TypeOfWork.class, typeOfWork.getId());
            Collection<Professional> professionalCollectionOld = persistentTypeOfWork.getProfessionalCollection();
            Collection<Professional> professionalCollectionNew = typeOfWork.getProfessionalCollection();
            List<String> illegalOrphanMessages = null;
            for (Professional professionalCollectionOldProfessional : professionalCollectionOld) {
                if (!professionalCollectionNew.contains(professionalCollectionOldProfessional)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Professional " + professionalCollectionOldProfessional + " since its typeOfWorkId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Professional> attachedProfessionalCollectionNew = new ArrayList<Professional>();
            for (Professional professionalCollectionNewProfessionalToAttach : professionalCollectionNew) {
                professionalCollectionNewProfessionalToAttach = em.getReference(professionalCollectionNewProfessionalToAttach.getClass(), professionalCollectionNewProfessionalToAttach.getUserId());
                attachedProfessionalCollectionNew.add(professionalCollectionNewProfessionalToAttach);
            }
            professionalCollectionNew = attachedProfessionalCollectionNew;
            typeOfWork.setProfessionalCollection(professionalCollectionNew);
            typeOfWork = em.merge(typeOfWork);
            for (Professional professionalCollectionNewProfessional : professionalCollectionNew) {
                if (!professionalCollectionOld.contains(professionalCollectionNewProfessional)) {
                    TypeOfWork oldTypeOfWorkIdOfProfessionalCollectionNewProfessional = professionalCollectionNewProfessional.getTypeOfWorkId();
                    professionalCollectionNewProfessional.setTypeOfWorkId(typeOfWork);
                    professionalCollectionNewProfessional = em.merge(professionalCollectionNewProfessional);
                    if (oldTypeOfWorkIdOfProfessionalCollectionNewProfessional != null && !oldTypeOfWorkIdOfProfessionalCollectionNewProfessional.equals(typeOfWork)) {
                        oldTypeOfWorkIdOfProfessionalCollectionNewProfessional.getProfessionalCollection().remove(professionalCollectionNewProfessional);
                        oldTypeOfWorkIdOfProfessionalCollectionNewProfessional = em.merge(oldTypeOfWorkIdOfProfessionalCollectionNewProfessional);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = typeOfWork.getId();
                if (findTypeOfWork(id) == null) {
                    throw new NonexistentEntityException("The typeOfWork with id " + id + " no longer exists.");
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
            TypeOfWork typeOfWork;
            try {
                typeOfWork = em.getReference(TypeOfWork.class, id);
                typeOfWork.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typeOfWork with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Professional> professionalCollectionOrphanCheck = typeOfWork.getProfessionalCollection();
            for (Professional professionalCollectionOrphanCheckProfessional : professionalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TypeOfWork (" + typeOfWork + ") cannot be destroyed since the Professional " + professionalCollectionOrphanCheckProfessional + " in its professionalCollection field has a non-nullable typeOfWorkId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(typeOfWork);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TypeOfWork> findTypeOfWorkEntities() {
        return findTypeOfWorkEntities(true, -1, -1);
    }

    public List<TypeOfWork> findTypeOfWorkEntities(int maxResults, int firstResult) {
        return findTypeOfWorkEntities(false, maxResults, firstResult);
    }

    private List<TypeOfWork> findTypeOfWorkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypeOfWork.class));
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

    public TypeOfWork findTypeOfWork(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypeOfWork.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypeOfWorkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypeOfWork> rt = cq.from(TypeOfWork.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
