/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.DAO.OrderDAO;
import hd.DAO.ProductDAO;
import hd.dto.Cart;
import hd.entity.Orders;
import hd.entity.Product;
import hd.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class ShoppingCartServlet extends HttpServlet {

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
            String method = request.getParameter("method");
            ProductDAO productDao = new ProductDAO();
            HttpSession session = request.getSession();
            if (method.equals("add")) {
                String productId = request.getParameter("productId");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                Product product = productDao.getProductByID(productId);
                addCart(session, product, quantity);
                // Todo: Response
            } else if (method.equals("show")) {
                request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
            } else if (method.equals("remove")) {
                String productId = request.getParameter("productId");
                removeCartItem(session, productId);
            } else if (method.equals("plus")) {
                String productId = request.getParameter("productId");
                plusQuantity(session, productId);
            } else if (method.equals("minus")) {
                String productId = request.getParameter("productId");
                minusQuantity(session, productId);
            } else if (method.equals("order")) {          
               if(session.getAttribute("cart")==null){
                   request.getRequestDispatcher("ShoppingCartServlet?method=purchase").forward(request, response);
               }else {
               List<Cart> listCart = (List<Cart>) session.getAttribute("cart");
               User user = (User) session.getAttribute("user");
               OrderDAO dao = new OrderDAO();
               dao.insertOrder(user);
               dao.inserOrderDetail(listCart);
               session.removeAttribute("cart");
               request.getRequestDispatcher("ShoppingCartServlet?method=purchase").forward(request, response);
               }
            } else if(method.endsWith("purchase")){
               User user = (User) session.getAttribute("user");
               OrderDAO dao = new OrderDAO();
               List<Orders> listOrder =  dao.getOrderByID(user.getUserId());
               request.setAttribute("listDTO", listOrder);
               request.getRequestDispatcher("purchase.jsp").forward(request, response);
               
            }
        }
    }
    
    private void addCart(HttpSession session, Product product, int quantity) {
        List<Cart> cartList = (List) session.getAttribute("cart");
        if (cartList == null || cartList.isEmpty()) {
            cartList = new ArrayList<>();
            addItemToCart(cartList, new Cart(product, quantity));
        } else {
            addItemToCart(cartList, new Cart(product, quantity));
        }
        session.setAttribute("cart", cartList);
    }
    
    private void addItemToCart(List<Cart> cartList, Cart cart) {
        if (isInList(cartList, cart.getProduct().getProductId())) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getProduct().getProductId().equals(cart.getProduct().getProductId())) {
                    cartList.get(i).setQuantity(cartList.get(i).getQuantity() + cart.getQuantity());
                }
            }
        } else {
            cartList.add(cart);
        }
    }
    
    private boolean isInList(List<Cart> cartList, String id) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getProduct().getProductId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    private void removeCartItem(HttpSession session, String productId) {
        List<Cart> cartList = (List) session.getAttribute("cart");
        if (cartList != null) {
            Cart cart = findCart(cartList, productId);
            if (cart != null) {
                cartList.remove(cart);
            }
        }
        session.setAttribute("cart", cartList);
    }
    
    private Cart findCart(List<Cart> cartList, String productId) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getProduct().getProductId().equals(productId)) {
                return cartList.get(i);
            }
        }
        return null;
    }
    
    private void plusQuantity(HttpSession session, String productId) {
        List<Cart> cartList = (List) session.getAttribute("cart");
        if (cartList != null) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getProduct().getProductId().equals(productId)) {
                    cartList.get(i).setQuantity(cartList.get(i).getQuantity() + 1);
                }
            }
        }
        session.setAttribute("cart", cartList);
    }
    
    private void minusQuantity(HttpSession session, String productId) {
        List<Cart> cartList = (List) session.getAttribute("cart");
        if (cartList != null) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getProduct().getProductId().equals(productId)) {
                    cartList.get(i).setQuantity(cartList.get(i).getQuantity() - 1);
                }
            }
        }
        session.setAttribute("cart", cartList);
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
