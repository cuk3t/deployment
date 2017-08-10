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
import hd.entity.Category;
import hd.entity.IdeaBook;
import hd.entity.IdeaBookPhoto;
import hd.entity.Project;
import hd.entity.Style;
import hd.entity.IdeaBookPhotoRef;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class IdeaBookPhotoJpaController implements Serializable {

    public IdeaBookPhotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IdeaBookPhoto ideaBookPhoto) {
        if (ideaBookPhoto.getIdeaBookPhotoRefCollection() == null) {
            ideaBookPhoto.setIdeaBookPhotoRefCollection(new ArrayList<IdeaBookPhotoRef>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryId = ideaBookPhoto.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getCategoryId());
                ideaBookPhoto.setCategoryId(categoryId);
            }
            IdeaBook ideaBookId = ideaBookPhoto.getIdeaBookId();
            if (ideaBookId != null) {
                ideaBookId = em.getReference(ideaBookId.getClass(), ideaBookId.getIdeaBookId());
                ideaBookPhoto.setIdeaBookId(ideaBookId);
            }
            Project projectId = ideaBookPhoto.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getProjectId());
                ideaBookPhoto.setProjectId(projectId);
            }
            Style styleId = ideaBookPhoto.getStyleId();
            if (styleId != null) {
                styleId = em.getReference(styleId.getClass(), styleId.getStyleId());
                ideaBookPhoto.setStyleId(styleId);
            }
            Collection<IdeaBookPhotoRef> attachedIdeaBookPhotoRefCollection = new ArrayList<IdeaBookPhotoRef>();
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach : ideaBookPhoto.getIdeaBookPhotoRefCollection()) {
                ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach = em.getReference(ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach.getClass(), ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach.getIdeaBookPhotoRefPK());
                attachedIdeaBookPhotoRefCollection.add(ideaBookPhotoRefCollectionIdeaBookPhotoRefToAttach);
            }
            ideaBookPhoto.setIdeaBookPhotoRefCollection(attachedIdeaBookPhotoRefCollection);
            em.persist(ideaBookPhoto);
            if (categoryId != null) {
                categoryId.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                categoryId = em.merge(categoryId);
            }
            if (ideaBookId != null) {
                ideaBookId.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                ideaBookId = em.merge(ideaBookId);
            }
            if (projectId != null) {
                projectId.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                projectId = em.merge(projectId);
            }
            if (styleId != null) {
                styleId.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                styleId = em.merge(styleId);
            }
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionIdeaBookPhotoRef : ideaBookPhoto.getIdeaBookPhotoRefCollection()) {
                IdeaBookPhoto oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef = ideaBookPhotoRefCollectionIdeaBookPhotoRef.getIdeaBookPhoto();
                ideaBookPhotoRefCollectionIdeaBookPhotoRef.setIdeaBookPhoto(ideaBookPhoto);
                ideaBookPhotoRefCollectionIdeaBookPhotoRef = em.merge(ideaBookPhotoRefCollectionIdeaBookPhotoRef);
                if (oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef != null) {
                    oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRefCollectionIdeaBookPhotoRef);
                    oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef = em.merge(oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionIdeaBookPhotoRef);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IdeaBookPhoto ideaBookPhoto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdeaBookPhoto persistentIdeaBookPhoto = em.find(IdeaBookPhoto.class, ideaBookPhoto.getPhotoId());
            Category categoryIdOld = persistentIdeaBookPhoto.getCategoryId();
            Category categoryIdNew = ideaBookPhoto.getCategoryId();
            IdeaBook ideaBookIdOld = persistentIdeaBookPhoto.getIdeaBookId();
            IdeaBook ideaBookIdNew = ideaBookPhoto.getIdeaBookId();
            Project projectIdOld = persistentIdeaBookPhoto.getProjectId();
            Project projectIdNew = ideaBookPhoto.getProjectId();
            Style styleIdOld = persistentIdeaBookPhoto.getStyleId();
            Style styleIdNew = ideaBookPhoto.getStyleId();
            Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollectionOld = persistentIdeaBookPhoto.getIdeaBookPhotoRefCollection();
            Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollectionNew = ideaBookPhoto.getIdeaBookPhotoRefCollection();
            List<String> illegalOrphanMessages = null;
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionOldIdeaBookPhotoRef : ideaBookPhotoRefCollectionOld) {
                if (!ideaBookPhotoRefCollectionNew.contains(ideaBookPhotoRefCollectionOldIdeaBookPhotoRef)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IdeaBookPhotoRef " + ideaBookPhotoRefCollectionOldIdeaBookPhotoRef + " since its ideaBookPhoto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getCategoryId());
                ideaBookPhoto.setCategoryId(categoryIdNew);
            }
            if (ideaBookIdNew != null) {
                ideaBookIdNew = em.getReference(ideaBookIdNew.getClass(), ideaBookIdNew.getIdeaBookId());
                ideaBookPhoto.setIdeaBookId(ideaBookIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getProjectId());
                ideaBookPhoto.setProjectId(projectIdNew);
            }
            if (styleIdNew != null) {
                styleIdNew = em.getReference(styleIdNew.getClass(), styleIdNew.getStyleId());
                ideaBookPhoto.setStyleId(styleIdNew);
            }
            Collection<IdeaBookPhotoRef> attachedIdeaBookPhotoRefCollectionNew = new ArrayList<IdeaBookPhotoRef>();
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach : ideaBookPhotoRefCollectionNew) {
                ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach = em.getReference(ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach.getClass(), ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach.getIdeaBookPhotoRefPK());
                attachedIdeaBookPhotoRefCollectionNew.add(ideaBookPhotoRefCollectionNewIdeaBookPhotoRefToAttach);
            }
            ideaBookPhotoRefCollectionNew = attachedIdeaBookPhotoRefCollectionNew;
            ideaBookPhoto.setIdeaBookPhotoRefCollection(ideaBookPhotoRefCollectionNew);
            ideaBookPhoto = em.merge(ideaBookPhoto);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                categoryIdNew = em.merge(categoryIdNew);
            }
            if (ideaBookIdOld != null && !ideaBookIdOld.equals(ideaBookIdNew)) {
                ideaBookIdOld.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                ideaBookIdOld = em.merge(ideaBookIdOld);
            }
            if (ideaBookIdNew != null && !ideaBookIdNew.equals(ideaBookIdOld)) {
                ideaBookIdNew.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                ideaBookIdNew = em.merge(ideaBookIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                projectIdNew = em.merge(projectIdNew);
            }
            if (styleIdOld != null && !styleIdOld.equals(styleIdNew)) {
                styleIdOld.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                styleIdOld = em.merge(styleIdOld);
            }
            if (styleIdNew != null && !styleIdNew.equals(styleIdOld)) {
                styleIdNew.getIdeaBookPhotoCollection().add(ideaBookPhoto);
                styleIdNew = em.merge(styleIdNew);
            }
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionNewIdeaBookPhotoRef : ideaBookPhotoRefCollectionNew) {
                if (!ideaBookPhotoRefCollectionOld.contains(ideaBookPhotoRefCollectionNewIdeaBookPhotoRef)) {
                    IdeaBookPhoto oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef = ideaBookPhotoRefCollectionNewIdeaBookPhotoRef.getIdeaBookPhoto();
                    ideaBookPhotoRefCollectionNewIdeaBookPhotoRef.setIdeaBookPhoto(ideaBookPhoto);
                    ideaBookPhotoRefCollectionNewIdeaBookPhotoRef = em.merge(ideaBookPhotoRefCollectionNewIdeaBookPhotoRef);
                    if (oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef != null && !oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef.equals(ideaBookPhoto)) {
                        oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef.getIdeaBookPhotoRefCollection().remove(ideaBookPhotoRefCollectionNewIdeaBookPhotoRef);
                        oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef = em.merge(oldIdeaBookPhotoOfIdeaBookPhotoRefCollectionNewIdeaBookPhotoRef);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ideaBookPhoto.getPhotoId();
                if (findIdeaBookPhoto(id) == null) {
                    throw new NonexistentEntityException("The ideaBookPhoto with id " + id + " no longer exists.");
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
            IdeaBookPhoto ideaBookPhoto;
            try {
                ideaBookPhoto = em.getReference(IdeaBookPhoto.class, id);
                ideaBookPhoto.getPhotoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ideaBookPhoto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<IdeaBookPhotoRef> ideaBookPhotoRefCollectionOrphanCheck = ideaBookPhoto.getIdeaBookPhotoRefCollection();
            for (IdeaBookPhotoRef ideaBookPhotoRefCollectionOrphanCheckIdeaBookPhotoRef : ideaBookPhotoRefCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IdeaBookPhoto (" + ideaBookPhoto + ") cannot be destroyed since the IdeaBookPhotoRef " + ideaBookPhotoRefCollectionOrphanCheckIdeaBookPhotoRef + " in its ideaBookPhotoRefCollection field has a non-nullable ideaBookPhoto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Category categoryId = ideaBookPhoto.getCategoryId();
            if (categoryId != null) {
                categoryId.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                categoryId = em.merge(categoryId);
            }
            IdeaBook ideaBookId = ideaBookPhoto.getIdeaBookId();
            if (ideaBookId != null) {
                ideaBookId.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                ideaBookId = em.merge(ideaBookId);
            }
            Project projectId = ideaBookPhoto.getProjectId();
            if (projectId != null) {
                projectId.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                projectId = em.merge(projectId);
            }
            Style styleId = ideaBookPhoto.getStyleId();
            if (styleId != null) {
                styleId.getIdeaBookPhotoCollection().remove(ideaBookPhoto);
                styleId = em.merge(styleId);
            }
            em.remove(ideaBookPhoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IdeaBookPhoto> findIdeaBookPhotoEntities() {
        return findIdeaBookPhotoEntities(true, -1, -1);
    }

    public List<IdeaBookPhoto> findIdeaBookPhotoEntities(int maxResults, int firstResult) {
        return findIdeaBookPhotoEntities(false, maxResults, firstResult);
    }

    private List<IdeaBookPhoto> findIdeaBookPhotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IdeaBookPhoto.class));
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

    public IdeaBookPhoto findIdeaBookPhoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IdeaBookPhoto.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdeaBookPhotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IdeaBookPhoto> rt = cq.from(IdeaBookPhoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<IdeaBookPhoto> getPhotosByIdeabookId(int ideabookId) {
        EntityManager em = getEntityManager();
        List<IdeaBookPhoto> list = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadPhotosByIdeabookId");
            query.setParameter("ideaBookId", ideabookId);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public IdeaBookPhoto getFirstPhotoByIdeabookIdAndStatus(int ideabookId, int status) {
        EntityManager em = getEntityManager();
        IdeaBookPhoto photo = new IdeaBookPhoto();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadFirstPhotoByStatusAndIdeabookId");
            query.setMaxResults(1);
            query.setParameter("ideaBookId", ideabookId);
            query.setParameter("status", status);
            photo = (IdeaBookPhoto) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return photo;
    }

    public List<IdeaBookPhoto> getPhotosByProjectId(int projectId) {
        EntityManager em = getEntityManager();
        List<IdeaBookPhoto> list = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadPhotosByProjectId");
            query.setParameter("projectId", projectId);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

//    public List<Integer> getIdeabookPhotosByStatus(int status) {
//        EntityManager em = getEntityManager();
//        List<Integer> list = new ArrayList<>();
//        try {
//            Query query = em.createNamedQuery("IdeaBookPhoto.loadIdeabookPhotoByStatus");
//            query.setParameter("status", status);
//            list = query.getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        return list;
//    }
//    public List<String> getUrlIdeabookPhotosByStatus(int status) {
//        EntityManager em = getEntityManager();
//        List<String> list = new ArrayList<>();
//        try {
//            Query query = em.createNamedQuery("IdeaBookPhoto.loadURLIdeabookPhotoByStatus");
//            query.setParameter("status", status);
//            list = query.getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        return list;
//    }
    public List<String> getCategoryIdeabookByIdeabookId(int ideabookId) {
        EntityManager em = getEntityManager();
        List<String> category = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadCategoryByIdeabookId");
            query.setParameter("ideaBookId", ideabookId);
            category = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return category;
    }

    public List<String> getCategoryProjectByProjectId(int projectId) {
        EntityManager em = getEntityManager();
        List<String> category = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadCategoryByProjectId");
            query.setParameter("projectId", projectId);
            category = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return category;
    }

    public List<String> getStyleIdeabookByIdeabookId(int ideabookId) {
        EntityManager em = getEntityManager();
        List<String> category = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadStyleByIdeabookId");
            query.setParameter("ideaBookId", ideabookId);
            category = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return category;
    }

    public List<String> getStyleProjectByProjectId(int projectId) {
        EntityManager em = getEntityManager();
        List<String> category = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadStyleByProjectId");
            query.setParameter("projectId", projectId);
            category = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return category;
    }

    public String getFirstPhotoByIdeabookId(int ideabookId) {
        EntityManager em = getEntityManager();
        String urlPhoto = "";
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadFirstPhotoByIdeabookId");
            query.setMaxResults(1);
            query.setParameter("ideaBookId", ideabookId);
            urlPhoto = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return urlPhoto;
    }

    public String getFirstPhotoByProjectId(int projectId) {
        EntityManager em = getEntityManager();
        String urlPhoto = "";
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadFirstPhotoByProjectId");
            query.setMaxResults(1);
            query.setParameter("projectId", projectId);
            urlPhoto = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return urlPhoto;
    }

    public String getFirstApprovedPhotoByProjectId(int projectId) {
        EntityManager em = getEntityManager();
        String urlPhoto = "";
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadFirstApprovedPhotoByProjectId");
            query.setMaxResults(1);
            query.setParameter("projectId", projectId);
            urlPhoto = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return urlPhoto;
    }

    public boolean isApprovationIdeabook(int ideabookId) {
        EntityManager em = getEntityManager();
        boolean flag = true;
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.checkAllPhotoByIdeabookId");
            query.setParameter("ideaBookId", ideabookId);
            if (query.getResultList().size() > 0) {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return flag;
    }

    public boolean isApprovationProject(int projectId) {
        EntityManager em = getEntityManager();
        boolean flag = true;
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.checkAllPhotoByProjectId");
            query.setParameter("projectId", projectId);
            if (query.getResultList().size() > 0) {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return flag;
    }

    public List<Integer> findProjectsHavePhotoApproved(int userId) {
        EntityManager em = getEntityManager();
        List<Integer> projectsId = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.findProjectsHavePhotoApprovedByUserId");
            query.setParameter("userId", userId);
            projectsId = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return projectsId;
    }

    public List<IdeaBookPhoto> getApprovedPhotosByProjectId(int projectId) {
        EntityManager em = getEntityManager();
        List<IdeaBookPhoto> photos = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("IdeaBookPhoto.loadApprovedPhotosByProjectId");
            query.setMaxResults(1);
            query.setParameter("projectId", projectId);
            photos = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return photos;
    }
}
