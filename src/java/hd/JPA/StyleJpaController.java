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
import hd.entity.Product;
import java.util.ArrayList;
import java.util.Collection;
import hd.entity.IdeaBookPhoto;
import hd.entity.Style;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class StyleJpaController implements Serializable {

    public StyleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Style style) {
        if (style.getProductCollection() == null) {
            style.setProductCollection(new ArrayList<Product>());
        }
        if (style.getIdeaBookPhotoCollection() == null) {
            style.setIdeaBookPhotoCollection(new ArrayList<IdeaBookPhoto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Product> attachedProductCollection = new ArrayList<Product>();
            for (Product productCollectionProductToAttach : style.getProductCollection()) {
                productCollectionProductToAttach = em.getReference(productCollectionProductToAttach.getClass(), productCollectionProductToAttach.getProductId());
                attachedProductCollection.add(productCollectionProductToAttach);
            }
            style.setProductCollection(attachedProductCollection);
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollection = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhotoToAttach : style.getIdeaBookPhotoCollection()) {
                ideaBookPhotoCollectionIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollection.add(ideaBookPhotoCollectionIdeaBookPhotoToAttach);
            }
            style.setIdeaBookPhotoCollection(attachedIdeaBookPhotoCollection);
            em.persist(style);
            for (Product productCollectionProduct : style.getProductCollection()) {
                Style oldStyleStyleIdOfProductCollectionProduct = productCollectionProduct.getStyleStyleId();
                productCollectionProduct.setStyleStyleId(style);
                productCollectionProduct = em.merge(productCollectionProduct);
                if (oldStyleStyleIdOfProductCollectionProduct != null) {
                    oldStyleStyleIdOfProductCollectionProduct.getProductCollection().remove(productCollectionProduct);
                    oldStyleStyleIdOfProductCollectionProduct = em.merge(oldStyleStyleIdOfProductCollectionProduct);
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionIdeaBookPhoto : style.getIdeaBookPhotoCollection()) {
                Style oldStyleIdOfIdeaBookPhotoCollectionIdeaBookPhoto = ideaBookPhotoCollectionIdeaBookPhoto.getStyleId();
                ideaBookPhotoCollectionIdeaBookPhoto.setStyleId(style);
                ideaBookPhotoCollectionIdeaBookPhoto = em.merge(ideaBookPhotoCollectionIdeaBookPhoto);
                if (oldStyleIdOfIdeaBookPhotoCollectionIdeaBookPhoto != null) {
                    oldStyleIdOfIdeaBookPhotoCollectionIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionIdeaBookPhoto);
                    oldStyleIdOfIdeaBookPhotoCollectionIdeaBookPhoto = em.merge(oldStyleIdOfIdeaBookPhotoCollectionIdeaBookPhoto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Style style) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Style persistentStyle = em.find(Style.class, style.getStyleId());
            Collection<Product> productCollectionOld = persistentStyle.getProductCollection();
            Collection<Product> productCollectionNew = style.getProductCollection();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionOld = persistentStyle.getIdeaBookPhotoCollection();
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionNew = style.getIdeaBookPhotoCollection();
            List<String> illegalOrphanMessages = null;
            for (Product productCollectionOldProduct : productCollectionOld) {
                if (!productCollectionNew.contains(productCollectionOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productCollectionOldProduct + " since its styleStyleId field is not nullable.");
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionOldIdeaBookPhoto : ideaBookPhotoCollectionOld) {
                if (!ideaBookPhotoCollectionNew.contains(ideaBookPhotoCollectionOldIdeaBookPhoto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IdeaBookPhoto " + ideaBookPhotoCollectionOldIdeaBookPhoto + " since its styleId field is not nullable.");
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
            style.setProductCollection(productCollectionNew);
            Collection<IdeaBookPhoto> attachedIdeaBookPhotoCollectionNew = new ArrayList<IdeaBookPhoto>();
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhotoToAttach : ideaBookPhotoCollectionNew) {
                ideaBookPhotoCollectionNewIdeaBookPhotoToAttach = em.getReference(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getClass(), ideaBookPhotoCollectionNewIdeaBookPhotoToAttach.getPhotoId());
                attachedIdeaBookPhotoCollectionNew.add(ideaBookPhotoCollectionNewIdeaBookPhotoToAttach);
            }
            ideaBookPhotoCollectionNew = attachedIdeaBookPhotoCollectionNew;
            style.setIdeaBookPhotoCollection(ideaBookPhotoCollectionNew);
            style = em.merge(style);
            for (Product productCollectionNewProduct : productCollectionNew) {
                if (!productCollectionOld.contains(productCollectionNewProduct)) {
                    Style oldStyleStyleIdOfProductCollectionNewProduct = productCollectionNewProduct.getStyleStyleId();
                    productCollectionNewProduct.setStyleStyleId(style);
                    productCollectionNewProduct = em.merge(productCollectionNewProduct);
                    if (oldStyleStyleIdOfProductCollectionNewProduct != null && !oldStyleStyleIdOfProductCollectionNewProduct.equals(style)) {
                        oldStyleStyleIdOfProductCollectionNewProduct.getProductCollection().remove(productCollectionNewProduct);
                        oldStyleStyleIdOfProductCollectionNewProduct = em.merge(oldStyleStyleIdOfProductCollectionNewProduct);
                    }
                }
            }
            for (IdeaBookPhoto ideaBookPhotoCollectionNewIdeaBookPhoto : ideaBookPhotoCollectionNew) {
                if (!ideaBookPhotoCollectionOld.contains(ideaBookPhotoCollectionNewIdeaBookPhoto)) {
                    Style oldStyleIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = ideaBookPhotoCollectionNewIdeaBookPhoto.getStyleId();
                    ideaBookPhotoCollectionNewIdeaBookPhoto.setStyleId(style);
                    ideaBookPhotoCollectionNewIdeaBookPhoto = em.merge(ideaBookPhotoCollectionNewIdeaBookPhoto);
                    if (oldStyleIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto != null && !oldStyleIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.equals(style)) {
                        oldStyleIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto.getIdeaBookPhotoCollection().remove(ideaBookPhotoCollectionNewIdeaBookPhoto);
                        oldStyleIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto = em.merge(oldStyleIdOfIdeaBookPhotoCollectionNewIdeaBookPhoto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = style.getStyleId();
                if (findStyle(id) == null) {
                    throw new NonexistentEntityException("The style with id " + id + " no longer exists.");
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
            Style style;
            try {
                style = em.getReference(Style.class, id);
                style.getStyleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The style with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Product> productCollectionOrphanCheck = style.getProductCollection();
            for (Product productCollectionOrphanCheckProduct : productCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Style (" + style + ") cannot be destroyed since the Product " + productCollectionOrphanCheckProduct + " in its productCollection field has a non-nullable styleStyleId field.");
            }
            Collection<IdeaBookPhoto> ideaBookPhotoCollectionOrphanCheck = style.getIdeaBookPhotoCollection();
            for (IdeaBookPhoto ideaBookPhotoCollectionOrphanCheckIdeaBookPhoto : ideaBookPhotoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Style (" + style + ") cannot be destroyed since the IdeaBookPhoto " + ideaBookPhotoCollectionOrphanCheckIdeaBookPhoto + " in its ideaBookPhotoCollection field has a non-nullable styleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(style);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Style> findStyleEntities() {
        return findStyleEntities(true, -1, -1);
    }

    public List<Style> findStyleEntities(int maxResults, int firstResult) {
        return findStyleEntities(false, maxResults, firstResult);
    }

    private List<Style> findStyleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Style.class));
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

    public Style findStyle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Style.class, id);
        } finally {
            em.close();
        }
    }

    public int getStyleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Style> rt = cq.from(Style.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
