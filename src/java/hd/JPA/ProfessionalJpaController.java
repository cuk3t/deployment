/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.IllegalOrphanException;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.JPA.exceptions.PreexistingEntityException;
import hd.entity.Professional;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.TypeOfWork;
import hd.entity.User;
import hd.entity.SellerInfo;
import hd.entity.Project;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class ProfessionalJpaController implements Serializable {

    public ProfessionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Professional professional) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (professional.getProjectCollection() == null) {
            professional.setProjectCollection(new ArrayList<Project>());
        }
        List<String> illegalOrphanMessages = null;
        User userOrphanCheck = professional.getUser();
        if (userOrphanCheck != null) {
            Professional oldProfessionalOfUser = userOrphanCheck.getProfessional();
            if (oldProfessionalOfUser != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The User " + userOrphanCheck + " already has an item of type Professional whose user column cannot be null. Please make another selection for the user field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TypeOfWork typeOfWorkId = professional.getTypeOfWorkId();
            if (typeOfWorkId != null) {
                typeOfWorkId = em.getReference(typeOfWorkId.getClass(), typeOfWorkId.getId());
                professional.setTypeOfWorkId(typeOfWorkId);
            }
            User user = professional.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                professional.setUser(user);
            }
            SellerInfo sellerInfo = professional.getSellerInfo();
            if (sellerInfo != null) {
                sellerInfo = em.getReference(sellerInfo.getClass(), sellerInfo.getUserId());
                professional.setSellerInfo(sellerInfo);
            }
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : professional.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getProjectId());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            professional.setProjectCollection(attachedProjectCollection);
            em.persist(professional);
            if (typeOfWorkId != null) {
                typeOfWorkId.getProfessionalCollection().add(professional);
                typeOfWorkId = em.merge(typeOfWorkId);
            }
            if (user != null) {
                user.setProfessional(professional);
                user = em.merge(user);
            }
            if (sellerInfo != null) {
                Professional oldProfessionalOfSellerInfo = sellerInfo.getProfessional();
                if (oldProfessionalOfSellerInfo != null) {
                    oldProfessionalOfSellerInfo.setSellerInfo(null);
                    oldProfessionalOfSellerInfo = em.merge(oldProfessionalOfSellerInfo);
                }
                sellerInfo.setProfessional(professional);
                sellerInfo = em.merge(sellerInfo);
            }
            for (Project projectCollectionProject : professional.getProjectCollection()) {
                Professional oldProfessionalUserIdOfProjectCollectionProject = projectCollectionProject.getProfessionalUserId();
                projectCollectionProject.setProfessionalUserId(professional);
                projectCollectionProject = em.merge(projectCollectionProject);
                if (oldProfessionalUserIdOfProjectCollectionProject != null) {
                    oldProfessionalUserIdOfProjectCollectionProject.getProjectCollection().remove(projectCollectionProject);
                    oldProfessionalUserIdOfProjectCollectionProject = em.merge(oldProfessionalUserIdOfProjectCollectionProject);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProfessional(professional.getUserId()) != null) {
                throw new PreexistingEntityException("Professional " + professional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Professional professional) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professional persistentProfessional = em.find(Professional.class, professional.getUserId());
            TypeOfWork typeOfWorkIdOld = persistentProfessional.getTypeOfWorkId();
            TypeOfWork typeOfWorkIdNew = professional.getTypeOfWorkId();
            User userOld = persistentProfessional.getUser();
            User userNew = professional.getUser();
            SellerInfo sellerInfoOld = persistentProfessional.getSellerInfo();
            SellerInfo sellerInfoNew = professional.getSellerInfo();
            Collection<Project> projectCollectionOld = persistentProfessional.getProjectCollection();
            Collection<Project> projectCollectionNew = professional.getProjectCollection();
            List<String> illegalOrphanMessages = null;
            if (userNew != null && !userNew.equals(userOld)) {
                Professional oldProfessionalOfUser = userNew.getProfessional();
                if (oldProfessionalOfUser != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The User " + userNew + " already has an item of type Professional whose user column cannot be null. Please make another selection for the user field.");
                }
            }
            if (sellerInfoOld != null && !sellerInfoOld.equals(sellerInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SellerInfo " + sellerInfoOld + " since its professional field is not nullable.");
            }
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Project " + projectCollectionOldProject + " since its professionalUserId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (typeOfWorkIdNew != null) {
                typeOfWorkIdNew = em.getReference(typeOfWorkIdNew.getClass(), typeOfWorkIdNew.getId());
                professional.setTypeOfWorkId(typeOfWorkIdNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                professional.setUser(userNew);
            }
            if (sellerInfoNew != null) {
                sellerInfoNew = em.getReference(sellerInfoNew.getClass(), sellerInfoNew.getUserId());
                professional.setSellerInfo(sellerInfoNew);
            }
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getProjectId());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            professional.setProjectCollection(projectCollectionNew);
            professional = em.merge(professional);
            if (typeOfWorkIdOld != null && !typeOfWorkIdOld.equals(typeOfWorkIdNew)) {
                typeOfWorkIdOld.getProfessionalCollection().remove(professional);
                typeOfWorkIdOld = em.merge(typeOfWorkIdOld);
            }
            if (typeOfWorkIdNew != null && !typeOfWorkIdNew.equals(typeOfWorkIdOld)) {
                typeOfWorkIdNew.getProfessionalCollection().add(professional);
                typeOfWorkIdNew = em.merge(typeOfWorkIdNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.setProfessional(null);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.setProfessional(professional);
                userNew = em.merge(userNew);
            }
            if (sellerInfoNew != null && !sellerInfoNew.equals(sellerInfoOld)) {
                Professional oldProfessionalOfSellerInfo = sellerInfoNew.getProfessional();
                if (oldProfessionalOfSellerInfo != null) {
                    oldProfessionalOfSellerInfo.setSellerInfo(null);
                    oldProfessionalOfSellerInfo = em.merge(oldProfessionalOfSellerInfo);
                }
                sellerInfoNew.setProfessional(professional);
                sellerInfoNew = em.merge(sellerInfoNew);
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    Professional oldProfessionalUserIdOfProjectCollectionNewProject = projectCollectionNewProject.getProfessionalUserId();
                    projectCollectionNewProject.setProfessionalUserId(professional);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                    if (oldProfessionalUserIdOfProjectCollectionNewProject != null && !oldProfessionalUserIdOfProjectCollectionNewProject.equals(professional)) {
                        oldProfessionalUserIdOfProjectCollectionNewProject.getProjectCollection().remove(projectCollectionNewProject);
                        oldProfessionalUserIdOfProjectCollectionNewProject = em.merge(oldProfessionalUserIdOfProjectCollectionNewProject);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = professional.getUserId();
                if (findProfessional(id) == null) {
                    throw new NonexistentEntityException("The professional with id " + id + " no longer exists.");
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
            Professional professional;
            try {
                professional = em.getReference(Professional.class, id);
                professional.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SellerInfo sellerInfoOrphanCheck = professional.getSellerInfo();
            if (sellerInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Professional (" + professional + ") cannot be destroyed since the SellerInfo " + sellerInfoOrphanCheck + " in its sellerInfo field has a non-nullable professional field.");
            }
            Collection<Project> projectCollectionOrphanCheck = professional.getProjectCollection();
            for (Project projectCollectionOrphanCheckProject : projectCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Professional (" + professional + ") cannot be destroyed since the Project " + projectCollectionOrphanCheckProject + " in its projectCollection field has a non-nullable professionalUserId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TypeOfWork typeOfWorkId = professional.getTypeOfWorkId();
            if (typeOfWorkId != null) {
                typeOfWorkId.getProfessionalCollection().remove(professional);
                typeOfWorkId = em.merge(typeOfWorkId);
            }
            User user = professional.getUser();
            if (user != null) {
                user.setProfessional(null);
                user = em.merge(user);
            }
            em.remove(professional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Professional> findProfessionalEntities() {
        return findProfessionalEntities(true, -1, -1);
    }

    public List<Professional> findProfessionalEntities(int maxResults, int firstResult) {
        return findProfessionalEntities(false, maxResults, firstResult);
    }

    private List<Professional> findProfessionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Professional.class));
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

    public Professional findProfessional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Professional.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Professional> rt = cq.from(Professional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
