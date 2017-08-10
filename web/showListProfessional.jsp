<%-- 
    Document   : professionals
    Created on : May 30, 2017, 1:56:41 PM
    Author     : cuk3t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <!--[if IE]>
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
            <![endif]-->
        <title>Professional </title>
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="assets/css/style.css" rel="stylesheet" />        
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
        <link href="assets/css/datepicker.css" rel="stylesheet" />
    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top" style="background: #f4f4f4 !important;">
        <%@include file="header.jsp" %>
        <!--END NAVBAR SECTION-->

        <div class="" style="margin-top: 100px;">
            <!--            LeftSide Bar-->
            <nav class="leftSideBar">
                <div class="sidebar">
                    <div class="sidebar-header" style="PADDING-LEFT: 13PX;">Home Design & Remodeling<span class="toggle down-icon"></span>
                    </div>
                    <div class="sidebar-body">		
                        <ul id="roomFilter">
                            <li class="sidebar-item" compid="opt" posid="">			
                                <a class="sidebar-item-label_12" href="showProfessionalServlet?btAction=All">
                                    <span class="option-text">All Professional</span>
                                </a>
                            </li>

                            <li class="sidebar-item" compid="opt" posid="">			
                                <a class="sidebar-item-label_12" href="showProfessionalServlet?btAction=1">
                                    <span class="option-text">Architects Designers</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="">			
                                <a class="sidebar-item-label_12" href="showProfessionalServlet?btAction=2">
                                    <span class="option-text">Home Builders</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="">			
                                <a class="sidebar-item-label_12" href="showProfessionalServlet?btAction=3">
                                    <span class="option-text">Interior Designers</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="">			
                                <a class="sidebar-item-label_12" href="showProfessionalServlet?btAction=4">
                                    <span class="option-text">Landscape Designers</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="">			
                                <a class="sidebar-item-label_12" href="showProfessionalServlet?btAction=5">
                                    <span class="option-text">Other Services</span>
                                </a>
                            </li>						

                        </ul>
                    </div>


                </div>
            </nav>
            <!--        right side content-->
            <div class="rightSideContent">
                <div class="browsePhotos">
                    <div class="headerContainer" style="margin-bottom: 10px; border-bottom: 1px solid #e6e6e6;">                       
                        <div style="padding: 0px 10px 0px 10px">
                            <h3>345,201 Architects and Building Designers</h3>                      
                        </div>
                        <c:forEach var="dto" items="${listDTO}">
                        <div class="whiteCard_pro-card " compid="uniprof" objid="james_crisp" posid="0">
                            <div class="pro-cover-photos">
                            
                       
                                <a href="<c:choose>
                                       <c:when test="${user.userId != dto.userId}">ViewOverServlet?txtID=${dto.userId}</c:when>
                                       <c:otherwise>ProfileServlet</c:otherwise>
                                   </c:choose>" id="_h_url_paid_pro1" onmousedown="">
                                    <img data-pin-no-hover="true" src="${dto.url}" class="proImage reloadable" width="310" height="180" sids="193129,186926,182837,183875,193126">
                                </a>
                            </div>
                            <div class="vcard" itemscope="" itemtype="http://schema.org/LocalBusiness">
                                <div class="pro-header clearfix">
                                    
                                    <div class="proThumb" itemprop="image">
                                        <img class=" hz-user-image" src="https://st.hzcdn.com/res/2551/pic/user_3.png?v=2551" width="40" height="40">
                                    </div>
                                    <div class="name-info">
                                        <a href="<c:choose>
                                       <c:when test="${user.userId != dto.userId}">ViewOverServlet?txtID=${dto.userId}</c:when>
                                       <c:otherwise>ProfileServlet</c:otherwise>
                                   </c:choose>" id="_h_url_paid_pro1" onmousedown="" class="pro-title" itemprop="name"><c:out value="${dto.professionalName}"/></a>
                                        
                                    </div>
                                </div>
                                <div class="pro-info" >
                                    <div class="pro-description text-muted"><c:out value="${dto.aboutMe}"/></div>
                                    
                                </div>
                                    <div style=" margin-top: 20px;padding-top: 10px">
                                    <ul class="pro-meta-extra list-unstyled text-bold text-muted" style="    margin-top: 40px; font-weight: bold; color: dimgray">
                                        <li class="pro-list-item pro-phone">
                                            <i class="pro-list-item--icon hzi-font hzi-Phone-fill"></i><span class="pro-list-item--text"><c:out value="${dto.phoneNumber}"/></span>
                                        </li>
                                        <li class="pro-list-item pro-location">
                                            <i class="pro-list-item--icon hzi-font hzi-location-pin-fill"></i><span class="pro-list-item--text"><span itemprop="addressLocality">address: <c:out value="${dto.address}"/></span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>



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
