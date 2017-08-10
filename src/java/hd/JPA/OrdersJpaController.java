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
import hd.entity.User;
import hd.entity.OrderDetail;
import hd.entity.Orders;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) {
        if (orders.getOrderDetailCollection() == null) {
            orders.setOrderDetailCollection(new ArrayList<OrderDetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = orders.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                orders.setUserId(userId);
            }
            Collection<OrderDetail> attachedOrderDetailCollection = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailCollectionOrderDetailToAttach : orders.getOrderDetailCollection()) {
                orderDetailCollectionOrderDetailToAttach = em.getReference(orderDetailCollectionOrderDetailToAttach.getClass(), orderDetailCollectionOrderDetailToAttach.getOrderDetailId());
                attachedOrderDetailCollection.add(orderDetailCollectionOrderDetailToAttach);
            }
            orders.setOrderDetailCollection(attachedOrderDetailCollection);
            em.persist(orders);
            if (userId != null) {
                userId.getOrdersCollection().add(orders);
                userId = em.merge(userId);
            }
            for (OrderDetail orderDetailCollectionOrderDetail : orders.getOrderDetailCollection()) {
                Orders oldOrderIdOfOrderDetailCollectionOrderDetail = orderDetailCollectionOrderDetail.getOrderId();
                orderDetailCollectionOrderDetail.setOrderId(orders);
                orderDetailCollectionOrderDetail = em.merge(orderDetailCollectionOrderDetail);
                if (oldOrderIdOfOrderDetailCollectionOrderDetail != null) {
                    oldOrderIdOfOrderDetailCollectionOrderDetail.getOrderDetailCollection().remove(orderDetailCollectionOrderDetail);
                    oldOrderIdOfOrderDetailCollectionOrderDetail = em.merge(oldOrderIdOfOrderDetailCollectionOrderDetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderId());
            User userIdOld = persistentOrders.getUserId();
            User userIdNew = orders.getUserId();
            Collection<OrderDetail> orderDetailCollectionOld = persistentOrders.getOrderDetailCollection();
            Collection<OrderDetail> orderDetailCollectionNew = orders.getOrderDetailCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderDetail orderDetailCollectionOldOrderDetail : orderDetailCollectionOld) {
                if (!orderDetailCollectionNew.contains(orderDetailCollectionOldOrderDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetail " + orderDetailCollectionOldOrderDetail + " since its orderId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                orders.setUserId(userIdNew);
            }
            Collection<OrderDetail> attachedOrderDetailCollectionNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailCollectionNewOrderDetailToAttach : orderDetailCollectionNew) {
                orderDetailCollectionNewOrderDetailToAttach = em.getReference(orderDetailCollectionNewOrderDetailToAttach.getClass(), orderDetailCollectionNewOrderDetailToAttach.getOrderDetailId());
                attachedOrderDetailCollectionNew.add(orderDetailCollectionNewOrderDetailToAttach);
            }
            orderDetailCollectionNew = attachedOrderDetailCollectionNew;
            orders.setOrderDetailCollection(orderDetailCollectionNew);
            orders = em.merge(orders);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getOrdersCollection().remove(orders);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getOrdersCollection().add(orders);
                userIdNew = em.merge(userIdNew);
            }
            for (OrderDetail orderDetailCollectionNewOrderDetail : orderDetailCollectionNew) {
                if (!orderDetailCollectionOld.contains(orderDetailCollectionNewOrderDetail)) {
                    Orders oldOrderIdOfOrderDetailCollectionNewOrderDetail = orderDetailCollectionNewOrderDetail.getOrderId();
                    orderDetailCollectionNewOrderDetail.setOrderId(orders);
                    orderDetailCollectionNewOrderDetail = em.merge(orderDetailCollectionNewOrderDetail);
                    if (oldOrderIdOfOrderDetailCollectionNewOrderDetail != null && !oldOrderIdOfOrderDetailCollectionNewOrderDetail.equals(orders)) {
                        oldOrderIdOfOrderDetailCollectionNewOrderDetail.getOrderDetailCollection().remove(orderDetailCollectionNewOrderDetail);
                        oldOrderIdOfOrderDetailCollectionNewOrderDetail = em.merge(oldOrderIdOfOrderDetailCollectionNewOrderDetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getOrderId();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderDetail> orderDetailCollectionOrphanCheck = orders.getOrderDetailCollection();
            for (OrderDetail orderDetailCollectionOrphanCheckOrderDetail : orderDetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the OrderDetail " + orderDetailCollectionOrphanCheckOrderDetail + " in its orderDetailCollection field has a non-nullable orderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userId = orders.getUserId();
            if (userId != null) {
                userId.getOrdersCollection().remove(orders);
                userId = em.merge(userId);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
