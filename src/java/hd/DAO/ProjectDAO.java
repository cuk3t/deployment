/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.connection.MyConnection;
import hd.dto.PhotoDTO;
import hd.dto.ProjectDTO;
import hd.entity.Project;
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
import javax.persistence.Query;

/**
 *
 * @author cuk3t
 */
public class ProjectDAO implements Serializable{

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
    public List<ProjectDTO> showListProjectOfPros(int user_id){
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProjectDTO dto = new ProjectDTO();
        List<ProjectDTO> listPros = new ArrayList<>();
        try {
            
            Connection conn = MyConnection.getConn();
            String sql = "SELECT pr.project_id, pr.project_name, pr.address, ph.url, count(ph.photo_id) as quantity \n" +
                        "FROM project pr, idea_book_photo ph, user u\n" +
                        "where pr.project_id = ph.project_id and pr.user_id='"+user_id+"' and u.user_id = pr.user_id and u.status = 1 and pr.status =1 and ph.status=1\n" +
                        "group by pr.project_id\n" +
                        "order by pr.project_id desc";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                dto = new ProjectDTO();
                int projectId = Integer.parseInt(rs.getString("project_id"));             
                String url = rs.getString("url");                                  
                String projectName= rs.getString("project_name");                
                String address = rs.getString("address");
                int quantity = Integer.parseInt(rs.getString("quantity"));
                dto.setProjectId(projectId);
                dto.setProjectName(projectName);
                dto.setUrlPhoto(url);
                dto.setAddress(address);
                dto.setQuantity(quantity);
                listPros.add(dto);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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
    return listPros;
    }
    
    public Project getProjectByID(int project_id){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("Project.findByProjectId");
            query.setParameter("projectId", project_id);
            
            return (Project) query.getSingleResult();
            
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
        
    }

    
}
