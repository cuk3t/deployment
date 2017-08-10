<%@page import="hd.entity.Category"%>
<%@page import="java.util.List"%>
<%@page import="hd.entity.Style"%>
<%@page import="hd.entity.Project"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>House Decor: Add Idea Book Photo</title>
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
                                    <a class="sidebar-item-label" href="ProjectServlet" >
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
                                        <a class="sidebar-item-label2" href="https://www.houzz.com/editProfile">
                                            <span>Professional Info</span>
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
                <div class="hz-one-card-right-content" style="width: 80%;  ">
                    <div class="hz-main-contents">
                        <div id="mainArea" style="margin-top: 100px;">
                            <div class="wellcome_container">                
                                <div class="wellcome_header_container">
                                    <div class="wellcome_tour_hearder">Idea Book Photo </div>
                                </div>
                                <%
                                    int projectId = Integer.parseInt(request.getAttribute("projectId").toString());
                                    List<Style> styles = (List<Style>) request.getAttribute("styles");
                                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                                %>
                                <div class="Wellcome_body_container">
                                    <form id="ideaBookPhotoForm" method="post" action="IdeaBookPhotoServlet?action=add" class="form-horizontal my-form" enctype="multipart/form-data">
                                        <input type="hidden" id="projectId" name="projectId" value="<%=projectId%>">
                                            <div class="rowReg" style="margin: auto">
                                                <label  class="lableInput" style="text-align: center">
                                                    Title
                                                </label>
                                                <input required type="text" class="inputReg" id="title" name="title" placeholder="Title" value="">
                                            </div>
                                            <div class="rowReg" style="margin: auto">
                                                <label  class="lableInput" style="text-align: center">
                                                    Description
                                                </label>
                                                <div></div>
                                                <textarea id="description" name="description" placeholder="Description" cols="48" rows="6" value=""></textarea>
                                            </div> 
                                            <div class="rowReg" style="margin: auto">
                                                <label  class="lableInput">
                                                    Category
                                                </label>
                                                <br>
                                                    <select id="categoryId" name="categoryId" style="width: 100%">
                                                        <%for (Category category : categories) {%>
                                                        <option value="<%=category.getCategoryId()%>"><%=category.getCategoryName()%></option>
                                                        <%}%>
                                                    </select>
                                            </div>
                                            <div class="rowReg" style="margin: auto">
                                                <label  class="lableInput" style="text-align: center">
                                                    Style
                                                </label>
                                                <br>
                                                    <select id="styleId" name="styleId" style="width: 100%">
                                                        <%for (Style styleId : styles) {%>
                                                        <option value="<%=styleId.getStyleId()%>"><%=styleId.getName()%></option>
                                                        <%}%>
                                                    </select>
                                            </div>
                                            <div class="rowReg" style="margin: auto">
                                                <label  class="lableInput" style="text-align: center">
                                                    Image
                                                </label>
                                                <input required type="file" id="image" name="image" accept="image/*">
                                            </div>
                                            <div class="rowReg" style="margin: auto; margin-top: 20px;">
                                                <input required type="submit" class="inputReg" value="Add">
                                            </div>  
                                            <div class="rowReg" style="margin: auto; margin-top: 10px;">
                                                <input required type="submit" class="inputReg" value="Cancel" onclick="location.href = 'ProjectServlet?action=edit&id=${projectId}'">
                                            </div>
                                    </form>
                                </div>
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
