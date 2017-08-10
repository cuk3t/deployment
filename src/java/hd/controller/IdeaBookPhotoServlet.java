/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.DAO.ProjectDAO;
import hd.JPA.CategoryJpaController;
import hd.JPA.IdeaBookJpaController;
import hd.JPA.IdeaBookPhotoJpaController;
import hd.JPA.ProjectJpaController;
import hd.JPA.StyleJpaController;
import hd.entity.IdeaBookPhoto;
import hd.entity.Project;
import hd.entity.User;
import hd.helpers.RoleConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author nhantd60126
 */
@WebServlet(name = "IdeaBookPhotoServlet", urlPatterns = {"/IdeaBookPhotoServlet"})
@MultipartConfig
public class IdeaBookPhotoServlet extends HttpServlet {
    
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
            User user = (User) session.getAttribute("user");
            if (user.getRoleId().getRoleId() == RoleConstants.PROFESSIONAL_ID) {
                inProccessRequest(request, response);
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            log("Error at IdeaBookPhotoServlet: " + e.getMessage());
        }
    }
    
    protected void inProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equalsIgnoreCase(action)) {
            inAddProccessRequest(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            inEditProccessRequest(request, response);
        }
    }
    
    private void inAddProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getMethod().equals("GET")) {
            int projectId = Integer.parseInt(request.getParameter("projectId"));
            request.setAttribute("projectId", projectId);
            putReferenceObjects(request, response);
            request.getRequestDispatcher("idea_book_photo_add.jsp").forward(request, response);
        } else {
            IdeaBookPhoto ideaBookPhoto = getIdeaBookPhotoFromRequest(request);
            if (ideaBookPhoto != null) {
                IdeaBookPhotoJpaController ideaBookJpa = new IdeaBookPhotoJpaController(emf);
                ideaBookJpa.create(ideaBookPhoto);
                response.sendRedirect("ProjectServlet?action=edit&id=" + ideaBookPhoto.getProjectId().getProjectId());
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }
    
    private void inEditProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id > 0) {
                IdeaBookPhotoJpaController ideaBookJpa = new IdeaBookPhotoJpaController(emf);
                IdeaBookPhoto ideaBookPhoto = ideaBookJpa.findIdeaBookPhoto(id);
                request.setAttribute("ideaBookPhoto", ideaBookPhoto);
                putReferenceObjects(request, response);
                request.getRequestDispatcher("idea_book_photo_edit.jsp").forward(request, response);
            }
        } else {
            IdeaBookPhotoJpaController ideaBookJpa = new IdeaBookPhotoJpaController(emf);
            
            IdeaBookPhoto ideaBookPhoto = getIdeaBookPhotoFromRequest(request);
            IdeaBookPhoto oldIdeaBookPhoto = ideaBookJpa.findIdeaBookPhoto(ideaBookPhoto.getPhotoId());
            
            if (ideaBookPhoto.getUrl() == null) {
                ideaBookPhoto.setUrl(oldIdeaBookPhoto.getUrl());
            }
            try {
                ideaBookJpa.edit(ideaBookPhoto);
                response.sendRedirect("ProjectServlet?action=edit&id=" + ideaBookPhoto.getProjectId().getProjectId());
            } catch (Exception ex) {
                Logger.getLogger(IdeaBookPhotoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private IdeaBookPhoto getIdeaBookPhotoFromRequest(HttpServletRequest request) throws IOException, ServletException {
        StyleJpaController styleJpa = new StyleJpaController(emf);
        CategoryJpaController categoryJpa = new CategoryJpaController(emf);
        ProjectJpaController projectJpa = new ProjectJpaController(emf);
        
        IdeaBookPhoto ideabookPhoto = new IdeaBookPhoto();
        Part filePart = request.getPart("image");
        
        if (filePart.getSize() != 0) {
            InputStream fileContent = filePart.getInputStream();
            String fileName = generateFileName();
            String path = getServletContext().getRealPath("/photo/ideabookphoto/");
            Path ideabookPhotoPath = Paths.get(path + fileName);
            Files.copy(fileContent, ideabookPhotoPath, StandardCopyOption.REPLACE_EXISTING);
            ideabookPhoto.setUrl("photo/ideabookphoto/" + fileName);
        }
        
        ideabookPhoto.setTilte(request.getParameter("title"));
        ideabookPhoto.setDescription(request.getParameter("description"));
        
        ideabookPhoto.setStatus(Constant.STATUS_OK);
        
        int categoryId = Integer.valueOf(request.getParameter("categoryId"));
        ideabookPhoto.setCategoryId(categoryJpa.findCategory(categoryId));
        
        int styleId = Integer.valueOf(request.getParameter("styleId"));
        ideabookPhoto.setStyleId(styleJpa.findStyle(styleId));
        
        int projectId = Integer.valueOf(request.getParameter("projectId"));
        Project project = projectJpa.findProject(projectId);
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (!Objects.equals(project.getProfessionalUserId().getUserId(), user.getUserId())) {
            return null;
        }
        ideabookPhoto.setProjectId(project);
        
        if (request.getParameter("id") != null) {
            ideabookPhoto.setPhotoId(Integer.parseInt(request.getParameter("id")));
        }
        
        return ideabookPhoto;
        
    }
    
    private String generateFileName() {
        Date date = Calendar.getInstance().getTime();
        return (date.getYear() + 1990) + "_" + date.getMonth() + "_" + date.getDay() + "_" + date.getHours() + "_" + date.getHours() + "_" + date.getMinutes() + "_" + date.getSeconds() + ".png";
    }
    
    private void putReferenceObjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StyleJpaController styleJpa = new StyleJpaController(emf);
        CategoryJpaController categoryJpa = new CategoryJpaController(emf);
        
        request.setAttribute("categories", categoryJpa.findCategoryEntities());
        request.setAttribute("styles", styleJpa.findStyleEntities());
        
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

}
