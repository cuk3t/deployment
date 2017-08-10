/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.entity.City;
import java.io.Serializable;
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
public class CityDAO implements Serializable{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");
    EntityManager em = emf.createEntityManager();
    public void persist(Object object) {
        
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
    
    public City searchCity(String nameCity){
        EntityManager em =emf.createEntityManager();
        Query query = em.createNamedQuery("City.findByCityName");
        query.setParameter("cityName", nameCity);
        System.out.println(nameCity);
        City city = new City();
        city= (City) query.getSingleResult();
        return city;
    }
    
    
}
