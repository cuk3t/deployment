/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.ProfessionalJpaController;
import hd.JPA.TypeOfWorkJpaController;
import hd.JPA.UserJpaController;
import hd.JPA.exceptions.PreexistingEntityException;
import hd.entity.Professional;
import hd.entity.TypeOfWork;
import hd.entity.User;
import hd.helpers.RoleConstants;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ProfessionalServlet", urlPatterns = {"/ProfessionalServlet"})
public class ProfessionalServlet extends HttpServlet {

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
        ProfessionalJpaController professionalJpaController = new ProfessionalJpaController(emf);
        
        TypeOfWorkJpaController typeOfWorkJpaController = new TypeOfWorkJpaController(emf);
        List<TypeOfWork> typeOfWorks = typeOfWorkJpaController.findTypeOfWorkEntities();
        request.setAttribute("typeOfWorks", typeOfWorks);
        
        Professional currentProfessional = professionalJpaController.findProfessional(user.getUserId());
        if (request.getMethod().equals("GET")) {

            request.setAttribute("professional", currentProfessional);
        } else {
            String professionalName = request.getParameter("professionalName");
            String address = request.getParameter("address");
            int typeOfWorkId = Integer.parseInt(request.getParameter("typeOfWorkId").toString());

            Professional professional = new Professional();
            professional.setProfessionalName(professionalName);
            professional.setAddress(address);
            professional.setTypeOfWorkId(typeOfWorkJpaController.findTypeOfWork(typeOfWorkId));
            professional.setUserId(user.getUserId());

            try {

                if (currentProfessional == null) {
                    professionalJpaController.create(professional);
                } else {
                    professionalJpaController.edit(professional);
                }
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(ProfessionalServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ProfessionalServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            response.sendRedirect("ProjectServlet");
            return;
        }

        request.getRequestDispatcher("professionals.jsp").forward(request, response);
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
