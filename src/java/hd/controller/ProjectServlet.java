///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package hd.controller;
//
//import hd.DAO.ProjectDAO;
//
//import hd.JPA.IdeaBookJpaController;
//import hd.JPA.ProjectJpaController;
//import hd.entity.Project;
//import hd.entity.User;
//import hd.helpers.RoleConstants;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.HttpMethodConstraint;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// *
// * @author nhantd60126
// */
//@WebServlet(name = "ProjectServlet", urlPatterns = {"/ProjectServlet"})
//public class ProjectServlet extends HttpServlet {
//
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestHouseDecorPU");
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            HttpSession session = request.getSession();
//            User user = (User) session.getAttribute("user");
//            if (user.getRoleId().getRoleId() == RoleConstants.PROFESSIONAL_ID) {
//                inProccessRequest(request, response);
//            } else {
//                request.getRequestDispatcher("error.jsp").forward(request, response);
//            }
//        }
//    }
//
//    protected void inProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        if ("add".equalsIgnoreCase(action)) {
//            inAddProccessRequest(request, response);
//        } else if ("edit".equalsIgnoreCase(action)) {
//            inEditProccessRequest(request, response);
//        } else {
//            inIndexProcessRequest(request, response);
//        }
//    }
//
//    private void inIndexProcessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        User user = (User) session.getAttribute("user");
//        ProjectDAO projectDAO = new ProjectDAO();
//        List<ProjectViewModel> projects = projectDAO.getListProjectOfPros(user.getUserId());
//        request.setAttribute("projects", projects);
//        request.getRequestDispatcher("project_index.jsp").forward(request, response);
//    }
//
//    private void inAddProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getMethod().equals("GET")) {
//            request.getRequestDispatcher("project_add.jsp").forward(request, response);
//        } else {
//            Project project = getProjectFromRequest(request);
//            ProjectJpaController projectJpa = new ProjectJpaController(emf);
//            int id = projectJpa.create(project);
//            response.sendRedirect("ProjectServlet?action=edit&id=" + id);
//        }
//    }
//
//    private Project getProjectFromRequest(HttpServletRequest request) {
//
//        String projectName = request.getParameter("projectName");
//        String address = request.getParameter("address");
//        float cost = Float.valueOf(request.getParameter("cost"));
//        String website = request.getParameter("website");
//
//        int year = Integer.parseInt(request.getParameter("year"));
//        String keywords = request.getParameter("keywords");
//        int status = Constant.STATUS_NOT_OK;
//
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//
//        Project project = new Project(projectName, address, cost, year, website, keywords, user);
//
//        project.setStatus(Constant.STATUS_NOT_OK);
//        if (request.getParameter("id") != null) {
//            project.setProjectId(Integer.parseInt(request.getParameter("id")));
//        }
//        return project;
//    }
//
//    private void inEditProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getMethod().equals("GET")) {
//            int id = Integer.parseInt(request.getParameter("id"));
//            if (id > 0) {
//                ProjectDAO projectDAO = new ProjectDAO();
//                Project project = projectDAO.getProjectByID(id);
//                request.setAttribute("project", project);
//                request.getRequestDispatcher("project_edit.jsp").forward(request, response);
//            }
//        } else {
//            try {
//                Project project = getProjectFromRequest(request);
//                
//                HttpSession session = request.getSession();
//                User user = (User) session.getAttribute("user");
//                project.setUserId(user);
//                
//                ProjectJpaController projectJpa = new ProjectJpaController(emf);
//                projectJpa.edit(project);
//                
//                response.sendRedirect("ProjectServlet?action=edit&id=" + project.getProjectId());
//            } catch (Exception ex) {
//                Logger.getLogger(ProjectServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
