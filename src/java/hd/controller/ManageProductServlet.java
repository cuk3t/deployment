/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lê Đại An
 */
public class ManageProductServlet extends HttpServlet {

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
            String url = "#";
            String button = request.getParameter(Constant.BTN_ACTION).toLowerCase();
            switch (button) {
                case Constant.WAITING:
                    url = Constant.LOAD_PRODUCTS_SERVLET;
                    break;
                case Constant.APPROVED:
                    url = Constant.LOAD_PRODUCTS_SERVLET;
                    break;
                case Constant.BLOCKED:
                    url = Constant.LOAD_PRODUCTS_SERVLET;
                    break;
                case Constant.VIEWDETAIL:
                    url = Constant.LOAD_PRODUCT_INFO_SERVLET;
                    break;
                case Constant.ACCEPT:
                    url = Constant.SET_STATUS_PRODUCT_SERVLET;
                    break;
                case Constant.BLOCK:
                    url = Constant.SET_STATUS_PRODUCT_SERVLET;
                    break;
                default:
                    break;
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("ERROR at ManageProjectServlet: " + e.getMessage());
            response.sendRedirect(Constant.LOGIN_ADMIN_PAGE);
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

}
