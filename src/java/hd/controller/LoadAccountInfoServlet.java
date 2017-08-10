/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.IdeaBookJpaController;
import hd.JPA.ProjectJpaController;
import hd.JPA.UserJpaController;
import hd.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lê Đại An
 */
public class LoadAccountInfoServlet extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            String url = Constant.LOGIN_ADMIN_PAGE;
            if (session.getAttribute(Constant.ATT_ADMIN) != null) {
                url = Constant.ACCOUNT_INFO_PAGE;
                int userId = Integer.parseInt(request.getParameter(Constant.PARAM_USER_ID));
                //Load account information
                AccountInfoDTO dto = new AccountInfoDTO(userId);
                UserJpaController userJpa = new UserJpaController(emf);
                User user = userJpa.findUser(userId);
                //set values to DTO
                dto.setFullname(user.getLastname() + " " + user.getFirstname());
                dto.setBirthdate(user.getDateOfBirth());
                dto.setGender(user.getGender());
                dto.setPhoneNumber(user.getPhoneNumber());
                dto.setEmail(user.getEmail());
                dto.setAddress1(user.getPrimaryAddress());
                dto.setAddress2(user.getSencondAddress());
                dto.setRole(user.getRoleId().getRoleId());
                dto.setRegisterDate(user.getRegisterDate());
                dto.setAboutMe(user.getAboutMe());
                //set list ideabook
                IdeaBookJpaController ideabookJpa = new IdeaBookJpaController(emf);
                List<IdeabookIdNameDTO> listIdeabookDTO = ideabookJpa.getIdeabookIdAndNameByUserId(userId);
                dto.setListIdeabook(listIdeabookDTO);
                //set list project
                if (user.getRoleId().getRoleId() != Constant.ROLE_MEMBER) {
                    ProjectJpaController projectJpa = new ProjectJpaController(emf);
                    List<ProjectIdNameDTO> listProjectDTO = projectJpa.getProjectIdAndNameByUserId(userId);
                    dto.setListProject(listProjectDTO);
                }
                //set status
                dto.setStatus(user.getStatus());
                request.setAttribute(Constant.ATT_DETAIL, dto);
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("ERROR at " + Constant.LOAD_ACCOUNT_INFO_SERVLET + ": " + e.getMessage());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

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
