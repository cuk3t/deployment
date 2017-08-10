<%-- 
    Document   : viewProfile
    Created on : Jun 6, 2017, 9:09:20 AM
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
        <title>House Decor: Profile </title>
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
       <c:set var="entity" value="${entity}"/>
        <!-- END HEADER -->
        <!--PORTFOLIO SECTION-->
        <div class="container" style="padding-top: 90px">
            <div class="profile-header">
                <div class="profile-cover">
                    <div class="profile-pic-container">
                        <a class="profile-pic-border" href="https://www.houzz.com/pro/cuk3t/manh-hung">
                            <img class="profile-pic" width="148" height="165" id="mainUserProfilePic" 
                                 src="https://st.hzcdn.com/res/2551/pic/user_3.png?v=2551"
                                 oncontextmenu="return false;" onmousedown="preventImageDrag(event);" 
                                 ondragstart="return false;" onselectstart="return false;">
                        </a>
                    </div>
                    <img id="coverImage" class="cover-image " src="assets/img/home-design.jpg" 
                         oncontextmenu="return false;" onmousedown="preventImageDrag(event);" 
                         ondragstart="return false;" onselectstart="return false;">
                        <div class="profile-info">
                            <div class="profile-title">
                                <h1>
                                    <a style="font-size: 25px;line-height: 1.1;text-decoration: none" href="#"><c:out value="${Infodto.professionalName}"/></a>
                                </h1>   
                            </div>
                        </div>
                        
                        <div class="side-bar">

                            <ul class="list-inline" style="margin-top: 11px;">
                                <li class=""><div class="profile-pic-placeholder"></div></li>
                                
                                <li class="sidebar-item" style="margin-left: 20px; margin-right: 30px">			
                                    <a class="sidebar-item-label-2" href="#" >
                                        <span class="option-text">Tổng Quan</span>
                                    </a>
                                </li>
                                
                                <li class="sidebar-item" style="margin-left: 20px; margin-right: 30px">			
                                    <a class="sidebar-item-label" href="#" >
                                        <span class="option-text" style="color: #3d8901">Dự Án</span>
                                    </a>
                                </li>
                                
                                <li class="sidebar-item" style="margin-left: 20px; margin-right: 30px">			
                                    <a class="sidebar-item-label-2" href="#" >
                                        <span class="option-text">Ý Tưởng</span>
                                    </a>
                                </li>
                                
                               
                            </ul>

                        </div>
                </div>

            </div>
        </div>
                    <!--                                body-->
                    
        <div class="container">
            <div class="container" style="background: #ffffff">
            <div class="hz-one-card-sideBar">
                <div class="hz-sidebar">
                    <div class="sidebar_filter">
                        <div class="sidebar-header" compid="filterHeader"> Project</div>
                        <div class="sidebar-body">
                            <ul style="margin-left: -10px;">
                                <c:forEach var="listDTO" items="${listDTO}">
                                <li class="sidebar-item2">
                                    <a class="sidebar-item-label2" href="ViewProjectDetailServlet?txtProjectID=${listDTO.projectId}&txtUserID=${entity.userId.userId}">
                                        <span>${listDTO.projectName}</span>
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
<!--                                        <-------------- Body ben trai-->

            <div class="hz-one-card-right-content2">
                
                <div class="hz-main-contents">
                    <div style="width: 600px; margin-top: 10px">
                       <h3 class="header-1 top">${entity.projectName}</h3>
                       <div id="projectDescTrimmed" style="max-height: none;">
				Thiếu trường miêu tả thông tin Project					
                       </div>
                       <div class="project-country" style="margin-top: 30px;border-bottom: 1px solid #e6e6e6;padding-bottom: 10px">Địa chỉ: ${entity.address}</div>
                       <c:forEach var="pic" items="${entity.ideaBookPhotoCollection}">
                       <div class="ic_whiteCardxl_portrait" >
                           <div class="photo_trackMe_white">
                               <div class="" compid="img">
                                   <a lb-view="" lb-mag="" class="" href="">
                                       <div>
                                           <img style="height: 370px; width: 370px" src="${pic.url}">
                                       </div>
                                   </a>
                               </div>
                               
                           </div>
                           <div class="photoMeta">
                               <div class="photoInfo" style="height: 412px;">
                                   <a class="photo-title trackMe" style="font-size: 22px;white-space: normal;" href="" compid="title">${pic.tilte}</a>			
                                   
                                   <div class="photo-description" style="margin-top: 10px;">
                                       ${pic.description}
                                   </div>                                      
                               </div>				
                           </div>
                       </div>
                       </c:forEach>
                    </div>
                </div>
            </div>
            </div>
        </div>
                    
    </body>
</html>
