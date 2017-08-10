/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.ProfessionalJpaController;
import hd.JPA.ProjectJpaController;
import hd.entity.Professional;
import hd.entity.Project;
import hd.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
 * @author Dell
 */
public class AddProjectServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String url = Constant.LOGIN_PAGE;
            HttpSession session = request.getSession();
            if (session.getAttribute(Constant.ATT_USER) != null) {
                User user = (User) session.getAttribute(Constant.ATT_USER);
                ProfessionalJpaController proJpa = new ProfessionalJpaController(emf);
                Professional professional = proJpa.findProfessional(user.getUserId());
                String projectName = request.getParameter(Constant.PARAM_PROJECT_NAME);
                byte[] bytes = projectName.getBytes(StandardCharsets.ISO_8859_1);
                projectName = new String(bytes, StandardCharsets.UTF_8);
                String projectAddress = request.getParameter(Constant.PARAM_ADDRESS);
                bytes = projectAddress.getBytes(StandardCharsets.ISO_8859_1);
                projectAddress = new String(bytes, StandardCharsets.UTF_8);
                Float projectCost = 0f;
                int projectYear = 0;
                try {
                    projectCost = Float.parseFloat(request.getParameter(Constant.PARAM_COST));
                    projectYear = Integer.parseInt(request.getParameter(Constant.PARAM_YEAR));
                } catch (Exception e) {
                    log("Error parse");
                }
                String projectWebsite = request.getParameter(Constant.PARAM_WEBSITE);
                String projectKey = request.getParameter(Constant.PARAM_KEY);
                ProjectJpaController projectJpa = new ProjectJpaController(emf);
                if (!projectJpa.isExistedInMyProjects(user.getUserId(), projectName)) {
                    projectJpa.create(new Project(projectName,
                            projectAddress,
                            projectCost,
                            projectWebsite,
                            projectYear,
                            projectKey,
                            Constant.STATUS_NO_IMAGE,
                            professional));
                    session.removeAttribute(Constant.ATT_ERROR_CREATE_PROJECT);
                } else {
                    ErrorCreateProjectDTO error = new ErrorCreateProjectDTO(projectName,
                            projectAddress,
                            String.valueOf(projectCost),
                            projectWebsite,
                            String.valueOf(projectYear),
                            projectKey);
                    session.setAttribute(Constant.ATT_ERROR_CREATE_PROJECT, error);
                }
                url = "LoadMyProjectsServlet?txtUserId=" + user.getUserId();
            }
//            request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect(url);
        } catch (Exception e) {
            log("Error at AddProjectServlet: " + e.getMessage());
            response.sendRedirect(Constant.LOGIN_PAGE);
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
