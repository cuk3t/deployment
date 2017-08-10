/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.DAO.ProfessionalDAO;
import hd.dto.ProfessionalDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cuk3t
 */
public class showProfessionalServlet extends HttpServlet {

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
            ProfessionalDAO dao = new ProfessionalDAO();
            if(button.equals("All")){              
                
                List<ProfessionalDTO> listDTO = dao.SearchPros("", "", "");
                request.setAttribute("listDTO", listDTO);
                
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
            } 
            else if(button.equals("1")){
                List<ProfessionalDTO> listDTO = dao.SearchPros("", "1", "");
//                        dao.getProsByTypeWork("1");
                request.setAttribute("listDTO", listDTO);
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
             }
            else if(button.equals("2")){
                List<ProfessionalDTO> listDTO = dao.SearchPros("", "2", "");
                request.setAttribute("listDTO", listDTO);
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
             }
            else if(button.equals("3")){
                List<ProfessionalDTO> listDTO = dao.SearchPros("", "3", "");
                request.setAttribute("listDTO", listDTO);
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
             }
            else if(button.equals("4")){
                List<ProfessionalDTO> listDTO = dao.SearchPros("", "4", "");
                request.setAttribute("listDTO", listDTO);
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
             }
            else if(button.equals("5")){
                List<ProfessionalDTO> listDTO = dao.SearchPros("", "5", "");
                request.setAttribute("listDTO", listDTO);
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
             }
            else if(button.equals("Find Pros")){
                String cityCode = request.getParameter("city");
                String typeofworkID = request.getParameter("typeWork");
                String prosName = request.getParameter("txtProsName");
                List<ProfessionalDTO> listDTO = dao.SearchPros(prosName, typeofworkID, cityCode);
                request.setAttribute("listDTO", listDTO);
                request.getRequestDispatcher("showListProfessional.jsp").forward(request, response);
            }
                    
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
