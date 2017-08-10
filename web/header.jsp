<%-- 
    Document   : header
    Created on : May 16, 2017, 4:06:41 PM
    Author     : Lòn nào vậy 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
        <!--NAVBAR SECTION-->
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">YOUR LOGO</a>
                </div>
                <div class="navbar-collapse collapse">
                    <div class="navbar-brand">
                        <form name="Search" action="Search">
                            <div>
                                <input type="text" name="txtSearch" value="" class="input-search"/>
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="MainServlet">HOME</a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="fa fa-bell-o"></i> 
                            </a>
                        </li>
                        <li>
                            <a href="ShoppingCartServlet?method=show">
                                <i class="fa fa-shopping-cart"></i>
                                <c:set var="num" value="0"/>
                                <c:forEach var="item" items="${cart}">
                                    <c:set var="num" value="${num + item.quantity}"/>

                                </c:forEach>
                                <span style="color: #4cae4c"><c:out value="${num}" /></span>
                                 
                            </a>
                        </li>

                        <li style="margin-left: 20px;margin-top: 7px;">
                            <!--<c:out value="${user.email}"/>-->


                            <div class="dropdown">
                                <c:if test="${user.userId == null}">
                                    <a href="MainServlet?btAction=checkLogin" class="dropbtn">
                                        <i class="fa fa-user"></i> 
                                    </a>
                                </c:if>

                                <c:if test="${user.userId != null}">
                                    <a href="#" class="dropbtn">
                                        <i class="fa fa-user"></i> 
                                    </a>
                                    <div class="dropdown-content">

                                        <a href="#">
                                            <c:out value="${user.email}"/>
                                        </a>
                                        <a href="ProfileServlet">Hồ Sơ</a>
                                        <c:if test="${sessionScope.user.roleId.roleId==2}">
                                            <a href="MainProfessionalServlet?btnAction=load&txtUserId=${sessionScope.user.userId}">Dự án</a>
                                        </c:if>
                                        <a href="IdeaBooksServlet?txtUserID=${user.userId}">Ý Tưởng</a>
                                        <a href="#">Đơn Hàng</a>
					<c:if test="${sessionScope.user.roleId.roleId==2}">
                                            <a href="#">Đăng ký bán hàng</a>
                                        </c:if>
                                        <c:if test="${sessionScope.user.roleId.roleId==3}">
                                            <a href="MyProductServlet?action=show&txtUserID=${user.userId}">Cửa hàng</a>
                                        </c:if>
                                        <a href="MyIdeaBooksServlet">Ý Tưởng</a>
                                        <a href="DemoServlet">Demo</a>
                                        <a href="LogoutServlet">Đăng Xuất</a>


                                    </div>
                                </c:if>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="houzz-header-secondary" id="menu">
                <ul class="header-nav-rooms">
                    <li class="header-nav-room">
                        <a class="menu-title" href="ShowListPhotoServlet">
                            <span class="fa fa-picture-o fa">Photos</span>
                        </a>
                    </li>
                    <li class="header-nav-room">
                        <a class="menu-title" href="ShowListProductServlet?txtCategoryName=all">
                            <span class="fa fa-archive fa">Shop By Department</span>
                        </a>
                    </li>
                    <li class="header-nav-room">
                        <a class="menu-title" href="MainServlet?btAction=professional">
                            <span class="fa fa-user fa">Find Professional</span>
                        </a>
                    </li>
                    <li class="header-nav-room">
                        <a class="menu-title" href="" style="color: #4cae4c">
                            <span class="fa fa-gift fa">Sale</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!--END NAVBAR SECTION-->

    </body>
</html>
