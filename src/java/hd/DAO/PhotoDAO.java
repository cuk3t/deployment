/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.connection.MyConnection;
import hd.dto.PhotoDTO;
import hd.entity.IdeaBookPhoto;

import java.io.Serializable;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author cuk3t
 */
public class PhotoDAO implements Serializable{

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
    
    
    public List<PhotoDTO> displayListPhoto(){
    
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        PhotoDTO dto = new PhotoDTO();
        List<PhotoDTO> listPhoto = new ArrayList<>();
        try {
            
            Connection conn = MyConnection.getConn();
            String sql = "select i.photo_id, i.url, i.project_id, p.project_name, u.user_id, u1.email , i.style_id from idea_book_photo i, project p, professional u, user u1\n" +
                            "where u1.user_id=u.user_id and u1.status=1 and i.photo_id in (select  max(ph.photo_id)as photo_id\n" +
                            "       from project pr, idea_book_photo ph, professional us\n" +
                            "	    where pr.project_id = ph.project_id and us.user_id= pr.professional_user_id and ph.status=1 and pr.status=1 \n" +
                            "	    group by pr.project_id \n" +
                            "	    order by max(ph.photo_id) DESC) and p.project_id = i.project_id and u.user_id= p.professional_user_id\n" +
                            "order by photo_id DESC";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                dto = new PhotoDTO();
                int photoID = Integer.parseInt(rs.getString("photo_id"));              
                String url = rs.getString("url");               
                int projectID = Integer.parseInt(rs.getString("project_id"));               
                String projectName= rs.getString("project_name");          
                int userID = Integer.parseInt(rs.getString("user_id"));        
                String email= rs.getString("email");
                int style_id = Integer.parseInt(rs.getString("style_id"));
                dto.setPhotoID(photoID);
                dto.setUrl(url);
                dto.setProjectID(projectID);
                dto.setProjectName(projectName);
                dto.setUserID(userID);
                dto.setEmail(email);
                dto.setStyleId(style_id);
                listPhoto.add(dto);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    return listPhoto;
    }
    
    public List<PhotoDTO> displayListPhotoByCategory(String categoryName){
    
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        PhotoDTO dto = new PhotoDTO();
        List<PhotoDTO> listPhoto = new ArrayList<>();
        try {
            
            Connection conn = MyConnection.getConn();
            String sql = "select  max(ph.photo_id) as photo_id, ph.url, pr.project_id,  pr.project_name,u.user_id,u1.email, ph.style_id\n" +
                    "from project pr, idea_book_photo ph, professional u, user u1, category ca\n" +
                    "where u1.user_id=u.user_id and pr.project_id = ph.project_id and u.user_id= pr.professional_user_id and ph.status=1 and pr.status=1 and u1.status=1\n" +
                    "and ca.category_id= ph.category_id and ca.category_name like '"+categoryName+"'\n" +
                    "group by pr.project_id \n" +
                    "order by max(ph.photo_id) DESC";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                dto = new PhotoDTO();
                int photoID = Integer.parseInt(rs.getString("photo_id"));                
                String url = rs.getString("url");                
                int projectID = Integer.parseInt(rs.getString("project_id"));                
                String projectName= rs.getString("project_name");                
                int userID = Integer.parseInt(rs.getString("user_id"));
                int style_id = Integer.parseInt(rs.getString("style_id"));                
                String email= rs.getString("email");  
                dto.setPhotoID(photoID);
                dto.setUrl(url);
                dto.setProjectID(projectID);
                dto.setProjectName(projectName);
                dto.setUserID(userID);
                dto.setEmail(email);
                dto.setStyleId(style_id);
                listPhoto.add(dto);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    return listPhoto;
    }
    public List<PhotoDTO> displayListPhotoByStyle(String style){
    
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        PhotoDTO dto = new PhotoDTO();
        List<PhotoDTO> listPhoto = new ArrayList<>();
        try {
            
            Connection conn = MyConnection.getConn();
            String sql = "select  max(ph.photo_id), photo_id, ph.url, pr.project_id,  pr.project_name,u.user_id,u.email\n" +
                        "from project pr, idea_book_photo ph, user u, style st\n" +
                        "where pr.project_id = ph.project_id and u.user_id= pr.project_id and ph.style_id =st.style_id and st.name like '%"+style+"%'\n" +
                        "group by pr.project_id \n" +
                        "order by max(ph.photo_id) DESC";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                dto = new PhotoDTO();
                int photoID = Integer.parseInt(rs.getString("photo_id"));                
                String url = rs.getString("url");                
                int projectID = Integer.parseInt(rs.getString("project_id"));                
                String projectName= rs.getString("project_name");                
                int userID = Integer.parseInt(rs.getString("user_id"));                
                String email= rs.getString("email");
                dto.setPhotoID(photoID);
                dto.setUrl(url);
                dto.setProjectID(projectID);
                dto.setProjectName(projectName);
                dto.setUserID(userID);
                dto.setEmail(email);
                listPhoto.add(dto);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    return listPhoto;
    }
    public IdeaBookPhoto getPhoto(int photoID){
        try {
            EntityManager em = emf.createEntityManager();
            IdeaBookPhoto entity = em.find(IdeaBookPhoto.class, photoID);
            return entity;
        } catch (Exception e) {
            return null;
        }       
    }
}
