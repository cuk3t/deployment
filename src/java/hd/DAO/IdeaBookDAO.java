/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.entity.IdeaBook;
import hd.entity.IdeaBookPhoto;
import hd.entity.IdeaBookPhotoRef;
import hd.entity.IdeaBookPhotoRefPK;

import hd.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
public class IdeaBookDAO implements Serializable {

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

    public List<IdeaBook> getMyIdeaBook(int userID) {

        EntityManager em = emf.createEntityManager();      
        List<IdeaBook> list = new ArrayList<>();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
            String jpql = "SELECT i FROM IdeaBook i WHERE i.userId.userId = '" + userID + "' and i.isPublic in(0,1)and i.status in(0,1,-1)";
            Query query = em.createQuery(jpql);
            list = query.getResultList();
//            em.setFlushMode(FlushModeType.COMMIT);
////            em.flush();
            return list;
            
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }

    }

    public IdeaBook getIdeaBookByTitleOrNull(String title) {
        EntityManager em = emf.createEntityManager();
        try {

            Query query = em.createNamedQuery("IdeaBook.findByTitle");
            query.setParameter("title", title);
            return (IdeaBook) query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean insertIdeaBook(String title, String descripsion, User userId) {
        EntityManager em = emf.createEntityManager();
        try {

            IdeaBook ideabook = getIdeaBookByTitleOrNull(title);

            if (ideabook == null) {

                ideabook = new IdeaBook();
                ideabook.setTitle(title);
                ideabook.setDescription(descripsion);
                ideabook.setStatus(0);

                ideabook.setUserId(userId);
                em.getTransaction().begin();
                em.persist(ideabook);
                em.getTransaction().commit();
                return true;
            }

        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
        return false;
    }

    public IdeaBook getMyIdeaBookByID(int id) {
        EntityManager em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        try {
            em.getTransaction().begin();
            String jpql = "SELECT i FROM IdeaBook i WHERE i.ideaBookId='"+ id +"'";
            Query query = em.createQuery(jpql);
            IdeaBook ideaBook = (IdeaBook) query.getSingleResult();
            
            String jpql2 = "SELECT i FROM IdeaBookPhoto i WHERE i.status in(1,2)";
            Query query2 = em.createQuery(jpql2);
            List<IdeaBookPhoto> list = query2.getResultList();
            Query query3 = em.createNamedQuery("IdeaBookPhotoRef.findByIdeaBookId");
            query3.setParameter("ideaBookId", id);
            Collection<IdeaBookPhotoRef> listIdeaBookPhotoRef = query3.getResultList();
            em.getTransaction().commit();
            for (int i = 0; i < list.size(); i++) {
                ideaBook.getIdeaBookPhotoCollection().remove(list.get(i));
            }
//            ideaBook.getIdeaBookPhotoRefCollection().removeAll(list);
            ideaBook.setIdeaBookPhotoRefCollection(listIdeaBookPhotoRef);
            return ideaBook;

        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    // add photo của project khác hoặc ideaBook khác vào ideaBook của mình
    public boolean addPhotoToIdeaBook (int ideaBookId, IdeaBookPhoto ideaBookPhoto,String description){
        EntityManager em = emf.createEntityManager();
        try {
            IdeaBook entity = em.find(IdeaBook.class, ideaBookId);
        IdeaBookPhotoRef ideaBookPhotoRef = new IdeaBookPhotoRef();
        
        ideaBookPhotoRef.setIdeaBook(entity);
        ideaBookPhotoRef.setIdeaBookPhoto(ideaBookPhoto);
        ideaBookPhotoRef.setComment(description);
        
        IdeaBookPhotoRefPK ideaBookPhotoRefPK = new IdeaBookPhotoRefPK(ideaBookId, ideaBookPhoto.getPhotoId());
        ideaBookPhotoRef.setIdeaBookPhotoRefPK(ideaBookPhotoRefPK);
        if(entity!=null){
            entity.getIdeaBookPhotoRefCollection().add(ideaBookPhotoRef);
            em.getTransaction().begin();
            em.persist(entity);
            em.persist(ideaBookPhotoRef);
            em.getTransaction().commit();
            return true;
        }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return false;
    }
    public boolean updateDescriptionIdeabook(int id, String description){
        EntityManager em = emf.createEntityManager();
        IdeaBook entity = em.find(IdeaBook.class, id);
        if(entity!=null){
            entity.setDescription(description);
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }
    public boolean deleteIdeaBook(int id){
        EntityManager em = emf.createEntityManager();
        IdeaBook entity = em.find(IdeaBook.class, id);
        IdeaBookPhotoDAO photoDao = new IdeaBookPhotoDAO();
        List<IdeaBookPhotoRef> list = photoDao.getListIdeaBookPhotoRef(id);
        
        if(entity!=null){
            
            em.getTransaction().begin();
            for (int i = 0; i < list.size(); i++) {
                IdeaBookPhotoRef ideaBookPhotoRef = list.get(i);
                photoDao.deleteIdeaBookPhotoRef(ideaBookPhotoRef.getIdeaBookPhoto().getPhotoId(), id);
            }
            entity.setStatus(-2);
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

    
}
