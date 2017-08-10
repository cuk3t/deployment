/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.entity.Category;
import hd.entity.IdeaBook;
import hd.entity.IdeaBookPhoto;
import hd.entity.IdeaBookPhotoRef;

import hd.entity.Style;
import java.io.Serializable;

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
public class IdeaBookPhotoDAO implements Serializable {

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

    public IdeaBookPhoto insertIdeaBookPhoto(String title, String descripsion, String url, int ideaBookID) {
        EntityManager em = emf.createEntityManager();
        IdeaBook ideaBook = em.find(IdeaBook.class, ideaBookID);
        
        try {
            
            
            IdeaBookPhoto entity = new IdeaBookPhoto();
            entity.setTilte(title);
            entity.setDescription(descripsion);
            entity.setStatus(0);
            entity.setUrl(url);
            entity.setIdeaBookId(ideaBook);
            Category categoryId = new Category(7, "Other");
            Style styleId = new Style(4);
            entity.setCategoryId(categoryId);
            entity.setStyleId(styleId);
            ideaBook.getIdeaBookPhotoCollection().add(entity);
            em.getTransaction().begin();
            ideaBook.setStatus(0);
            em.merge(ideaBook);
            em.persist(entity); 
            em.persist(ideaBook); 
            em.getTransaction().commit();
            
            em.close();
            
            
            return entity;

        } catch (Exception e) {
            return null;
        } finally {
            
            
        }

    }
    public List<IdeaBookPhotoRef> getListIdeaBookPhotoRef(int id){
        EntityManager em = emf.createEntityManager();
        try {
            
            Query query = em.createNamedQuery("IdeaBookPhotoRef.findByIdeaBookId");
            query.setParameter("ideaBookId", id);
            return  (List<IdeaBookPhotoRef>) query.getResultList();

        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    public boolean updateIdeaBookPhotoRefPK(int ideaBookID,int photoID, String description){
        EntityManager em = emf.createEntityManager();
        String jpql ="SELECT i FROM IdeaBookPhotoRef i WHERE i.ideaBookPhotoRefPK.photoId = '"+photoID+"' and i.ideaBookPhotoRefPK.ideaBookId = '"+ideaBookID+"'";
        Query query = em.createQuery(jpql);
        IdeaBookPhotoRef entity = (IdeaBookPhotoRef) query.getSingleResult();
       
        if(entity!=null){
            entity.setComment(description);
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }
    public boolean detetePhotoInIdeaBook(int photoID){
        EntityManager em = emf.createEntityManager();
        IdeaBookPhoto entity = em.find(IdeaBookPhoto.class, photoID);
        if(entity!=null){
            entity.setStatus(2);
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        }
        return false;
        
    }
    public boolean deleteIdeaBookPhotoRef(int photoID, int ideaBookID){
    
        EntityManager em = emf.createEntityManager();
       
        String jpql ="SELECT i FROM IdeaBookPhotoRef i WHERE i.ideaBookPhotoRefPK.photoId = '"+photoID+"' and i.ideaBookPhotoRefPK.ideaBookId = '"+ideaBookID+"'";
        IdeaBookPhotoRef entity;
        
        Query query = em.createQuery(jpql);
        try {
            entity = (IdeaBookPhotoRef) query.getSingleResult();
            IdeaBook ideaBook = em.find(IdeaBook.class, ideaBookID);
        if(entity!=null){
            ideaBook.getIdeaBookPhotoRefCollection().remove(entity);
            em.getTransaction().begin();          
            em.remove(entity);
            em.persist(ideaBook);
            em.getTransaction().commit();
            return true;
        }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    // lấy ideaBookPhoto trong DB để add vào IdeaBook của mình
    public IdeaBookPhoto getIdeaBookPhoto(int photoID){
        try {
            EntityManager em = emf.createEntityManager();
            IdeaBookPhoto entity = em.find(IdeaBookPhoto.class, photoID);
            return entity;
        } catch (Exception e) {
            return null;
        }        
    }
}
