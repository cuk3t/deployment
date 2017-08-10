/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.IdeaBookPhotoJpaController;
import hd.JPA.ProjectJpaController;
import hd.entity.Project;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class LoadProjectsServlet extends HttpServlet {
    
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
            int status = Constant.STATUS_WAIT;
            String button = request.getParameter(Constant.BTN_ACTION).toLowerCase();
            if (button.equals(Constant.APPROVED)) {
                status = Constant.STATUS_OK;
            }
            session.removeAttribute(Constant.ATT_PROJECT_LIST);
            String url = Constant.LOGIN_ADMIN_PAGE;
            if (session.getAttribute(Constant.ATT_ADMIN) != null) {
                List<ProjectDTO> list = new ArrayList<>();
                switch (status) {
                    case Constant.STATUS_OK:
                        loadProjectByStatus(list, Constant.STATUS_OK);
                        url = Constant.PROJECT_PAGE;
                        break;
                    case Constant.STATUS_WAIT:
                        loadProjectByStatus(list, Constant.STATUS_WAIT);
                        url = Constant.PROJECT_PAGE;
                        break;
                    default:
                        break;
                }
                session.setAttribute(Constant.ATT_LIST, list);
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("ERROR at LoadProjectsServlet: " + e.getMessage());
            response.sendRedirect(Constant.ERROR_PAGE);
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
    
    private void loadProjectByStatus(List<ProjectDTO> list, int status) {
        ProjectJpaController projectJpa = new ProjectJpaController(emf);
        IdeaBookPhotoJpaController ideabookPhotoJpa = new IdeaBookPhotoJpaController(emf);
        List<Project> listProject = projectJpa.getProjectsByStatus(status);
        //Load product categories
        for (int i = 0; i < listProject.size(); i++) {
            ProjectDTO project = new ProjectDTO();
            project.setProject(listProject.get(i));
            //Load ideabook categories
            List<String> categories = ideabookPhotoJpa.getCategoryProjectByProjectId(listProject.get(i).getProjectId());
            for (int j = 0; j < categories.size(); j++) {
                project.setCategory(project.getCategory() + categories.get(j) + " ");
            }
            //Load ideabook styles
            List<String> styles = ideabookPhotoJpa.getStyleProjectByProjectId(listProject.get(i).getProjectId());
            for (int j = 0; j < styles.size(); j++) {
                project.setStyle(project.getStyle() + styles.get(j) + " ");
            }
            //Add to list
            list.add(project);
        }
    }
    
}
