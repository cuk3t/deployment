/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.service;

import hd.JPA.CategoryJpaController;
import hd.JPA.TrackingJpaController;
import hd.JPA.UserJpaController;
import hd.entity.Category;
import hd.entity.Product;
import hd.entity.Tracking;
import hd.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author An
 */
public class HDSystem {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");

    public void addTracking(int userId, int categoryId) {
        try {
            boolean isExist = false;
            TrackingJpaController trackingJpa = new TrackingJpaController(emf);
            UserJpaController userJpa = new UserJpaController(emf);
            CategoryJpaController cateJpa = new CategoryJpaController(emf);
            User user = userJpa.findUser(userId);
            Category cate = cateJpa.findCategory(categoryId);
            List<Tracking> list = trackingJpa.findTrackingEntities();
            Tracking tracking = new Tracking();
            tracking.setUser(user);
            tracking.setCategory(cate);
            tracking.setCount(0);
            Date lastUpdate = new Date();
            for (Tracking dto : list) {
                if (isExistTracking(dto, userId, categoryId)) {
                    tracking = dto;
                    isExist = true;
                    break;
                }
            }
            tracking.setLastUpdate(lastUpdate);
            tracking.setCount(tracking.getCount() + 1);
            if (isExist) {
                trackingJpa.edit(tracking);
            } else {
                trackingJpa.create(tracking);
            }
            removeTracking(user.getUserId(), 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getTargetSendPromotion(Product product) {
        try {
            List<String> emails = new ArrayList<>();
            TrackingJpaController trackingJpa = new TrackingJpaController(emf);
            int categoryId = product.getCategoryCategoryId().getCategoryId();
            List<Tracking> trackings = trackingJpa.findTrackingByCategoryId(categoryId);
            for (Tracking dto : trackings) {
                emails.add(dto.getUser().getEmail());
            }
            return emails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isExistTracking(Tracking dto, int userId, int categoryId) {
        return dto.getUser().getUserId() == userId
                && dto.getCategory().getCategoryId() == categoryId;
    }

    private void removeTracking(int userId, int outdateDays) {
        TrackingJpaController trackingJpa = new TrackingJpaController(emf);
        try {
            trackingJpa.removeOutdateTracking(outdateDays, new Date(), userId);
        } catch (Exception ex) {
            Logger.getLogger(HDSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
}
