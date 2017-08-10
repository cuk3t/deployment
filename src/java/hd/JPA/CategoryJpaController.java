/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.IllegalOrphanException;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.entity.Category;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Product;
import java.util.ArrayList;
import java.util.Collection;
import hd.entity.Tracking;
import hd.entity.IdeaBookPhoto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class CategoryJpaController implements Serializable {

    public CategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Category category) {
        if (category.getProductCollection() == null) {
            category.setProductCollection(new ArrayList<Product>());
        }
        if (category.getTrackingCollection() == null) {
            category.setTrackingCollection(new ArrayList<Tracking>());
        }
        if (category.getIdeaBookPhotoCollection() == null) {
            category.setIdeaBookPhotoCollection(new ArrayList<IdeaBookPhoto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Product> attachedProductCollection = new ArrayList<Product>();
            for (Product productCollectionProductToAttach : category.getProductCollection()) {
                productCollectionProductToAttach = em.getReference(productCollectionProductToAttach.getClass(), productCollectionProductToAttach.getProductId());
                attachedProductCollection.add(productCollectionProductToAttach);
            }
            category.setProductCollection(attachedProductCollection);
            Collection<Tracking> attachedTrackingCollection = new ArrayList<Tracking>();
            for (Tracking trackingCollectionTrackingToAttach : category.getTrackingCollection()) {
                trackingCollectionTrackingToAttach = em.getReference(trackingCollectionTrackingToAttach.getClass(), trackingCollectionTrackingToAttach.getTrackingPK());
                attachedTrackingCollection.add(trackingCollectionTrackingToAttach);
            }
            category.setTrackingCollection(attachedTrackingCollection);
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollection = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhotoToAttach : category.getIdeaBookPhotoCollection()) {
                ideaBookPhotoCollectionIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollection.add(ideaBookPhotoCollectionIdeaBookPhotoToAttach);
            }
            category.setIdeaBookPhotoCollection(attachedIdeaBookPhotoCollection);
            em.persist(category);
            for (Product productCollectionProduct : category.getProductCollection()) {
                Category oldCategoryCategoryIdOfProductCollectionProduct = productCollectionProduct.getCategoryCategoryId();
                productCollectionProduct.setCategoryCategoryId(category);
                productCollectionProduct = em.merge(productCollectionProduct);
                if (oldCategoryCategoryIdOfProductCollectionProduct != null) {
                    oldCategoryCategoryIdOfProductCollectionProduct.getProductCollection().remove(productCollectionProduct);
                    oldCategoryCategoryIdOfProductCollectionProduct = em.merge(oldCategoryCategoryIdOfProductCollectionProduct);
                }
            }
            for (Tracking trackingCollectionTracking : category.getTrackingCollection()) {
                Category oldCategoryOfTrackingCollectionTracking = trackingCollectionTracking.getCategory();
                trackingCollectionTracking.setCategory(category);
                trackingCollectionTracking = em.merge(trackingCollectionTracking);
                if (oldCategoryOfTrackingCollectionTracking != null) {
                    oldCategoryOfTrackingCollectionTracking.getTrackingCollection().remove(trackingCollectionTracking);
                    oldCategoryOfTrackingCollectionTracking = em.merge(oldCategoryOfTrackingCollectionTracking);
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhoto : category.getIdeaBookPhotoCollection()) {
                Category oldCategoryIdOfIdeaBookPhotoCollectionIdeaBookPhoto = ideaBookPhotoCollectionIdeaBookPhoto.getCategoryId();
                ideaBookPhotoCollectionIdeaBookPhoto.setCategoryId(category);
                ideaBookPhotoCollectionIdeaBookPhoto = em.merge(ideaBookPhotoCollectionIdeaBookPhoto);
                if (oldCategoryIdOfIdeaBookPhotoCollectionIdeaBookPhoto != null) {
                    oldCategoryIdOfIdeaBookPhotoCollectionIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionIdeaBookPhoto);
                    oldCategoryIdOfIdeaBookPhotoCollectionIdeaBookPhoto = em.merge(oldCategoryIdOfIdeaBookPhotoCollectionIdeaBookPhoto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Category category) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category persistentCategory = em.find(Category.class, category.getCategoryId());
            Collection<Product> productCollectionOld = persistentCategory.getProductCollection();
            Collection<Product> productCollectionNew = category.getProductCollection();
            Collection<Tracking> trackingCollectionOld = persistentCategory.getTrackingCollection();
            Collection<Tracking> trackingCollectionNew = category.getTrackingCollection();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionOld = persistentCategory.getIdeaBookPhotoCollection();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionNew = category.getIdeaBookPhotoCollection();
            List<String> illegalOrphanMessages = null;
            for (Product productCollectionOldProduct : productCollectionOld) {
                if (!productCollectionNew.contains(productCollectionOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productCollectionOldProduct + " since its categoryCategoryId field is not nullable.");
                }
            }
            for (Tracking trackingCollectionOldTracking : trackingCollectionOld) {
                if (!trackingCollectionNew.contains(trackingCollectionOldTracking)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tracking " + trackingCollectionOldTracking + " since its category field is not nullable.");
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionOldIdeaBookPhoto : ideaBookPhotoCollectionOld) {
                if (!ideaBookPhotoCollectionNew.contains(ideaBookPhotoCollectionOldIdeaBookPhoto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IdeaBookPhoto " + ideaBookPhotoCollectionOldIdeaBookPhoto + " since its categoryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Product> attachedProductCollectionNew = new ArrayList<Product>();
            for (Product productCollectionNewProductToAttach : productCollectionNew) {
                productCollectionNewProductToAttach = em.getReference(productCollectionNewProductToAttach.getClass(), productCollectionNewProductToAttach.getProductId());
                attachedProductCollectionNew.add(productCollectionNewProductToAttach);
            }
            productCollectionNew = attachedProductCollectionNew;
            category.setProductCollection(productCollectionNew);
            Collection<Tracking> attachedTrackingCollectionNew = new ArrayList<Tracking>();
            for (Tracking trackingCollectionNewTrackingToAttach : trackingCollectionNew) {
                trackingCollectionNewTrackingToAttach = em.getReference(trackingCollectionNewTrackingToAttach.getClass(), trackingCollectionNewTrackingToAttach.getTrackingPK());
                attachedTrackingCollectionNew.add(trackingCollectionNewTrackingToAttach);
            }
            trackingCollectionNew = attachedTrackingCollectionNew;
            category.setTrackingCollection(trackingCollectionNew);
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollectionNew = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhotoToAttach : ideaBookPhotoCollectionNew) {
                ideaBookPhotoCollectionNewIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollectionNew.add(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach);
            }
            ideaBookPhotoCollectionNew = attachedIdeaBookPhotoCollectionNew;
            category.setIdeaBookPhotoCollection(ideaBookPhotoCollectionNew);
            category = em.merge(category);
            for (Product productCollectionNewProduct : productCollectionNew) {
                if (!productCollectionOld.contains(productCollectionNewProduct)) {
                    Category oldCategoryCategoryIdOfProductCollectionNewProduct = productCollectionNewProduct.getCategoryCategoryId();
                    productCollectionNewProduct.setCategoryCategoryId(category);
                    productCollectionNewProduct = em.merge(productCollectionNewProduct);
                    if (oldCategoryCategoryIdOfProductCollectionNewProduct != null && !oldCategoryCategoryIdOfProductCollectionNewProduct.equals(category)) {
                        oldCategoryCategoryIdOfProductCollectionNewProduct.getProductCollection().remove(productCollectionNewProduct);
                        oldCategoryCategoryIdOfProductCollectionNewProduct = em.merge(oldCategoryCategoryIdOfProductCollectionNewProduct);
                    }
                }
            }
            for (Tracking trackingCollectionNewTracking : trackingCollectionNew) {
                if (!trackingCollectionOld.contains(trackingCollectionNewTracking)) {
                    Category oldCategoryOfTrackingCollectionNewTracking = trackingCollectionNewTracking.getCategory();
                    trackingCollectionNewTracking.setCategory(category);
                    trackingCollectionNewTracking = em.merge(trackingCollectionNewTracking);
                    if (oldCategoryOfTrackingCollectionNewTracking != null && !oldCategoryOfTrackingCollectionNewTracking.equals(category)) {
                        oldCategoryOfTrackingCollectionNewTracking.getTrackingCollection().remove(trackingCollectionNewTracking);
                        oldCategoryOfTrackingCollectionNewTracking = em.merge(oldCategoryOfTrackingCollectionNewTracking);
                    }
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhoto : ideaBookPhotoCollectionNew) {
                if (!ideaBookPhotoCollectionOld.contains(ideaBookPhotoCollectionNewIdeaBookPhoto)) {
                    Category oldCategoryIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = ideaBookPhotoCollectionNewIdeaBookPhoto.getCategoryId();
                    ideaBookPhotoCollectionNewIdeaBookPhoto.setCategoryId(category);
                    ideaBookPhotoCollectionNewIdeaBookPhoto = em.merge(ideaBookPhotoCollectionNewIdeaBookPhoto);
                    if (oldCategoryIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto != null && !oldCategoryIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.equals(category)) {
                        oldCategoryIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionNewIdeaBookPhoto);
                        oldCategoryIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = em.merge(oldCategoryIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = category.getCategoryId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
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
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getCategoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Product> productCollectionOrphanCheck = category.getProductCollection();
            for (Product productCollectionOrphanCheckProduct : productCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the Product " + productCollectionOrphanCheckProduct + " in its productCollection field has a non-nullable categoryCategoryId field.");
            }
            Collection<Tracking> trackingCollectionOrphanCheck = category.getTrackingCollection();
            for (Tracking trackingCollectionOrphanCheckTracking : trackingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the Tracking " + trackingCollectionOrphanCheckTracking + " in its trackingCollection field has a non-nullable category field.");
            }
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionOrphanCheck = category.getIdeaBookPhotoCollection();
            for (IdeaBookPhoto ideaBookPhotoCollectionOrphanCheckIdeaBookPhoto : ideaBookPhotoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the IdeaBookPhoto " + ideaBookPhotoCollectionOrphanCheckIdeaBookPhoto + " in its ideaBookPhotoCollection field has a non-nullable categoryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(category);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> findCategoryEntities() {
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
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

    public Category findCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
