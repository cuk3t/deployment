/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.CategoryJpaController;
import hd.dto.CategoryDTO;
import hd.entity.Category;
import hd.service.HDSystem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
 * @author LongVTHSE60972
 */
@WebServlet(name = "DemoServlet", urlPatterns = {"/DemoServlet"})
public class DemoServlet extends HttpServlet {

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
            HDSystem system = new HDSystem();
            system.addTracking(1, 2);
            /* TODO output your page here. You may use following sample code. */
//            CategoryJpaController catController = new CategoryJpaController(emf);
//            List<Category> catList = catController.findCategoryEntities();
//            List<CategoryDTO> dtoList = new ArrayList<>();
//
//            for (Category categoryEntity : catList) {
//                CategoryDTO dto = new CategoryDTO(categoryEntity);
//                dtoList.add(dto);
//            }
//
//            System.out.println("dtolist size" + dtoList.size());
//            request.setAttribute("CATEGORY_LIST", dtoList);
//            request.getRequestDispatcher("banUserDemo.jsp").forward(request, response);
            response.sendRedirect(Constant.LOGIN_ADMIN_PAGE);
        } catch (Exception e) {
            log("ERROR: " + e.getMessage());
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
