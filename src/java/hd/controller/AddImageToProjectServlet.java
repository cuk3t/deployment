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
import hd.entity.Category;

import hd.entity.IdeaBookPhoto;
import hd.entity.Project;
import hd.entity.Style;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Dell
 */
public class AddImageToProjectServlet extends HttpServlet {

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
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) { //to do
            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
                Iterator iter = items.iterator();
                Hashtable params = new Hashtable();
                String fileName = null;
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        params.put(item.getFieldName(), item.getString("UTF-8"));
                    } else if (!item.isFormField()) {
                        try {
                            long time = System.currentTimeMillis();
                            String itemName = item.getName();
                            fileName = time + itemName.substring(itemName.lastIndexOf("\\") + 1);
                            String RealPath = getServletContext().getRealPath("/") + "images\\" + fileName;
                            File savedFile = new File(RealPath);
                            item.write(savedFile);
                            String localPath = "D:\\Project\\TestHouseDecor-Merge\\web\\images\\" + fileName;
//                            savedFile = new File(localPath);
//                            item.write(savedFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //Init Jpa
                CategoryJpaController categoryJpa = new CategoryJpaController(emf);
                StyleJpaController styleJpa = new StyleJpaController(emf);
                ProjectJpaController projectJpa = new ProjectJpaController(emf);
                IdeaBookPhotoJpaController photoJpa = new IdeaBookPhotoJpaController(emf);
                // get Object Category by categoryId
                int cateId = Integer.parseInt((String) params.get("ddlCategory"));
                Category cate = categoryJpa.findCategory(cateId);
                // get Object Style by styleId
                int styleId = Integer.parseInt((String) params.get("ddlStyle"));
                Style style = styleJpa.findStyle(styleId);
                // get Object Project by projectId
                int projectId = Integer.parseInt((String) params.get("txtProjectId"));
                Project project = projectJpa.findProject(projectId);
                //Get param
                String title = (String) params.get("title");
                String description = (String) params.get("description");

                String url = "images/" + fileName;
                //Init IdeabookPhoto
                IdeaBookPhoto photo = new IdeaBookPhoto(title, url, description, cate, style, project);
                photoJpa.create(photo);
                url = "ViewMyProjectDetailServlet?txtProjectId=" + projectId;
                response.sendRedirect(url);
            }
        } catch (Exception e) {
            log("Error at AddImageToProjectServlet: " + e.getMessage());
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
