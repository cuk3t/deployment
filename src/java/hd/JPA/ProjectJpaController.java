/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.NonexistentEntityException;
import hd.controller.ProjectIdNameDTO;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Professional;
import hd.entity.IdeaBookPhoto;
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
public class ProjectJpaController implements Serializable {

    public ProjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) {
        if (project.getIdeaBookPhotoCollection() == null) {
            project.setIdeaBookPhotoCollection(new ArrayList<IdeaBookPhoto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professional professionalUserId = project.getProfessionalUserId();
            if (professionalUserId != null) {
                professionalUserId = em.getReference(professionalUserId.getClass(), professionalUserId.getUserId());
                project.setProfessionalUserId(professionalUserId);
            }
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollection = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhotoToAttach : project.getIdeaBookPhotoCollection()) {
                ideaBookPhotoCollectionIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollection.add(ideaBookPhotoCollectionIdeaBookPhotoToAttach);
            }
            project.setIdeaBookPhotoCollection(attachedIdeaBookPhotoCollection);
            em.persist(project);
            if (professionalUserId != null) {
                professionalUserId.getProjectCollection().add(project);
                professionalUserId = em.merge(professionalUserId);
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhoto : project.getIdeaBookPhotoCollection()) {
                Project oldProjectIdOfIdeaBookPhotoCollectionIdeaBookPhoto = ideaBookPhotoCollectionIdeaBookPhoto.getProjectId();
                ideaBookPhotoCollectionIdeaBookPhoto.setProjectId(project);
                ideaBookPhotoCollectionIdeaBookPhoto = em.merge(ideaBookPhotoCollectionIdeaBookPhoto);
                if (oldProjectIdOfIdeaBookPhotoCollectionIdeaBookPhoto != null) {
                    oldProjectIdOfIdeaBookPhotoCollectionIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionIdeaBookPhoto);
                    oldProjectIdOfIdeaBookPhotoCollectionIdeaBookPhoto = em.merge(oldProjectIdOfIdeaBookPhotoCollectionIdeaBookPhoto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getProjectId());
            Professional professionalUserIdOld = persistentProject.getProfessionalUserId();
            Professional professionalUserIdNew = project.getProfessionalUserId();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionOld = persistentProject.getIdeaBookPhotoCollection();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionNew = project.getIdeaBookPhotoCollection();
            if (professionalUserIdNew != null) {
                professionalUserIdNew = em.getReference(professionalUserIdNew.getClass(), professionalUserIdNew.getUserId());
                project.setProfessionalUserId(professionalUserIdNew);
            }
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollectionNew = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhotoToAttach : ideaBookPhotoCollectionNew) {
                ideaBookPhotoCollectionNewIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollectionNew.add(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach);
            }
            ideaBookPhotoCollectionNew = attachedIdeaBookPhotoCollectionNew;
            project.setIdeaBookPhotoCollection(ideaBookPhotoCollectionNew);
            project = em.merge(project);
            if (professionalUserIdOld != null && !professionalUserIdOld.equals(professionalUserIdNew)) {
                professionalUserIdOld.getProjectCollection().remove(project);
                professionalUserIdOld = em.merge(professionalUserIdOld);
            }
            if (professionalUserIdNew != null && !professionalUserIdNew.equals(professionalUserIdOld)) {
                professionalUserIdNew.getProjectCollection().add(project);
                professionalUserIdNew = em.merge(professionalUserIdNew);
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionOldIdeaBookPhoto : ideaBookPhotoCollectionOld) {
                if (!ideaBookPhotoCollectionNew.contains(ideaBookPhotoCollectionOldIdeaBookPhoto)) {
                    ideaBookPhotoCollectionOldIdeaBookPhoto.setProjectId(null);
                    ideaBookPhotoCollectionOldIdeaBookPhoto = em.merge(ideaBookPhotoCollectionOldIdeaBookPhoto);
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhoto : ideaBookPhotoCollectionNew) {
                if (!ideaBookPhotoCollectionOld.contains(ideaBookPhotoCollectionNewIdeaBookPhoto)) {
                    Project oldProjectIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = ideaBookPhotoCollectionNewIdeaBookPhoto.getProjectId();
                    ideaBookPhotoCollectionNewIdeaBookPhoto.setProjectId(project);
                    ideaBookPhotoCollectionNewIdeaBookPhoto = em.merge(ideaBookPhotoCollectionNewIdeaBookPhoto);
                    if (oldProjectIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto != null && !oldProjectIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.equals(project)) {
                        oldProjectIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionNewIdeaBookPhoto);
                        oldProjectIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = em.merge(oldProjectIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = project.getProjectId();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
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
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getProjectId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            Professional professionalUserId = project.getProfessionalUserId();
            if (professionalUserId != null) {
                professionalUserId.getProjectCollection().remove(project);
                professionalUserId = em.merge(professionalUserId);
            }
            Collection<IdeaBookPhoto> ideaBookPhotoCollection = project.getIdeaBookPhotoCollection();
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhoto : ideaBookPhotoCollection) {
                ideaBookPhotoCollectionIdeaBookPhoto.setProjectId(null);
                ideaBookPhotoCollectionIdeaBookPhoto = em.merge(ideaBookPhotoCollectionIdeaBookPhoto);
            }
            em.remove(project);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
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

    public Project findProject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<ProjectIdNameDTO> getProjectIdAndNameByUserId(int userId) {
        EntityManager em = getEntityManager();
        List<ProjectIdNameDTO> list = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Project.loadIdAndName");
            query.setParameter("userId", userId);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

//    public List<Project> getProjectByUserId(int userId) {
//        EntityManager em = getEntityManager();
//        List<Project> list = new ArrayList<>();
//        try {
//            Query query = em.createNamedQuery("Project.findByUserId");
//            query.setParameter("userId", userId);
//            list = query.getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        return list;
//    }
    public List<Project> getProjectsByStatus(int status) {
        EntityManager em = getEntityManager();
        List<Project> list = new ArrayList<Project>();
        try {
            Query query = em.createNamedQuery("Project.loadProjectByStatus");
            query.setParameter("status", status);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public List<Project> findProjectsByUserId(int userId) {
        EntityManager em = getEntityManager();
        List<Project> list = new ArrayList<Project>();
        try {
            Query query = em.createNamedQuery("Project.findProjectByUserId");
            query.setParameter("userId", userId);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public boolean isExistedInMyProjects(int userId, String projectName) {
        boolean flag = true;
        EntityManager em = getEntityManager();
        List<Project> list = new ArrayList<Project>();
        try {
            Query query = em.createNamedQuery("Project.isExistedInMyProjects");
            query.setParameter("userId", userId);
            query.setParameter("projectName", projectName);
            list = query.getResultList();
            flag = list.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return flag;
    }

    public List<Project> findOtherProjectsByUserId(Integer userId) {
        EntityManager em = getEntityManager();
        List<Project> list = new ArrayList<Project>();
        try {
            Query query = em.createNamedQuery("Project.findOtherProjectByUserId");
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
