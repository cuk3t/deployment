/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import hd.JPA.ProductJpaController;
import hd.JPA.ProductPhotoJpaController;
import hd.entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lê Đại An
 */
public class LoadProductsServlet extends HttpServlet {

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
            HttpSession session = request.getSession();
            String button = request.getParameter(Constant.BTN_ACTION).toLowerCase();
            int status = Constant.STATUS_WAIT;
            switch (button) {
                case Constant.APPROVED:
                    status = Constant.STATUS_OK;
                    break;
                case Constant.BLOCKED:
                    status = Constant.STATUS_NOT_OK;
                    break;
            }
            session.removeAttribute(Constant.ATT_PROJECT_LIST);
            String url = Constant.LOGIN_ADMIN_PAGE;
            if (session.getAttribute(Constant.ATT_ADMIN) != null) {
                url = Constant.PRODUCT_PAGE;
                ProductJpaController productJpa = new ProductJpaController(emf);
                List<Product> list = new ArrayList<>();
                switch (status) {
                    case Constant.STATUS_OK:
                        list = productJpa.getProductsByStatus(Constant.STATUS_OK);
                        break;
                    case Constant.STATUS_WAIT:
                        list = productJpa.getProductsByStatus(Constant.STATUS_WAIT);
                        break;
                    case Constant.STATUS_NOT_OK:
                        list = productJpa.getProductsByStatus(Constant.STATUS_NOT_OK);
                        break;
                    default:
                        break;
                }
                List<ProductDTO> products = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    ProductDTO product = new ProductDTO();
                    product.setProduct(list.get(i));
                    String productId = product.getProduct().getProductId();
                    //Load first product photo
                    ProductPhotoJpaController productPhotoJpa = new ProductPhotoJpaController(emf);
                    String urlPhoto = productPhotoJpa.getFirstUrlProductPhotoByProductId(productId);
                    product.setUrlPhoto(urlPhoto);
                    //Load product categories
//                    List<String> categories = productPhotoJpa.getCategoryProductByProductId(productId);
//                    for (int j = 0; j < categories.size(); j++) {
//                        product.setCategory(product.getCategory() + categories.get(j) + " ");
//                    }
                    //Load product styles
//                    List<String> styles = productPhotoJpa.getStyleProductByProductId(productId);
//                    for (int j = 0; j < styles.size(); j++) {
//                        product.setStyle(product.getStyle() + styles.get(j) + " ");
//                    }
                    //Add to list products
                    products.add(product);
                }
                session.setAttribute(Constant.ATT_LIST, products);
            }
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log("ERROR at LoadProductsServlet: " + e.getMessage());
            response.sendRedirect(Constant.ERROR_PAGE);
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

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
