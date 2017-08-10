/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.UserJpaController;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LongVTHSE60972
 */
public class UserServlet extends HttpServlet {
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
        try (PrintWriter out = response.getWriter()) {
            String button = request.getParameter("btAction");
            String errMsg = "No error";
            int userIdNo = -1;
            switch (button) {
                case "banUser": {
                    String userId = request.getParameter("userId");
                    UserJpaController jpaController = new UserJpaController(emf);
                    
                    try {
                        userIdNo = Integer.parseInt(userId);
                        jpaController.banUser(userIdNo);
                    } catch (NumberFormatException nfe) {
                        errMsg = "User id is NaN";
                    } catch (NonexistentEntityException nee) {
                        errMsg = "This user is not exist";
                    } catch (Exception e) {
                        errMsg = "Unexpected exception";
                    }
                }
                break;
                case "activeUser": {
                    String userId = request.getParameter("userId");
                    UserJpaController jpaController = new UserJpaController(emf);
                    
                    try {
                        userIdNo = Integer.parseInt(userId);
                        jpaController.activeUser(userIdNo);
                    } catch (NumberFormatException nfe) {
                        errMsg = "User id is NaN";
                    } catch (NonexistentEntityException nee) {
                        errMsg = "This user is not exist";
                    } catch (Exception e) {
                        errMsg = "Unexpected exception";
                    }
                }
                break;
            }
            
            UserJpaController jpaController = new UserJpaController(emf);
            User user = jpaController.findUser(userIdNo);
            
            String status = "not found";
            if (user != null) {
                if (user.getStatus() == 1) {
                    status = "Active";
                } else if (user.getStatus() == -1) {
                    status = "Banned";
                } 
                
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + " - action: " + button +"</h1>");
            out.println("<h1>Status: " + status +"</h1>");
            out.println("<h1>Error: " + errMsg +"</h1>");
            
            out.println("</body>");
            out.println("</html>");
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

}
