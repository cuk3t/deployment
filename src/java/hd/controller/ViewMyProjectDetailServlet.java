/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.CategoryJpaController;
import hd.JPA.IdeaBookPhotoJpaController;
import hd.JPA.ProjectJpaController;
import hd.JPA.StyleJpaController;
import hd.JPA.UserJpaController;
import hd.entity.Category;
import hd.entity.IdeaBookPhoto;
import hd.entity.Project;
import hd.entity.Style;
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
public class ViewMyProjectDetailServlet extends HttpServlet {

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
            ProjectJpaController projectJpa = new ProjectJpaController(emf);
            IdeaBookPhotoJpaController photoJpa = new IdeaBookPhotoJpaController(emf);
            String url = "project_detail.jsp";

            User currentUser = (User) session.getAttribute(Constant.ATT_USER);
            int projectId = Integer.parseInt(request.getParameter("txtProjectId"));
            Project project = projectJpa.findProject(projectId);

            List<MyProjectDTO> list = new ArrayList<>();
            List<IdeaBookPhoto> photos = new ArrayList<>();
            if (currentUser != null && isMyProject(currentUser.getUserId(), project.getProfessionalUserId().getUserId())) {
                photos = photoJpa.getPhotosByProjectId(projectId);
                list = loadOwnProjects(currentUser);
                CategoryJpaController cateJpa = new CategoryJpaController(emf);
                StyleJpaController styleJpa = new StyleJpaController(emf);
                List<Category> categories = cateJpa.findCategoryEntities();
                session.setAttribute("CATE", categories);
                List<Style> styles = styleJpa.findStyleEntities();
                session.setAttribute("STYLE", styles);
            } else {
                UserJpaController userJpa = new UserJpaController(emf);
                currentUser = userJpa.findUser(project.getProfessionalUserId().getUserId());
                photos = photoJpa.getApprovedPhotosByProjectId(projectId);
                list = loadOtherProjects(currentUser);
            }

            session.setAttribute(Constant.ATT_MY_PROJECTS, list);
            session.setAttribute("PROJECT", project);
            session.setAttribute("USERVIEW", currentUser);
            session.setAttribute("MYPROJECTDETAIL", photos);
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("Error at ViewMyProject: " + e.getMessage());
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
            MyProjectDTO dto = new MyProjectDTO();
            dto.setProject(project);
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
            MyProjectDTO dto = new MyProjectDTO();
            dto.setProject(project);
            list.add(dto);
        }
        return list;
    }

}
