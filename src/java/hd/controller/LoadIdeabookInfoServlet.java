/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.IdeaBookJpaController;
import hd.JPA.IdeaBookPhotoJpaController;
import hd.entity.IdeaBook;
import hd.entity.IdeaBookPhoto;
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
public class LoadIdeabookInfoServlet extends HttpServlet {

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
                url = Constant.IDEABOOK_INFO_PAGE;
                int ideabookId = Integer.parseInt(request.getParameter(Constant.PARAM_IDEABOOK_ID));
                IdeaBookJpaController ideabookJpa = new IdeaBookJpaController(emf);
                IdeaBook ideabook = ideabookJpa.findIdeaBook(ideabookId);
                IdeaBookPhotoJpaController ideabookPhotoJpa = new IdeaBookPhotoJpaController(emf);
                List<IdeaBookPhoto> listPhoto = ideabookPhotoJpa.getPhotosByIdeabookId(ideabookId);
                int select = 0;
                if(session.getAttribute(Constant.ATT_SELECT)!=null){
                    select = (int) session.getAttribute(Constant.ATT_SELECT);
                }
                for (int i = 0; i < listPhoto.size(); i++) {
                    IdeaBookPhoto photo = listPhoto.get(i);
                    if (photo.getStatus() == Constant.STATUS_WAIT) {
                        select = i;
                        break;
                    }
                }
                session.setAttribute(Constant.ATT_SELECT, select);
                session.setAttribute(Constant.ATT_DETAIL, ideabook);
                session.setAttribute(Constant.ATT_LIST, listPhoto);
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("ERROR at " + Constant.LOAD_IDEABOOK_INFO_SERVLET + ": " + e.getMessage());
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
