/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.IllegalOrphanException;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.JPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Professional;
import hd.entity.Product;
import hd.entity.SellerInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class SellerInfoJpaController implements Serializable {

    public SellerInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SellerInfo sellerInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sellerInfo.getProductCollection() == null) {
            sellerInfo.setProductCollection(new ArrayList<Product>());
        }
        List<String> illegalOrphanMessages = null;
        Professional professionalOrphanCheck = sellerInfo.getProfessional();
        if (professionalOrphanCheck != null) {
            SellerInfo oldSellerInfoOfProfessional = professionalOrphanCheck.getSellerInfo();
            if (oldSellerInfoOfProfessional != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Professional " + professionalOrphanCheck + " already has an item of type SellerInfo whose professional column cannot be null. Please make another selection for the professional field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professional professional = sellerInfo.getProfessional();
            if (professional != null) {
                professional = em.getReference(professional.getClass(), professional.getUserId());
                sellerInfo.setProfessional(professional);
            }
            Collection<Product> attachedProductCollection = new ArrayList<Product>();
            for (Product productCollectionProductToAttach : sellerInfo.getProductCollection()) {
                productCollectionProductToAttach = em.getReference(productCollectionProductToAttach.getClass(), productCollectionProductToAttach.getProductId());
                attachedProductCollection.add(productCollectionProductToAttach);
            }
            sellerInfo.setProductCollection(attachedProductCollection);
            em.persist(sellerInfo);
            if (professional != null) {
                professional.setSellerInfo(sellerInfo);
                professional = em.merge(professional);
            }
            for (Product productCollectionProduct : sellerInfo.getProductCollection()) {
                SellerInfo oldUserIdOfProductCollectionProduct = productCollectionProduct.getUserId();
                productCollectionProduct.setUserId(sellerInfo);
                productCollectionProduct = em.merge(productCollectionProduct);
                if (oldUserIdOfProductCollectionProduct != null) {
                    oldUserIdOfProductCollectionProduct.getProductCollection().remove(productCollectionProduct);
                    oldUserIdOfProductCollectionProduct = em.merge(oldUserIdOfProductCollectionProduct);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSellerInfo(sellerInfo.getUserId()) != null) {
                throw new PreexistingEntityException("SellerInfo " + sellerInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SellerInfo sellerInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SellerInfo persistentSellerInfo = em.find(SellerInfo.class, sellerInfo.getUserId());
            Professional professionalOld = persistentSellerInfo.getProfessional();
            Professional professionalNew = sellerInfo.getProfessional();
            Collection<Product> productCollectionOld = persistentSellerInfo.getProductCollection();
            Collection<Product> productCollectionNew = sellerInfo.getProductCollection();
            List<String> illegalOrphanMessages = null;
            if (professionalNew != null && !professionalNew.equals(professionalOld)) {
                SellerInfo oldSellerInfoOfProfessional = professionalNew.getSellerInfo();
                if (oldSellerInfoOfProfessional != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Professional " + professionalNew + " already has an item of type SellerInfo whose professional column cannot be null. Please make another selection for the professional field.");
                }
            }
            for (Product productCollectionOldProduct : productCollectionOld) {
                if (!productCollectionNew.contains(productCollectionOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productCollectionOldProduct + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (professionalNew != null) {
                professionalNew = em.getReference(professionalNew.getClass(), professionalNew.getUserId());
                sellerInfo.setProfessional(professionalNew);
            }
            Collection<Product> attachedProductCollectionNew = new ArrayList<Product>();
            for (Product productCollectionNewProductToAttach : productCollectionNew) {
                productCollectionNewProductToAttach = em.getReference(productCollectionNewProductToAttach.getClass(), productCollectionNewProductToAttach.getProductId());
                attachedProductCollectionNew.add(productCollectionNewProductToAttach);
            }
            productCollectionNew = attachedProductCollectionNew;
            sellerInfo.setProductCollection(productCollectionNew);
            sellerInfo = em.merge(sellerInfo);
            if (professionalOld != null && !professionalOld.equals(professionalNew)) {
                professionalOld.setSellerInfo(null);
                professionalOld = em.merge(professionalOld);
            }
            if (professionalNew != null && !professionalNew.equals(professionalOld)) {
                professionalNew.setSellerInfo(sellerInfo);
                professionalNew = em.merge(professionalNew);
            }
            for (Product productCollectionNewProduct : productCollectionNew) {
                if (!productCollectionOld.contains(productCollectionNewProduct)) {
                    SellerInfo oldUserIdOfProductCollectionNewProduct = productCollectionNewProduct.getUserId();
                    productCollectionNewProduct.setUserId(sellerInfo);
                    productCollectionNewProduct = em.merge(productCollectionNewProduct);
                    if (oldUserIdOfProductCollectionNewProduct != null && !oldUserIdOfProductCollectionNewProduct.equals(sellerInfo)) {
                        oldUserIdOfProductCollectionNewProduct.getProductCollection().remove(productCollectionNewProduct);
                        oldUserIdOfProductCollectionNewProduct = em.merge(oldUserIdOfProductCollectionNewProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sellerInfo.getUserId();
                if (findSellerInfo(id) == null) {
                    throw new NonexistentEntityException("The sellerInfo with id " + id + " no longer exists.");
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
            SellerInfo sellerInfo;
            try {
                sellerInfo = em.getReference(SellerInfo.class, id);
                sellerInfo.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sellerInfo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Product> productCollectionOrphanCheck = sellerInfo.getProductCollection();
            for (Product productCollectionOrphanCheckProduct : productCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SellerInfo (" + sellerInfo + ") cannot be destroyed since the Product " + productCollectionOrphanCheckProduct + " in its productCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Professional professional = sellerInfo.getProfessional();
            if (professional != null) {
                professional.setSellerInfo(null);
                professional = em.merge(professional);
            }
            em.remove(sellerInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SellerInfo> findSellerInfoEntities() {
        return findSellerInfoEntities(true, -1, -1);
    }

    public List<SellerInfo> findSellerInfoEntities(int maxResults, int firstResult) {
        return findSellerInfoEntities(false, maxResults, firstResult);
    }

    private List<SellerInfo> findSellerInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SellerInfo.class));
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

    public SellerInfo findSellerInfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SellerInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSellerInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SellerInfo> rt = cq.from(SellerInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<SellerInfo> loadSellerList() {
        EntityManager em = getEntityManager();
        List<SellerInfo> list = new ArrayList<SellerInfo>();
        try {
            Query query = em.createNamedQuery("SellerInfo.findAll");
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

}
