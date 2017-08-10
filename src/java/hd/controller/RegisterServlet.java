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
public class RegisterServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String url = "";
            String email = request.getParameter("email");
            UserDAO dao = new UserDAO();
            User user = dao.getUserByEmail(email);
            if (user != null) {
                request.setAttribute("emailIsExist", "Email is exist in system.");
                url = "register.jsp";
                request.getRequestDispatcher(url).forward(request, response);
            }
            
            else {
                String password = request.getParameter("password");
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

                //Date birthday = Date.valueOf(date);
                String phone = request.getParameter("phone");
                String gender = request.getParameter("gender");
                boolean gender1 = gender.equals("1");
                String nameCity = request.getParameter("city");
                
                CityDAO cityDao = new CityDAO();
                City city = cityDao.searchCity(nameCity);
                String address = request.getParameter("address");

                dao.insertMember(email, password, lastname, firstname, birthday, phone, gender1, city, address);
                
                HttpSession session = request.getSession();
                User user2 = dao.getUserByEmail(email);
                session.setAttribute("user", user2);
                request.getRequestDispatcher("LoginServlet").forward(request, response);
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
