/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.ProductJpaController;
import hd.JPA.exceptions.NonexistentEntityException;
import hd.entity.Product;
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
public class ProductServlet extends HttpServlet {

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
            String button = request.getParameter("btAction");
            String errMsg = "No error";
            String productId = request.getParameter("productId");

            switch (button) {
                case "buyProduct": {

                    String quantity = request.getParameter("quantity");
                    ProductJpaController jpaController = new ProductJpaController(emf);

                    try {
                        Integer quantityNo = Integer.parseInt(quantity);
                        int result = jpaController.buyProduct(productId, quantityNo);
                        if (result < 0) {
                            errMsg = "Product out of stock";
                        }
                    } catch (NumberFormatException nfe) {
                        errMsg = "User id is NaN";
                    } catch (NonexistentEntityException nee) {
                        errMsg = "This user is not exist";
                    } catch (Exception e) {
                        errMsg = "Unexpected exception";
                    }
                }
                break;
                case "changeProductQuantity": {
                    String quantity = request.getParameter("quantity");
                    ProductJpaController jpaController = new ProductJpaController(emf);

                    try {
                        Integer quantityNo = Integer.parseInt(quantity);
                        jpaController.changeProductQuantity(productId, quantityNo);
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

            ProductJpaController jpaController = new ProductJpaController(emf);
            Product p = jpaController.findProduct(productId);

            int curQuantity = -1;
            if (p != null) {
                curQuantity = p.getQuantity();
            }

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>Error msg: " + errMsg + "</h1>");
            out.println("<h1>Current quantity " + curQuantity + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            log("Error at ProductServlet: " + e.getMessage());
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
