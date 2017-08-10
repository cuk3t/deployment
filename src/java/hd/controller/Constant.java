/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd.controller;

import java.io.Serializable;

/**
 *
 * @author Lê Đại An
 */
public class Constant implements Serializable {

    /*-------------------SERVLET--------------------------*/
    public static final String MAIN_ADMIN_SERVLET = "MainAdminServlet";
    public static final String MANAGE_IDEABOOK_SERVLET = "ManageIdeabookServlet";
    public static final String MANAGE_PROJECT_SERVLET = "ManageProjectServlet";
    public static final String MANAGE_PRODUCT_SERVLET = "ManageProductServlet";
    public static final String LOGOUT_ADMIN_SERVLET = "LogoutAdminServlet";
    public static final String LOGIN_ADMIN_SERVLET = "LoginAdminServlet";
    public static final String LOAD_ACCOUNTS_SERVLET = "LoadAccountsServlet";
    public static final String LOAD_IDEABOOKS_SERVLET = "LoadIdeabooksServlet";
    public static final String LOAD_PRODUCTS_SERVLET = "LoadProductsServlet";
    public static final String LOAD_PROJECTS_SERVLET = "LoadProjectsServlet";
    public static final String LOAD_ACCOUNT_INFO_SERVLET = "LoadAccountInfoServlet";
    public static final String LOAD_PROJECT_INFO_SERVLET = "LoadProjectInfoServlet";
    public static final String LOAD_PRODUCT_INFO_SERVLET = "LoadProductInfoServlet";
    public static final String LOAD_IDEABOOK_INFO_SERVLET = "LoadIdeabookInfoServlet";
    public static final String SET_STATUS_ACCOUNT_SERVLET = "SetStatusAccountServlet";
    public static final String SET_STATUS_PROJECT_SERVLET = "SetStatusProjectServlet";
    public static final String LOAD_PROJECT_SERVLET = "LoadProjectsServlet";
    public static final String SET_STATUS_PRODUCT_SERVLET = "SetStatusProductServlet";
    public static final String SET_STATUS_IDEABOOK_SERVLET = "SetStatusIdeabookServlet";
    public static final String SET_STATUS_PHOTO_SERVLET = "SetStatusPhotoServlet";
    public static final String SELECT_PHOTO_SERVLET = "SelectPhotoServlet";
    public static final String LOAD_MY_PROJECTS_SERVLET = "LoadMyProjectsServlet";
    public static final String ADD_PROJECT_SERVLET = "AddProjectServlet";
    public static final String MAIN_PROFESSIONAL_SERVLET = "MainProfessionalServlet";
    public static final String VIEW_MY_PROJECT_DETAIL_SERVLET = "ViewMyProjectDetailServlet";
    public static final String DELETE_PROJECT_PHOTO_SERVLET = "DeleteProjectPhotoServlet";
    public static final String DELETE_PROJECT_SERVLET = "DeleteProjectServlet";
    public static final String UPDATE_PROJECT_SERVLET = "UpdateProjectServlet";
    public static final String ADD_IMAGE_TO_PROJECT_SERVLET = "AddImageToProjectServlet";
    public static final String LOAD_SELLERS_SERVLET = "LoadSellersServlet";
    public static final String LOAD_SELLER_INFO_SERVLET = "LoadSellerInfoServlet";
    public static final String UPDATE_SELLER_DATE_SERVLET = "UpdateSellerDateServlet";
    
