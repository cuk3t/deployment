/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.entity.City;
import hd.entity.Role;
import hd.entity.User;
import java.io.Serializable;
import java.sql.Date;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author cuk3t
 */
public class UserDAO implements Serializable {

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

    public boolean insertMember(String email, String password, String lastname, String firstname,
                    Date birthday, String phone, boolean gender1, City city, String address) {
        EntityManager em = emf.createEntityManager();
        try {
            User reg =getUserByUsernameOrNull(email);
            
            if (reg == null) {
                
                reg = new User();
                reg.setEmail(email);
                reg.setPassword(password);
                reg.setLastname(lastname);
                reg.setFirstname(firstname);
                reg.setDateOfBirth(birthday);
                reg.setPhoneNumber(phone);
                reg.setGender(gender1);
                reg.setPrimaryAddress(address);
                java.util.Date date = new java.util.Date();
                java.sql.Date registerDate = new java.sql.Date( date.getTime());
                reg.setRegisterDate(registerDate);
                Role role = new Role();            
                role.setRoleId(1);
                role.setRoleName("member");
                reg.setRoleId(role);
                reg.setStatus(1);
                reg.setCityCode(city);
                em.getTransaction().begin();
                em.persist(reg);
                em.getTransaction().commit();
                return true;
            }
        } catch (NoResultException e) {
        }finally {
            em.close();
        }
        return false;
    }

    public User getUserByUsernameOrNull(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = '" + email + "'";
            Query query = em.createQuery(jpql);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public User getUserByID(int UserID) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.userId = '" + UserID + "'";
            Query query = em.createQuery(jpql);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public boolean checkLogin(String email, String password){
    //    EntityManager em = emf.createEntityManager();
        EntityManager em = emf.createEntityManager();
        String jpql ="SELECT u FROM User u WHERE u.email = '" + email + "' And u.password = '" + password+ "'";
        Query query = em.createQuery(jpql);
        
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } finally {
            em.close();
        }      
    }
    public List searchLikeByEmail(String email){
        EntityManager em = emf.createEntityManager();
        try {
    
            Query query = em.createNamedQuery("User.findByLikeEmail");
            query.setParameter("email", "%" + email + "%");
            
            List result = query.getResultList();
            
            return  result;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public User getUserByEmail(String email){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("User.findByEmail");
            query.setParameter("email", email);
            User user = (User) query.getSingleResult();
            return user;
            
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
         
    }
    public boolean updateUser(int id, String firstname, String lastname, Date birthday, String phone,
            boolean gender1, City city, String address, String aboutMe){
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, id);
        if(user!=null){
        
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setDateOfBirth(birthday);
            user.setPhoneNumber(phone);
            user.setGender(gender1);
            user.setCityCode(city);
            user.setPrimaryAddress(address);
            user.setAboutMe(aboutMe);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        }
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return false;
    }
   
}
