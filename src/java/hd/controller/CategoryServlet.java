/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.CategoryJpaController;
import hd.JPA.TrackingJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LongVTHSE60972
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/CategoryServlet"})
public class CategoryServlet extends HttpServlet {
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
            /* TODO output your page here. You may use following sample code. */
            
            String button = request.getParameter("btAction");
            String errMsg = "No error";
            String msg = "No msg to show";
            int categoryIdNo = 0;
            int userIdNo = 0;
            switch (button) {
                case "enterCategory": {
                    String categoryId = request.getParameter("categoryId");
                    String userId = request.getParameter("userId");
                    TrackingJpaController jpaController = new TrackingJpaController(emf);
                    
                    try {
                        userIdNo = Integer.parseInt(userId);
                        categoryIdNo = Integer.parseInt(categoryId);
                        int result = jpaController.enterCategory(userIdNo, categoryIdNo);
                        msg = categoryId + " was enter " + result + " time";
                    } catch (NumberFormatException nfe) {
                        errMsg = "User id is NaN";
                    } catch (Exception e) {
                        errMsg = "Unexpected exception";
                    }
                }
                break;
                case "removeOutdateTracking": {
                    String productId = request.getParameter("productId");
                    String quantity = request.getParameter("quantity");
                    String outdateDays = request.getParameter("outdateDays");
                    TrackingJpaController jpaController = new TrackingJpaController(emf);
                    
                    try {
                        categoryIdNo = Integer.parseInt(productId);
                        Integer quantityNo = Integer.parseInt(quantity);
                        
                        
                    } catch (NumberFormatException nfe) {
                        errMsg = "User id is NaN";
                    } catch (Exception e) {
                        errMsg = "Unexpected exception";
                    }
                }
                break;
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>Msg: " + msg + "</h1>");
            out.println("<h1>ErrMsg: " + errMsg + "</h1>");
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
