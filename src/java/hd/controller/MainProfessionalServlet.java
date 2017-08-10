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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dell
 */
public class MainProfessionalServlet extends HttpServlet {

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
            String button = request.getParameter(Constant.BTN_ACTION).toLowerCase();
            String url = "#";
            switch (button) {
                case Constant.LOAD:
                    session.removeAttribute(Constant.ATT_ERROR_CREATE_PROJECT);
                    url = Constant.LOAD_MY_PROJECTS_SERVLET;
                    break;
                case Constant.CREATE:
                    url = Constant.ADD_PROJECT_SERVLET;
                    break;
                case Constant.VIEWDETAIL:
                    url = Constant.VIEW_MY_PROJECT_DETAIL_SERVLET;
                    break;
                case "deletephoto":
                    url = Constant.DELETE_PROJECT_PHOTO_SERVLET;
                    break;
                case "deleteproject":
                    url = Constant.DELETE_PROJECT_SERVLET;
                    break;
                case "update":
                    url = Constant.UPDATE_PROJECT_SERVLET;
                    break;
                case "addphoto":
                    url = Constant.ADD_IMAGE_TO_PROJECT_SERVLET;
                    break;
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("Error at MainProfessionalServlet: " + e.getMessage());
            response.sendRedirect(Constant.ERROR_PAGE);
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
