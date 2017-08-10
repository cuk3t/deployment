/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.DAO.CityDAO;
import hd.DAO.UserDAO;
import hd.entity.City;
import hd.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author cuk3t
 */
public class UpdateProfileServlet extends HttpServlet {

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
            
            HttpSession session = request.getSession();
            
            User user = (User) session.getAttribute("user");
            int id = user.getUserId();
            String firstname = request.getParameter("firstName");
            String lastname = request.getParameter("lastName");
            String dateStr = request.getParameter("birthDay");

                SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                java.util.Date date = null;
                try {
                    date = sdf1.parse(dateStr);
                } catch (ParseException ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            java.sql.Date birthday = new Date(date.getTime());
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            boolean gender1 = gender.equals("1");
            String nameCity = request.getParameter("city");
                
            CityDAO cityDao = new CityDAO();
            City city = cityDao.searchCity(nameCity);
            String address = request.getParameter("address");
            String aboutMe = request.getParameter("aboutMe");
            UserDAO userDao = new UserDAO();
            boolean check = false;
            check = userDao.updateUser(id, firstname, lastname, birthday, phone, gender1, city, address, aboutMe);
            if(check){
                
            request.getRequestDispatcher("ProfileServlet").forward(request, response);
            }
            else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
