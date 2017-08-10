/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.DAO.ProductDAO;
import hd.entity.Product;
import hd.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cuk3t
 */
public class MyProductServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String action = request.getParameter("action");
            if (action.equals("create")){
                if(session.getAttribute("user")==null){
                request.getRequestDispatcher("login.html").forward(request, response);
                }else{
                        int userID = user.getUserId();
                        String productID = request.getParameter("productCode")+"_"+ userID;                       
                        
                        String productName = request.getParameter("productName");
                        String barCode = request.getParameter("barCode");
                        float price = Float.parseFloat(request.getParameter("price"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));
                        String size = request.getParameter("size");
                        String material = request.getParameter("material");
                        String warranty = request.getParameter("warranty");
                        int style = Integer.parseInt(request.getParameter("style"));
                        int category = Integer.parseInt(request.getParameter("category"));
                        String description = request.getParameter("description");
                        ProductDAO productDao = new ProductDAO();
                        productDao.createProduct(productName, barCode, price, quantity, size, material, warranty, style, category, description, userID, productID);
                        request.getRequestDispatcher("MyProductServlet?action=show&txtUserID="+userID+"").forward(request, response);
                }
            } else if(action.equals("show")){
                
                int idtmp = 0;
                if(session.getAttribute("user")!=null){
                    idtmp=user.getUserId();
                }
                int userID = Integer.parseInt(request.getParameter("txtUserID"));
                if(idtmp== userID){
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> list = productDAO.getListProductBySeller(userID);
                    request.setAttribute("listDTO", list);
                    request.getRequestDispatcher("my_product.jsp").forward(request, response);
                } else{
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    
                }
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
