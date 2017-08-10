/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.ProfessionalJpaController;
import hd.JPA.SellerInfoJpaController;
import hd.JPA.TypeOfWorkJpaController;
import hd.JPA.UserJpaController;
import hd.JPA.exceptions.PreexistingEntityException;
import hd.entity.Professional;
import hd.entity.SellerInfo;
import hd.entity.TypeOfWork;
import hd.entity.User;
import hd.helpers.RoleConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nhantd60126
 */
@WebServlet(name = "SellerServlet", urlPatterns = {"/SellerServlet"})
public class SellerServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user.getRoleId().getRoleId() == RoleConstants.PROFESSIONAL_ID) {
                inProccessRequest(request, response);
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    protected void inProccessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        SellerInfoJpaController sellerInfoJpaController = new SellerInfoJpaController(emf);
        SellerInfo currentSellerInfo = sellerInfoJpaController.findSellerInfo(user.getUserId());
        
        if (request.getMethod().equals("GET")) {
            request.setAttribute("seller", currentSellerInfo);
        } else {
            String taxNumber = request.getParameter("taxNumber");
            String storeAddress = request.getParameter("storeAddress");
            String sellerName = request.getParameter("sellerName");
            String phone = request.getParameter("phone");

            SellerInfo sellerInfo = new SellerInfo();
            sellerInfo.setTaxNumber(taxNumber);
            sellerInfo.setStoreAddress(storeAddress);
            sellerInfo.setSellerName(sellerName);
            sellerInfo.setPhone(phone);
            sellerInfo.setStartDate(new Date());
            Date dueDate = new Date();
            dueDate.setYear(dueDate.getYear()+1);
            sellerInfo.setDueDate(dueDate);
            sellerInfo.setUserId(user.getUserId());
            
           try {
                if (currentSellerInfo == null) {
                    sellerInfoJpaController.create(sellerInfo);
                } else {
                    sellerInfoJpaController.edit(sellerInfo);
                }
            } catch (Exception ex) {
                Logger.getLogger(SellerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            response.sendRedirect("ProjectServlet");
            return;
        }

        request.getRequestDispatcher("seller.jsp").forward(request, response);
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
