<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <!--[if IE]>
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
            <![endif]-->
        <title>Free Responsive Template Using Bootstrap : Revas  </title>
        <!-- BOOTSTRAP CORE STYLE CSS -->
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="assets/css/style.css" rel="stylesheet" />    
        <!-- GOOGLE FONT -->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top"style="background: #f4f4f4 !important;">
        <!-- HEADER -->
        <%@include file="header.jsp" %>
        <!-- END HEADER -->
        <!--PORTFOLIO SECTION-->
        <section id="portfolio"  >
            <div class="container">
                <div class="row text-center" >
                    <div class="col-md-12">
                        <h1 style="margin-top: 50px">User info </h1>
                    </div>
                    
                    <form name="banUser" action="MainServlet" method="POST">
                        <div class="form-group ">
                            UserId <input type="text" name="userId" value="1">
                        </div>
                        
                        
                        <div class="form-group ">
                            <input type="submit" name="btAction" id="banUser" value="banUser">
                        </div>
                        
                        <div class="form-group ">
                            <input type="submit" name="btAction" id="activeUser" value="activeUser">
                        </div>
                    </form>
                    
                    <form name="Product" action="MainServlet" method="POST">
                        <div class="form-group ">
                            ProductId <input type="text" name="productId" value="1">
                        </div>
                        <div class="form-group ">
                            Quantity <input type="text" name="quantity" value="1">
                        </div>
                        
                        
                        <div class="form-group ">
                            <input type="submit" name="btAction" id="buyProduct" value="buyProduct">
                        </div>
                        
                        <div class="form-group ">
                            <input type="submit" name="btAction" id="changeProductQuantity" value="changeProductQuantity">
                        </div>
                    </form>
                    
                    <form name="Tracking" action="MainServlet" method="POST">
                        <div class="form-group ">
                            UserId <input type="text" name="userId" value="1">
                        </div>
                        
                        <c:set var="catList" value="${requestScope.CATEGORY_LIST}"/>
                        
                        <select name="categoryId">
                            <c:forEach var="category" items="${catList}">
                                <option value="${category.categoryID}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                        
                        <div class="form-group ">
                            <input type="submit" name="btAction" id="changeProductQuantity" value="enterCategory">
                        </div>
                        
                        <div class="form-group ">
                            <input type="submit" name="btAction" id="changeProductQuantity" value="removeOutdateTracking">
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <!--END PORTFOLIO SECTION-->
        <!--FOOTER-->
        <%@include file="footer.jsp" %>
        <!--END FOOTER-->
        <!-- JAVASCRIPT FILES PLACED AT THE BOTTOM TO REDUCE THE LOADING TIME  -->
        <!-- CORE JQUERY  -->
        <script src="assets/plugins/jquery-1.10.2.js"></script>
        <!-- BOOTSTRAP SCRIPTS  -->
        <script src="assets/plugins/bootstrap.js"></script>
        <!-- EASING SCROLL SCRIPTS PLUGIN  -->
        <script src="assets/plugins/jquery.easing.min.js"></script>
        <!-- CUSTOM SCRIPTS   -->
        <script src="assets/js/custom.js"></script>
    </body>
</html>
