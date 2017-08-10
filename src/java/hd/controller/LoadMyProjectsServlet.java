/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.IdeaBookPhotoJpaController;
import hd.JPA.ProjectJpaController;
import hd.JPA.UserJpaController;
import hd.entity.Project;
import hd.entity.User;
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
 * @author Dell
 */
public class LoadMyProjectsServlet extends HttpServlet {

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
            String url = Constant.MY_PROJECTS_PAGE;
            HttpSession session = request.getSession();
            int userId = Integer.parseInt(request.getParameter("txtUserId"));
            User currentUser = (User) session.getAttribute(Constant.ATT_USER);
            List<MyProjectDTO> list;
            if (currentUser != null && isMyProject(currentUser.getUserId(), userId)) {
                list = loadOwnProjects(currentUser);
            } else {
                UserJpaController userJpa = new UserJpaController(emf);
                currentUser = userJpa.findUser(userId);
                list = loadOtherProjects(currentUser);
            }
            session.setAttribute("USERVIEW", currentUser);
            session.setAttribute(Constant.ATT_MY_PROJECTS, list);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("Error at LoadMyProjectsServlet: " + e.getMessage());
            response.sendRedirect("error.jsp");
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

    private boolean isMyProject(int currentUserId, int userId) {
        return currentUserId == userId;
    }

    private List<MyProjectDTO> loadOwnProjects(User currentUser) {
        ProjectJpaController projectJpa = new ProjectJpaController(emf);
        List<Project> projects = projectJpa.findProjectsByUserId(currentUser.getUserId());

        IdeaBookPhotoJpaController photoJpa = new IdeaBookPhotoJpaController(emf);
        List<MyProjectDTO> list = new ArrayList<>();
        for (Project project : projects) {
            String urlPhoto = photoJpa.getFirstPhotoByProjectId(project.getProjectId());
            MyProjectDTO dto = new MyProjectDTO(project, urlPhoto);
            list.add(dto);
        }
        return list;
    }

    private List<MyProjectDTO> loadOtherProjects(User user) {
        IdeaBookPhotoJpaController photoJpa = new IdeaBookPhotoJpaController(emf);
        ProjectJpaController projectJpa = new ProjectJpaController(emf);
        List<Project> projects = new ArrayList<>();
        List<Integer> approvedProjectId = photoJpa.findProjectsHavePhotoApproved(user.getUserId());
        for (Integer projectId : approvedProjectId) {
            Project project = projectJpa.findProject(projectId);
            projects.add(project);
        }
        List<MyProjectDTO> list = new ArrayList<>();
        for (Project project : projects) {
            String urlPhoto = photoJpa.getFirstApprovedPhotoByProjectId(project.getProjectId());
            MyProjectDTO dto = new MyProjectDTO(project, urlPhoto);
            list.add(dto);
        }
        return list;
    }
}
