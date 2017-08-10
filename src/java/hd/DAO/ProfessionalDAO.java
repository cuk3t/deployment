/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.DAO;

import hd.connection.MyConnection;

import hd.dto.ProfessionalDTO;
import hd.entity.Professional;
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
public class ProfessionalDAO implements Serializable{

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
    
    public Professional getProsByID(int ID){
        EntityManager em = emf.createEntityManager();
        try {Query query = em.createNamedQuery("Professional.findByUserId");
            query.setParameter("userId", ID );
            return (Professional) query.getSingleResult();
            
        } catch (Exception e) {
            return null;
        }  finally {
            em.close();
        }     
    }
    public List<ProfessionalDTO> getProsByTypeWork(String typeWork){
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProfessionalDTO dto = new ProfessionalDTO();
        List<ProfessionalDTO> listPros = new ArrayList<>();
        
        try {
            
            Connection conn = MyConnection.getConn();
            String sql = "SELECT u.user_id, prs.professional_name, u.about_me, u.phone_number, prs.address, prj.project_id, c.cityName, prs.type_of_work_id, ph.url\n" +
            "FROM professional prs, project prj, user u, city c, idea_book_photo ph\n" +
            "where  prj.project_id = ph.photo_id and prs.type_of_work_id like'%"+typeWork+"%' and u.status = 1 and prs.user_id= prj.professional_user_id and u.user_id=prs.user_id and u.city_code=c.city_code\n" +
            "group by prs.user_id\n" +
            "order By prj.project_id desc";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                dto = new ProfessionalDTO();
                String professionalName = rs.getString("professional_name");
                dto.setProfessionalName(professionalName);
                String address = rs.getString("address");
                dto.setAddress(address);
                int userId = Integer.parseInt(rs.getString("user_id"));
                dto.setUserId(userId);
                String aboutMe = rs.getString("about_me");
                dto.setAboutMe(aboutMe);
                String cityName = rs.getString("cityName");
                dto.setCityName(cityName);
                String phoneNumber = rs.getString("phone_number");
                dto.setPhoneNumber(phoneNumber);
                String url = rs.getString("url");
                dto.setUrl(url);
                listPros.add(dto);
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
        return listPros;
    }
    public List<ProfessionalDTO> SearchPros(String prosName, String typeofworkID, String cityCode){
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProfessionalDTO dto = new ProfessionalDTO();
        List<ProfessionalDTO> listPros = new ArrayList<>();
        
        try {
            
            Connection conn = MyConnection.getConn();
            String sql = "SELECT u.user_id, prs.professional_name, u.about_me, u.phone_number, prs.address, prj.project_id, c.cityName, prs.type_of_work_id, ph.url\n" +
                "FROM professional prs, project prj, user u, city c, idea_book_photo ph\n" +
                "where prj.status = 1 and ph.status=1 and u.status=1 and c.city_code like '%"+cityCode+"%' and ph.project_id = prj.project_id\n" +
                "and prs.professional_name like'%"+prosName+"%' and prs.type_of_work_id like'%"+typeofworkID+"%' "
                + " and prs.user_id= prj.professional_user_id and u.user_id=prs.user_id and u.city_code=c.city_code\n" +
                "group by prs.user_id\n" +
                "order By prj.project_id desc";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                dto = new ProfessionalDTO();
                String professionalName = rs.getString("professional_name");
                dto.setProfessionalName(professionalName);
                String address = rs.getString("address");
                dto.setAddress(address);
                int userId = Integer.parseInt(rs.getString("user_id"));
                dto.setUserId(userId);
                String aboutMe = rs.getString("about_me");
                dto.setAboutMe(aboutMe);
                String cityName = rs.getString("cityName");
                dto.setCityName(cityName);
                String phoneNumber = rs.getString("phone_number");
                dto.setPhoneNumber(phoneNumber); 
                String url = rs.getString("url");
                System.out.println(url);
                dto.setUrl(url);
                listPros.add(dto);
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
        return listPros;
    }

    
    
}
