/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.JPA;

import hd.JPA.exceptions.NonexistentEntityException;
import hd.entity.OrderDetail;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hd.entity.Orders;
import hd.entity.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class OrderDetailJpaController implements Serializable {

    public OrderDetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderDetail orderDetail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders orderId = orderDetail.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getOrderId());
                orderDetail.setOrderId(orderId);
            }
            Product productId = orderDetail.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                orderDetail.setProductId(productId);
            }
            em.persist(orderDetail);
            if (orderId != null) {
                orderId.getOrderDetailCollection().add(orderDetail);
                orderId = em.merge(orderId);
            }
            if (productId != null) {
                productId.getOrderDetailCollection().add(orderDetail);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderDetail orderDetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderDetail persistentOrderDetail = em.find(OrderDetail.class, orderDetail.getOrderDetailId());
            Orders orderIdOld = persistentOrderDetail.getOrderId();
            Orders orderIdNew = orderDetail.getOrderId();
            Product productIdOld = persistentOrderDetail.getProductId();
            Product productIdNew = orderDetail.getProductId();
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getOrderId());
                orderDetail.setOrderId(orderIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                orderDetail.setProductId(productIdNew);
            }
            orderDetail = em.merge(orderDetail);
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getOrderDetailCollection().remove(orderDetail);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getOrderDetailCollection().add(orderDetail);
                orderIdNew = em.merge(orderIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getOrderDetailCollection().remove(orderDetail);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getOrderDetailCollection().add(orderDetail);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderDetail.getOrderDetailId();
                if (findOrderDetail(id) == null) {
                    throw new NonexistentEntityException("The orderDetail with id " + id + " no longer exists.");
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
            OrderDetail orderDetail;
            try {
                orderDetail = em.getReference(OrderDetail.class, id);
                orderDetail.getOrderDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderDetail with id " + id + " no longer exists.", enfe);
            }
            Orders orderId = orderDetail.getOrderId();
            if (orderId != null) {
                orderId.getOrderDetailCollection().remove(orderDetail);
                orderId = em.merge(orderId);
            }
            Product productId = orderDetail.getProductId();
            if (productId != null) {
                productId.getOrderDetailCollection().remove(orderDetail);
                productId = em.merge(productId);
            }
            em.remove(orderDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderDetail> findOrderDetailEntities() {
        return findOrderDetailEntities(true, -1, -1);
    }

    public List<OrderDetail> findOrderDetailEntities(int maxResults, int firstResult) {
        return findOrderDetailEntities(false, maxResults, firstResult);
    }

    private List<OrderDetail> findOrderDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderDetail.class));
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

    public OrderDetail findOrderDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderDetail> rt = cq.from(OrderDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
