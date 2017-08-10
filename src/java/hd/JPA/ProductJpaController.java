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
import hd.entity.Category;
import hd.entity.SellerInfo;
import hd.entity.Style;
import hd.entity.PromotionDetail;
import java.util.ArrayList;
import java.util.Collection;
import hd.entity.OrderDetail;
import hd.entity.Product;
import hd.entity.ProductPhoto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, Exception {
        if (product.getPromotionDetailCollection() == null) {
            product.setPromotionDetailCollection(new ArrayList<PromotionDetail>());
        }
        if (product.getOrderDetailCollection() == null) {
            product.setOrderDetailCollection(new ArrayList<OrderDetail>());
        }
        if (product.getProductPhotoCollection() == null) {
            product.setProductPhotoCollection(new ArrayList<ProductPhoto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryCategoryId = product.getCategoryCategoryId();
            if (categoryCategoryId != null) {
                categoryCategoryId = em.getReference(categoryCategoryId.getClass(), categoryCategoryId.getCategoryId());
                product.setCategoryCategoryId(categoryCategoryId);
            }
            SellerInfo userId = product.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                product.setUserId(userId);
            }
            Style styleStyleId = product.getStyleStyleId();
            if (styleStyleId != null) {
                styleStyleId = em.getReference(styleStyleId.getClass(), styleStyleId.getStyleId());
                product.setStyleStyleId(styleStyleId);
            }
            Collection<PromotionDetail> attachedPromotionDetailCollection = new ArrayList<PromotionDetail>();
            for (PromotionDetail promotionDetailCollectionPromotionDetailToAttach : product.getPromotionDetailCollection()) {
                promotionDetailCollectionPromotionDetailToAttach = em.getReference(promotionDetailCollectionPromotionDetailToAttach.getClass(), promotionDetailCollectionPromotionDetailToAttach.getPromotionDetailId());
                attachedPromotionDetailCollection.add(promotionDetailCollectionPromotionDetailToAttach);
            }
            product.setPromotionDetailCollection(attachedPromotionDetailCollection);
            Collection<OrderDetail> attachedOrderDetailCollection = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailCollectionOrderDetailToAttach : product.getOrderDetailCollection()) {
                orderDetailCollectionOrderDetailToAttach = em.getReference(orderDetailCollectionOrderDetailToAttach.getClass(), orderDetailCollectionOrderDetailToAttach.getOrderDetailId());
                attachedOrderDetailCollection.add(orderDetailCollectionOrderDetailToAttach);
            }
            product.setOrderDetailCollection(attachedOrderDetailCollection);
            Collection<ProductPhoto> attachedProductPhotoCollection = new ArrayList<ProductPhoto>();
            for (ProductPhoto productPhotoCollectionProductPhotoToAttach : product.getProductPhotoCollection()) {
                productPhotoCollectionProductPhotoToAttach = em.getReference(productPhotoCollectionProductPhotoToAttach.getClass(), productPhotoCollectionProductPhotoToAttach.getPhotoId());
                attachedProductPhotoCollection.add(productPhotoCollectionProductPhotoToAttach);
            }
            product.setProductPhotoCollection(attachedProductPhotoCollection);
            em.persist(product);
            if (categoryCategoryId != null) {
                categoryCategoryId.getProductCollection().add(product);
                categoryCategoryId = em.merge(categoryCategoryId);
            }
            if (userId != null) {
                userId.getProductCollection().add(product);
                userId = em.merge(userId);
            }
            if (styleStyleId != null) {
                styleStyleId.getProductCollection().add(product);
                styleStyleId = em.merge(styleStyleId);
            }
            for (PromotionDetail promotionDetailCollectionPromotionDetail : product.getPromotionDetailCollection()) {
                Product oldProductIdOfPromotionDetailCollectionPromotionDetail = promotionDetailCollectionPromotionDetail.getProductId();
                promotionDetailCollectionPromotionDetail.setProductId(product);
                promotionDetailCollectionPromotionDetail = em.merge(promotionDetailCollectionPromotionDetail);
                if (oldProductIdOfPromotionDetailCollectionPromotionDetail != null) {
                    oldProductIdOfPromotionDetailCollectionPromotionDetail.getPromotionDetailCollection().remove(promotionDetailCollectionPromotionDetail);
                    oldProductIdOfPromotionDetailCollectionPromotionDetail = em.merge(oldProductIdOfPromotionDetailCollectionPromotionDetail);
                }
            }
            for (OrderDetail orderDetailCollectionOrderDetail : product.getOrderDetailCollection()) {
                Product oldProductIdOfOrderDetailCollectionOrderDetail = orderDetailCollectionOrderDetail.getProductId();
                orderDetailCollectionOrderDetail.setProductId(product);
                orderDetailCollectionOrderDetail = em.merge(orderDetailCollectionOrderDetail);
                if (oldProductIdOfOrderDetailCollectionOrderDetail != null) {
                    oldProductIdOfOrderDetailCollectionOrderDetail.getOrderDetailCollection().remove(orderDetailCollectionOrderDetail);
                    oldProductIdOfOrderDetailCollectionOrderDetail = em.merge(oldProductIdOfOrderDetailCollectionOrderDetail);
                }
            }
            for (ProductPhoto productPhotoCollectionProductPhoto : product.getProductPhotoCollection()) {
                Product oldProductIdOfProductPhotoCollectionProductPhoto = productPhotoCollectionProductPhoto.getProductId();
                productPhotoCollectionProductPhoto.setProductId(product);
                productPhotoCollectionProductPhoto = em.merge(productPhotoCollectionProductPhoto);
                if (oldProductIdOfProductPhotoCollectionProductPhoto != null) {
                    oldProductIdOfProductPhotoCollectionProductPhoto.getProductPhotoCollection().remove(productPhotoCollectionProductPhoto);
                    oldProductIdOfProductPhotoCollectionProductPhoto = em.merge(oldProductIdOfProductPhotoCollectionProductPhoto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProduct(product.getProductId()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProductId());
            Category categoryCategoryIdOld = persistentProduct.getCategoryCategoryId();
            Category categoryCategoryIdNew = product.getCategoryCategoryId();
            SellerInfo userIdOld = persistentProduct.getUserId();
            SellerInfo userIdNew = product.getUserId();
            Style styleStyleIdOld = persistentProduct.getStyleStyleId();
            Style styleStyleIdNew = product.getStyleStyleId();
            Collection<PromotionDetail> promotionDetailCollectionOld = persistentProduct.getPromotionDetailCollection();
            Collection<PromotionDetail> promotionDetailCollectionNew = product.getPromotionDetailCollection();
            Collection<OrderDetail> orderDetailCollectionOld = persistentProduct.getOrderDetailCollection();
            Collection<OrderDetail> orderDetailCollectionNew = product.getOrderDetailCollection();
            Collection<ProductPhoto> productPhotoCollectionOld = persistentProduct.getProductPhotoCollection();
            Collection<ProductPhoto> productPhotoCollectionNew = product.getProductPhotoCollection();
            List<String> illegalOrphanMessages = null;
            for (PromotionDetail promotionDetailCollectionOldPromotionDetail : promotionDetailCollectionOld) {
                if (!promotionDetailCollectionNew.contains(promotionDetailCollectionOldPromotionDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PromotionDetail " + promotionDetailCollectionOldPromotionDetail + " since its productId field is not nullable.");
                }
            }
            for (OrderDetail orderDetailCollectionOldOrderDetail : orderDetailCollectionOld) {
                if (!orderDetailCollectionNew.contains(orderDetailCollectionOldOrderDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetail " + orderDetailCollectionOldOrderDetail + " since its productId field is not nullable.");
                }
            }
            for (ProductPhoto productPhotoCollectionOldProductPhoto : productPhotoCollectionOld) {
                if (!productPhotoCollectionNew.contains(productPhotoCollectionOldProductPhoto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductPhoto " + productPhotoCollectionOldProductPhoto + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoryCategoryIdNew != null) {
                categoryCategoryIdNew = em.getReference(categoryCategoryIdNew.getClass(), categoryCategoryIdNew.getCategoryId());
                product.setCategoryCategoryId(categoryCategoryIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                product.setUserId(userIdNew);
            }
            if (styleStyleIdNew != null) {
                styleStyleIdNew = em.getReference(styleStyleIdNew.getClass(), styleStyleIdNew.getStyleId());
                product.setStyleStyleId(styleStyleIdNew);
            }
            Collection<PromotionDetail> attachedPromotionDetailCollectionNew = new ArrayList<PromotionDetail>();
            for (PromotionDetail promotionDetailCollectionNewPromotionDetailToAttach : promotionDetailCollectionNew) {
                promotionDetailCollectionNewPromotionDetailToAttach = em.getReference(promotionDetailCollectionNewPromotionDetailToAttach.getClass(), promotionDetailCollectionNewPromotionDetailToAttach.getPromotionDetailId());
                attachedPromotionDetailCollectionNew.add(promotionDetailCollectionNewPromotionDetailToAttach);
            }
            promotionDetailCollectionNew = attachedPromotionDetailCollectionNew;
            product.setPromotionDetailCollection(promotionDetailCollectionNew);
            Collection<OrderDetail> attachedOrderDetailCollectionNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailCollectionNewOrderDetailToAttach : orderDetailCollectionNew) {
                orderDetailCollectionNewOrderDetailToAttach = em.getReference(orderDetailCollectionNewOrderDetailToAttach.getClass(), orderDetailCollectionNewOrderDetailToAttach.getOrderDetailId());
                attachedOrderDetailCollectionNew.add(orderDetailCollectionNewOrderDetailToAttach);
            }
            orderDetailCollectionNew = attachedOrderDetailCollectionNew;
            product.setOrderDetailCollection(orderDetailCollectionNew);
            Collection<ProductPhoto> attachedProductPhotoCollectionNew = new ArrayList<ProductPhoto>();
            for (ProductPhoto productPhotoCollectionNewProductPhotoToAttach : productPhotoCollectionNew) {
                productPhotoCollectionNewProductPhotoToAttach = em.getReference(productPhotoCollectionNewProductPhotoToAttach.getClass(), productPhotoCollectionNewProductPhotoToAttach.getPhotoId());
                attachedProductPhotoCollectionNew.add(productPhotoCollectionNewProductPhotoToAttach);
            }
            productPhotoCollectionNew = attachedProductPhotoCollectionNew;
            product.setProductPhotoCollection(productPhotoCollectionNew);
            product = em.merge(product);
            if (categoryCategoryIdOld != null && !categoryCategoryIdOld.equals(categoryCategoryIdNew)) {
                categoryCategoryIdOld.getProductCollection().remove(product);
                categoryCategoryIdOld = em.merge(categoryCategoryIdOld);
            }
            if (categoryCategoryIdNew != null && !categoryCategoryIdNew.equals(categoryCategoryIdOld)) {
                categoryCategoryIdNew.getProductCollection().add(product);
                categoryCategoryIdNew = em.merge(categoryCategoryIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getProductCollection().remove(product);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getProductCollection().add(product);
                userIdNew = em.merge(userIdNew);
            }
            if (styleStyleIdOld != null && !styleStyleIdOld.equals(styleStyleIdNew)) {
                styleStyleIdOld.getProductCollection().remove(product);
                styleStyleIdOld = em.merge(styleStyleIdOld);
            }
            if (styleStyleIdNew != null && !styleStyleIdNew.equals(styleStyleIdOld)) {
                styleStyleIdNew.getProductCollection().add(product);
                styleStyleIdNew = em.merge(styleStyleIdNew);
            }
            for (PromotionDetail promotionDetailCollectionNewPromotionDetail : promotionDetailCollectionNew) {
                if (!promotionDetailCollectionOld.contains(promotionDetailCollectionNewPromotionDetail)) {
                    Product oldProductIdOfPromotionDetailCollectionNewPromotionDetail = promotionDetailCollectionNewPromotionDetail.getProductId();
                    promotionDetailCollectionNewPromotionDetail.setProductId(product);
                    promotionDetailCollectionNewPromotionDetail = em.merge(promotionDetailCollectionNewPromotionDetail);
                    if (oldProductIdOfPromotionDetailCollectionNewPromotionDetail != null && !oldProductIdOfPromotionDetailCollectionNewPromotionDetail.equals(product)) {
                        oldProductIdOfPromotionDetailCollectionNewPromotionDetail.getPromotionDetailCollection().remove(promotionDetailCollectionNewPromotionDetail);
                        oldProductIdOfPromotionDetailCollectionNewPromotionDetail = em.merge(oldProductIdOfPromotionDetailCollectionNewPromotionDetail);
                    }
                }
            }
            for (OrderDetail orderDetailCollectionNewOrderDetail : orderDetailCollectionNew) {
                if (!orderDetailCollectionOld.contains(orderDetailCollectionNewOrderDetail)) {
                    Product oldProductIdOfOrderDetailCollectionNewOrderDetail = orderDetailCollectionNewOrderDetail.getProductId();
                    orderDetailCollectionNewOrderDetail.setProductId(product);
                    orderDetailCollectionNewOrderDetail = em.merge(orderDetailCollectionNewOrderDetail);
                    if (oldProductIdOfOrderDetailCollectionNewOrderDetail != null && !oldProductIdOfOrderDetailCollectionNewOrderDetail.equals(product)) {
                        oldProductIdOfOrderDetailCollectionNewOrderDetail.getOrderDetailCollection().remove(orderDetailCollectionNewOrderDetail);
                        oldProductIdOfOrderDetailCollectionNewOrderDetail = em.merge(oldProductIdOfOrderDetailCollectionNewOrderDetail);
                    }
                }
            }
            for (ProductPhoto productPhotoCollectionNewProductPhoto : productPhotoCollectionNew) {
                if (!productPhotoCollectionOld.contains(productPhotoCollectionNewProductPhoto)) {
                    Product oldProductIdOfProductPhotoCollectionNewProductPhoto = productPhotoCollectionNewProductPhoto.getProductId();
                    productPhotoCollectionNewProductPhoto.setProductId(product);
                    productPhotoCollectionNewProductPhoto = em.merge(productPhotoCollectionNewProductPhoto);
                    if (oldProductIdOfProductPhotoCollectionNewProductPhoto != null && !oldProductIdOfProductPhotoCollectionNewProductPhoto.equals(product)) {
                        oldProductIdOfProductPhotoCollectionNewProductPhoto.getProductPhotoCollection().remove(productPhotoCollectionNewProductPhoto);
                        oldProductIdOfProductPhotoCollectionNewProductPhoto = em.merge(oldProductIdOfProductPhotoCollectionNewProductPhoto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = product.getProductId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PromotionDetail> promotionDetailCollectionOrphanCheck = product.getPromotionDetailCollection();
            for (PromotionDetail promotionDetailCollectionOrphanCheckPromotionDetail : promotionDetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the PromotionDetail " + promotionDetailCollectionOrphanCheckPromotionDetail + " in its promotionDetailCollection field has a non-nullable productId field.");
            }
            Collection<OrderDetail> orderDetailCollectionOrphanCheck = product.getOrderDetailCollection();
            for (OrderDetail orderDetailCollectionOrphanCheckOrderDetail : orderDetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the OrderDetail " + orderDetailCollectionOrphanCheckOrderDetail + " in its orderDetailCollection field has a non-nullable productId field.");
            }
            Collection<ProductPhoto> productPhotoCollectionOrphanCheck = product.getProductPhotoCollection();
            for (ProductPhoto productPhotoCollectionOrphanCheckProductPhoto : productPhotoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the ProductPhoto " + productPhotoCollectionOrphanCheckProductPhoto + " in its productPhotoCollection field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Category categoryCategoryId = product.getCategoryCategoryId();
            if (categoryCategoryId != null) {
                categoryCategoryId.getProductCollection().remove(product);
                categoryCategoryId = em.merge(categoryCategoryId);
            }
            SellerInfo userId = product.getUserId();
            if (userId != null) {
                userId.getProductCollection().remove(product);
                userId = em.merge(userId);
            }
            Style styleStyleId = product.getStyleStyleId();
            if (styleStyleId != null) {
                styleStyleId.getProductCollection().remove(product);
                styleStyleId = em.merge(styleStyleId);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int changeProductQuantity(String productId, int newQuantity) throws NonexistentEntityException, Exception {
        if (newQuantity < 0) {
            newQuantity = 0;
        }

        Product product = findProduct(productId);

        if (product == null) {
            throw new NonexistentEntityException("This product is not exist");
        } else {
            product.setQuantity(newQuantity);
            edit(product);

            return newQuantity;
        }
    }

    public int buyProduct(String productId, int quantity) throws NonexistentEntityException, Exception {
        if (quantity < 0) {
            quantity = 0;
        }

        Product product = findProduct(productId);

        if (product == null) {
            throw new NonexistentEntityException("This product is not exist");
        } else {
            int currentQuantity = product.getQuantity();

            if (currentQuantity < quantity) {
                return -1;
            } else {
                int newQuantity = currentQuantity - quantity;
                product.setQuantity(newQuantity);
                edit(product);

                return newQuantity;
            }
        }
    }

    public List<Product> getProductsByStatus(int status) {
        EntityManager em = getEntityManager();
        List<Product> list = new ArrayList<Product>();
        try {
            Query query = em.createNamedQuery("Product.findByStatus");
            query.setParameter("status", status);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
