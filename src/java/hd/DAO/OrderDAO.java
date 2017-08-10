/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.dto.Cart;

import hd.entity.OrderDetail;
import hd.entity.Orders;
import hd.entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author cuk3t
 */
public class OrderDAO implements Serializable{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    public boolean insertOrder(User user){
        EntityManager em = emf.createEntityManager();
        try {
            Date time = new Date(System.currentTimeMillis());
            int status =0;
            
            Orders order = new Orders();
            order.setCreatedTime(time);
           
            order.setUserId(user);
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();     
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    public void inserOrderDetail(List<Cart> listCart){
        EntityManager em = emf.createEntityManager();
        
        String jpql = "SELECT o FROM Orders o WHERE o.orderId >= all(select p.orderId from Orders p)";
        Query query = em.createQuery(jpql);

        Orders order1 = (Orders) query.getSingleResult();
        
        for (int i = 0; i < listCart.size(); i++) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order1); 
                float price = listCart.get(i).getProduct().getPrice()*listCart.get(i).getQuantity();
                orderDetail.setPrice(price);
                orderDetail.setProductId(listCart.get(i).getProduct());
                orderDetail.setQuantity(listCart.get(i).getQuantity());
               
                persist(orderDetail);
                
                
            }
        
    }
    
    public List<Orders> getOrderByID(int ID){
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
             String jpql = "SELECT o FROM Orders o WHERE o.userId.userId = "+ID+" Order by o.orderId DESC";
             Query query = em.createQuery(jpql);
             return (List<Orders>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