    /*-------------------PAGE--------------------------*/
    public static final String LOGIN_ADMIN_PAGE = "login-admin.jsp";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String HOME_PAGE = "home.jsp";
    public static final String ERROR_PAGE = "admin/error.jsp";
    public static final String BLOCK_LIST_PAGE = "admin/blocked-accounts.jsp";
    public static final String BLOCK_PROJECTS_PAGE = "admin/blocked-projects.jsp";
    public static final String BLOCK_PRODUCTS_PAGE = "admin/blocked-products.jsp";
    public static final String ADMIN_HOME_PAGE = "admin/home.jsp";
    public static final String APPROVE_PRODUCT_PAGE = "admin/approved-product.jsp";
    public static final String ACCOUNT_PAGE = "admin/account.jsp";
    public static final String MANAGE_ACCOUNT_PROFESSIONAL_PAGE = "admin/manage-professional.jsp";
    public static final String ACCOUNT_INFO_PAGE = "admin/account-info.jsp";
    public static final String PRODUCT_INFO_PAGE = "admin/product_info.jsp";
    public static final String PROJECT_INFO_PAGE = "admin/project-info.jsp";
    public static final String IDEABOOK_INFO_PAGE = "admin/ideabook-info.jsp";
    public static final String MANAGE_PROJECT_PAGE = "admin/manage-project.jsp";
    public static final String PROJECT_PAGE = "admin/project.jsp";
    public static final String IDEABOOK_PAGE = "admin/ideabook.jsp";
    public static final String PRODUCT_PAGE = "admin/product.jsp";
    public static final String SELLER_PAGE = "admin/seller.jsp";
    public static final String SELLER_INFO_PAGE = "admin/seller_info.jsp";
    public static final String MY_PROJECTS_PAGE = "my_projects.jsp";
    /*-------------------STRING BUTTON--------------------------*/
    public static final String ADMIN = "admin";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String VIEWDETAIL = "viewdetail";
    public static final String ACCEPT = "accept";
    public static final String BLOCK = "block";
    public static final String ACCEPT_PHOTO = "acceptphoto";
    public static final String BLOCK_PHOTO = "blockphoto";
    public static final String ACTION = "action";
    public static final String LOAD = "load";
    public static final String LOAD_PROJECT = "loadprojects";
    public static final String MEMBER = "mem";
    public static final String PROFESSIONAL = "pro";
    public static final String BLACK_LIST = "blacklist";
    public static final String WAITING = "waiting";
    public static final String APPROVED = "approved";
    public static final String BLOCKED = "blocked";
    public static final String SELECT = "select";
    public static final String CREATE = "create";
    public static final String LOAD_SELLERS = "loadsellers";
    public static final String SELLER_INFO = "sellerinfo";
    /*-------------------BUTTON--------------------------*/
    public static final String BTN_ACTION = "btnAction";
    /*-------------------PARAM--------------------------*/
    public static final String PARAM_USER_ID = "txtUserId";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_USERNAME = "txtUsername";
    public static final String PARAM_PASSWORD = "txtPassword";
    public static final String PARAM_ROLE = "role";
    public static final String PARAM_PROJECT_ID = "txtProjectId";
    public static final String PARAM_PRODUCT_ID = "txtProductId";
    public static final String PARAM_IDEABOOK_ID = "txtIdeabookId";
    public static final String PARAM_PHOTO_ID = "txtPhotoId";
    public static final String PARAM_PROJECT_NAME = "txtProjectName";
    public static final String PARAM_ADDRESS = "txtAddress";
    public static final String PARAM_COST = "txtCost";
    public static final String PARAM_WEBSITE = "txtWebsite";
    public static final String PARAM_YEAR = "txtYear";
    public static final String PARAM_KEY = "txtKey";
    /*-------------------ATTRIBUTE--------------------------*/
    public static final String ATT_ADMIN = "ADMIN";
    public static final String ATT_ACCOUNT_LIST = "ACCOUNTLIST";
    public static final String ATT_DETAIL = "DETAIL";
    public static final String ATT_PROJECT_LIST = "PROJECTLIST";
    public static final String ATT_LIST = "LIST";
    public static final String ATT_SELECT = "SELECT";
    public static final String ATT_ERROR = "ERROR";
    public static final String ATT_ERROR_CREATE_PROJECT = "ERRORCREATEPROJECT";
    public static final String ATT_STATUS = "STATUS";
    public static final String ATT_USER = "user";
    public static final String ATT_MY_PROJECTS = "MYPROJECT";
    public static final String ATT_SELLER = "SELLER";
    /*-------------------STATUS CODE--------------------------*/
    public static final int STATUS_DELETED = 2;
    public static final int STATUS_OK = 1;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_NOT_OK = -1;
    public static final int STATUS_NO_IMAGE = -2;
    public static final int ROLE_MEMBER = 1;
    public static final int ROLE_PROFESSIONAL = 2;
    public static final int ROLE_SELLER = 3;
}
