<%@page import="hd.controller.ProjectViewModel"%>
<%@page import="hd.entity.Project"%>
<%@page import="hd.dto.ProjectDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<%@page language="java"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>House Decor: Projects </title>
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="assets/css/style.css" rel="stylesheet" />    
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

        <link href="assets/css/datepicker.css" rel="stylesheet" />
        <script src="assets/plugins/jquery-1.10.2.js"></script> 
        <script src="assets/plugins/bootstrap.js"></script>
        <!-- EASING SCROLL SCRIPTS PLUGIN  -->
        <script src="assets/plugins/jquery.easing.min.js"></script>
        <!-- CUSTOM SCRIPTS   -->
        <script src="assets/js/custom.js"></script>
        <!--        <script src='https://code.jquery.com/jquery-1.10.0.min.js'></script>-->
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootstrap-datepicker.js"></script>
        <script src='assets/js/jquery-2.1.1.min.js'></script>
        <script src='assets/js/jquery.validate.js'></script>
        <script src='assets/js/signup-form.js'></script>
    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top"style="background: #f4f4f4 !important;">
        <!-- HEADER -->        
        <%@include file="header.jsp" %>
        <!-- END HEADER -->
        <!--PORTFOLIO SECTION-->
        <c:set var="dto" value="${userEntity}"/>
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
                                    <a style="font-size: 25px;line-height: 1.1;text-decoration: none" href="#"><c:out value="${dto.firstname}"/> <c:out value="${dto.lastname}"/></a>
                                </h1>   
                            </div>
                        </div>
                        <div class="change-cover-photo">
                            <a class="hzBtn" id="changeCoverPhoto" style="text-decoration: none" href="#">
                                <i class="fa fa-camera fa"></i>
                                Change Cover Photo
                            </a>
                        </div>
                        <div class="profile-pro-actions">
                            <a class="btn-white" style="text-decoration: none;" href="#">
                                <i class="fa fa-pencil fa" style="margin-right: 2px;"></i>Edit Profile
                            </a>
                        </div>
                        <div class="side-bar">

                            <ul class="list-inline" style="margin-top: 11px;">
                                <li class=""><div class="profile-pic-placeholder"></div></li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="https://www.houzz.com/pro/cuk3t/manh-hung" >
                                        <span class="option-text">Your Houzz</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label" href="#" >
                                        <span class="option-text" style="color: #3d8901">Project</span>
                                    </a>
                                </li>

                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="https://www.houzz.com/pro/cuk3t/manh-hung" >
                                        <span class="option-text">Ideabooks</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="https://www.houzz.com/pro/cuk3t/manh-hung" >
                                        <span class="option-text">Bookmarks</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="https://www.houzz.com/pro/cuk3t/manh-hung" >
                                        <span class="option-text">Purchases</span>
                                    </a>
                                </li>
                            </ul>

                        </div>
                </div>

            </div>
        </div>
                                <div class="container">
            <div class="container" style="background: #ffffff">
                <div class="hz-one-card-sideBar">
                    <div class="hz-sidebar">
                        <div class="sidebar_filter">
                            <div class="sidebar-header" compid="filterHeader">Account</div>
                            <div class="sidebar-body">
                                <ul>
                                    <li class="sidebar-item2">
                                        <a class="sidebar-item-label2" href="https://www.houzz.com/editProfile">
                                            <span>Profile Info</span>
                                        </a>
                                    </li>
                                    <li class="sidebar-item2">
                                        <a class="sidebar-item-label2" href="ProfessionalServlet">
                                            <span>Professional Info</span>
                                        </a>
                                    </li>
                                    <li class="sidebar-item2">
                                        <a class="sidebar-item-label2" href="SellerServlet">
                                            <span>Seller Info</span>
                                        </a>
                                    </li>
                                    <li class="sidebar-item2">
                                        <a class="sidebar-item-label2" href="https://www.houzz.com/editProfile">
                                            <span>Password</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="hz-one-card-right-content" style="width: 80%">
                    <div class="hz-main-contents">
                        <a class="btn-white" style="text-decoration: none; margin-top: 20px; margin-left: 34px" href="ProjectServlet?action=add">
                            <i class="fa fa-plus fa" style="margin-right: 2px;"></i>Add Project
                        </a>
                        <div>
                            <div class="row text-center pad-top">
                                <%
                                    List<ProjectViewModel> projects = (List<ProjectViewModel>) request.getAttribute("projects");
                                %>

                                <%if (projects != null) {for (ProjectViewModel project : projects) {%>
                                <div class="col-md-4 col-sm-4 col-xs-12 project-card">
                                    <a href="ProjectServlet?action=edit&id=<%=project.getProjectId()%>" data-toggle="modal">
                                        <%if(project.getUrl() != null){%>
                                            <img src="<%=project.getUrl()%>" class="img-responsive " alt="">
                                        <%}else{%>
                                            <img src="assets/img/noImage.png" class="img-responsive " alt="">
                                        <%}%>
                                    </a>
                                    <label><%=project.getProjectName()%></label>
                                </div>
                                <%}}%>

                            </div>  
                        </div>
                    </div>
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
