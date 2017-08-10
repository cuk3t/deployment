<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>House Decor: Profile </title>
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="assets/css/style.css" rel="stylesheet" />    
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
      
        <link href="assets/css/datepicker.css" rel="stylesheet" />
        <script src="assets/plugins/jquery-1.10.2.js"></script> 
<!--        <script src="assets/plugins/bootstrap.js"></script>-->
        <!-- EASING SCROLL SCRIPTS PLUGIN  -->
<!--        <script src="assets/plugins/jquery.easing.min.js"></script>-->
        <!-- CUSTOM SCRIPTS   -->
        <script src="assets/js/custom.js"></script>
<!--        <script src='https://code.jquery.com/jquery-1.10.0.min.js'></script>-->
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootstrap-datepicker.js"></script>
<!--        <script src='assets/js/jquery-2.1.1.js'></script>-->
        <script src='assets/js/jquery.validate.js'></script>
        <script src='assets/js/signup-form.js'></script>
    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top"style="background: #f4f4f4 !important;">
        <!-- HEADER -->        
        <%@include file="header.jsp" %>
        <!-- END HEADER -->
        <!--PORTFOLIO SECTION-->
        
        <div class="container" style="padding-top: 90px">
            <div class="profile-header">
                <div class="profile-cover">
                    <div class="profile-pic-container">
                        <div class="profile-pic-border" >
                            <img class="profile-pic" width="148" height="165" id="mainUserProfilePic" 
                                 src="https://st.hzcdn.com/res/2551/pic/user_3.png?v=2551"
                                 oncontextmenu="return false;" onmousedown="preventImageDrag(event);" 
                                 ondragstart="return false;" onselectstart="return false;">
                        </div>
                    </div>
                    <img id="coverImage" class="cover-image " src="assets/img/home-design.jpg" 
                         oncontextmenu="return false;" onmousedown="preventImageDrag(event);" 
                         ondragstart="return false;" onselectstart="return false;">
                        <div class="profile-info">
                            <div class="profile-title">
                                <h1>
                                    <a style="font-size: 25px;line-height: 1.1;text-decoration: none" href="#"><c:out value="${userDTO.firstname}"/> <c:out value="${userDTO.lastname}"/></a>
                                </h1>   
                            </div>
                        </div>
                        <div class="change-cover-photo">
                            <a class="hzBtn" id="changeCoverPhoto" style="text-decoration: none" href="#">
                                <i class="fa fa-camera fa"></i>
                                Change Cover Photo
                            </a>
                        </div>
                            <div class="side-bar">

                                    <ul class="list-inline" style="margin-top: 11px;">
                                        <li class=""><div class="profile-pic-placeholder"></div></li>
                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="ProfileServlet">
                                                <span class="option-text">Hồ Sơ</span>
                                            </a>
                                        </li>

                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="MainProfessionalServlet?btnAction=load&amp;txtUserId=1">
                                                <span class="option-text">Dự Án</span>
                                            </a>
                                        </li>
                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="IdeaBooksServlet?txtUserID=${user.userId}">
                                                <span class="option-text">Ý Tưởng</span>
                                            </a>
                                        </li>
                                       
                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label" href="#">
                                                <span class="option-text" style="color: #3d8901">Mua Hàng</span>
                                            </a>
                                        </li>
                                        <c:if test="${user.roleId.roleId == 3}">
                                            <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="MyProductServlet?action=show&txtUserID=${user.userId}" >
                                                <span class="option-text">Cửa Hàng</span>
                                            </a>
                                            </li>
                                            <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="" >
                                                <span class="option-text">Đơn Hàng</span>
                                            </a>
                                            </li>
                                        </c:if>
                                    </ul>

                                </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="container" style="background: #ffffff; padding-left: 20px">
            <div id="coPage">
                <h1 class="header-1 top">Đơn Hàng Đã Mua</h1>
                <c:forEach var="dto" items="${listDTO}">
                    <div id="coMain" style="padding-bottom: 10px;">
                    <div class="coBox shoppingCart">
                        <div class="coBoxHead">
                            <div class="itemImage"><b>${dto.createdTime}</b></div>
                            <div class="itemDetail">&nbsp;</div>
                            <div class="itemPrice"><b>Số Lượng</b></div>
                            <div class="itemQuantity"><b>Tổng tiền</b></div>
                        </div>
                        <c:forEach var="orderDetail" items="${dto.orderDetailCollection}">
                            <div class="coBoxBody" id="shopping-cart-items">

                                    <div id="cart-id-1" class="cart-item" data-value="1">
                                        <div class="coProductGroup">
                                            <div class="item first" id="cItm_27518004">
                                                <div class="itemImage">
                                                    <a href="" compid="img" objid="61906617">
                                                        <img src="${orderDetail.productId.productPhotoCollection[0].url}" width="100" height="100">
                                                    </a>
                                                </div>
                                                <div class="itemDetail">
                                                    <div class="title">
                                                        <a href="" class="header-5 no-margin colorLink" style="color: #3d8901;font-size: 16px; font-weight: bold" id="product-name-1" objid="61906617"> ${orderDetail.productId.productName}</a>
                                                    </div>
                                                   
                                                </div>
                                                <div class="itemQuantity">
                                                    <div class="quantity">
                                                        <div class="inline-dropdown-container">                  
                                                            <span id="cart-quantity-1">${orderDetail.quantity}</span>   
                                                        </div>

                                                    </div>
                                                </div>
                                                <div class="itemPrice">
                                                    <p class="header-5" style="font-size: 16px;font-weight: bold;">
                                                        <span id="item-price">${orderDetail.price}</span> vnd
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                </c:forEach>
            </div>
                
            </div>
        </div>
        <!--END PORTFOLIO SECTION-->
        <!--FOOTER-->
        <%@include file="footer.jsp" %>
        <!--END FOOTER-->
        <!-- JAVASCRIPT FILES PLACED AT THE BOTTOM TO REDUCE THE LOADING TIME  -->
        <!-- CORE JQUERY  -->
       
    </body>
</html>
