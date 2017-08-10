<%-- 
    Document   : project_detail
    Created on : Jul 12, 2017, 2:43:53 PM
    Author     : An Lee
--%>

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
        <c:set var="error" value="${sessionScope.ERRORADDPROJECTIMAGE}"/>
        <c:set var="user" value="${sessionScope.USERVIEW}"/>
        <c:set var="project" value="${sessionScope.PROJECT}"/>
        <div class="container" style="padding-top: 90px">
            <div class="profile-header">
                <div class="profile-cover">
                    <div class="profile-pic-container">
                        <div class="profile-pic-border" >
                            <img class="profile-pic" width="148" height="165" id="mainUserProfilePic" 
                                 src="https://st.hzcdn.com/res/2551/pic/user_3.png?v=2551"
                                 oncontextmenu="return false;" onmousedown="preventImageDrag(event);" 
                                 ondragstart="return false;" onselectstart="return false;"/>
                        </div>
                    </div>
                    <img id="coverImage" class="cover-image " src="assets/img/home-design.jpg" 
                         oncontextmenu="return false;" onmousedown="preventImageDrag(event);" 
                         ondragstart="return false;" onselectstart="return false;">
                        <div class="profile-info">
                            <div class="profile-title">
                                <h1>
                                    <a style="font-size: 25px;line-height: 1.1;text-decoration: none" href="#"><c:out value="${user.firstname}"/> <c:out value="${user.lastname}"/></a>
                                </h1>   
                            </div>
                        </div>
                        <div class="change-cover-photo">
                            <a class="hzBtn" id="changeCoverPhoto" style="text-decoration: none" href="#">
                                <i class="fa fa-camera fa"></i>
                                Change Cover Photo
                            </a>
                        </div>
                        <!--                        <div class="profile-pro-actions">
                                                    <a class="btn-white" style="text-decoration: none;" href="#">
                                                        <i class="fa fa-pencil fa" style="margin-right: 2px;"></i>Edit Profile
                                                    </a>
                                                </div>-->
                        <div class="side-bar">

                            <ul class="list-inline" style="margin-top: 11px;">
                                <li class=""><div class="profile-pic-placeholder"></div></li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="ProfileServlet" >
                                        <span class="option-text">Hồ Sơ</span>
                                    </a>
                                </li>

                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label" href="MainProfessionalServlet?btnAction=load&txtUserId=${user.userId}">
                                        <span class="option-text" style="color: #3d8901">Dự Án</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="MyIdeaBooksServlet" >
                                        <span class="option-text" >Ý Tưởng</span>
                                    </a>
                                </li>

                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="https://www.houzz.com/pro/cuk3t/manh-hung" >
                                        <span class="option-text">Đánh Dấu</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="https://www.houzz.com/pro/cuk3t/manh-hung" >
                                        <span class="option-text">Mua Hàng</span>
                                    </a>
                                </li>

                                <c:if test="${user.roleId.roleId==2}">
                                    <li class="sidebar-item">			
                                        <a class="sidebar-item-label-2" href="#" >
                                            <span class="option-text">Đăng ký bán hàng</span>
                                        </a>
                                    </li>
                                </c:if>

                                <c:if test="${user.roleId.roleId==3}">
                                    <li class="sidebar-item">			
                                        <a class="sidebar-item-label-2" href="#" >
                                            <span class="option-text">Cửa hàng</span>
                                        </a>
                                    </li>
                                </c:if>
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
                            <div class="sidebar-header" compid="filterHeader">Danh Sách Dự Án</div>
                            <div class="sidebar-body">
                                <ul>
                                    <c:forEach var="dto" items="${sessionScope.MYPROJECT}">
                                        <li class="sidebar-item2">
                                            <a class="sidebar-item-label2" href="MainProfessionalServlet?btnAction=viewdetail&txtProjectId=${dto.project.projectId}">
                                                <span>${dto.project.projectName}</span>
                                            </a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div style="padding-left: 200px; max-width: 740px; padding-top: 24px">
                    <form id="Edit" action="MainProfessionalServlet?btnAction=update" method="POST">
                        <input type="hidden" name="txtProjectId" value="${project.projectId}" />
                        <div style="height: 40px">
                            <div class="col-lg-2">Dự án*:</div>
                            <div class="col-lg-8">
                                <c:if test="${user.userId==sessionScope.user.userId}">
                                    <input class="form-control" required="" type="text" id="newGalleryName" placeholder="Tên dự án" name="txtProjectName" maxlength="71" value="${project.projectName}"/>
                                </c:if>
                                <c:if test="${user.userId!=sessionScope.user.userId}">
                                    <input readonly="true" class="form-control" required="" type="text" id="newGalleryName" placeholder="Tên dự án" name="txtProjectName" maxlength="71" value="${project.projectName}"/>
                                </c:if>
                            </div>
                        </div>
                        <div style="height: 40px">
                            <div class="col-md-2">Địa chỉ:</div> 
                            <div class="col-md-8">
                                <c:if test="${user.userId==sessionScope.user.userId}">
                                    <input class="form-control" type="text" id="newGalleryName" placeholder="Địa chỉ" name="txtAddress" maxlength="71" value="${project.address}"/>
                                </c:if>
                                <c:if test="${user.userId!=sessionScope.user.userId}">
                                    <input readonly="true" class="form-control" type="text" id="newGalleryName" placeholder="Địa chỉ" name="txtAddress" maxlength="71" value="${project.address}"/>
                                </c:if>
                            </div>
                        </div>
                        <div style="height: 40px">
                            <div class="col-md-2">Giá:</div> 
                            <div class="col-md-8">
                                <c:if test="${user.userId==sessionScope.user.userId}">
                                    <input class="form-control" type="number" id="newGalleryName" placeholder="Giá" name="txtCost" min="0" value="${project.cost}"/>
                                </c:if>
                                <c:if test="${user.userId!=sessionScope.user.userId}">
                                    <input readonly="true" class="form-control" type="number" id="newGalleryName" placeholder="Giá" name="txtCost" min="0" value="${project.cost}"/>
                                </c:if>
                            </div>
                        </div >
                        <div style="height: 40px">
                            <div class="col-md-2">Website:</div> 
                            <div class="col-md-8">
                                <c:if test="${user.userId==sessionScope.user.userId}">
                                    <input class="form-control" type="text" id="newGalleryName" placeholder="Website" name="txtWebsite" maxlength="71" value="${project.website}"/>
                                </c:if>
                                <c:if test="${user.userId!=sessionScope.user.userId}">
                                    <input readonly="true" class="form-control" type="text" id="newGalleryName" placeholder="Website" name="txtWebsite" maxlength="71" value="${project.website}"/>
                                </c:if>
                            </div>
                        </div>
                        <div style="height: 40px">
                            <div class="col-md-2">Năm:</div> 
                            <div class="col-md-8">
                                <c:if test="${user.userId==sessionScope.user.userId}">
                                    <input class="form-control" type="number" id="newGalleryName" placeholder="Năm" name="txtYear" min="2000" max="2017" maxlength="4" value="${project.year}"/>
                                </c:if>
                                <c:if test="${user.userId!=sessionScope.user.userId}">
                                    <input readonly="true" class="form-control" type="number" id="newGalleryName" placeholder="Năm" name="txtYear" min="2000" max="2017" maxlength="4" value="${project.year}"/>
                                </c:if>
                            </div>
                        </div>
                    </form>
                    <c:if test="${user.userId==sessionScope.user.userId}">
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary" form="Edit">Lưu</button>
                            <button class="btn btn-default" type="reset" form="Edit">Hủy</button>
                        </div> 
                    </c:if>
                </div>

                <div class="hz-main-contents">
                    <div id="userGalleriesBody" scopeid="userGalleriesBody" class="comp-box">
                        <div class="header-1 top">
                            Dự Án: ${project.projectName}
                            <c:if test="${user.userId==sessionScope.user.userId}">
                                <form action="MainProfessionalServlet?btnAction=deleteProject&txtProjectId=${project.projectId}" method="POST">
                                    <button type="submit" class="btn btn-danger">Xóa dự án</button>
                                </form>
                            </c:if>
                        </div>
                        <c:if test="${user.userId==sessionScope.user.userId}">
                            <div class="" style="padding-top: 10px; float: left; margin-right: 15px;margin-left: 10px">
                                <button data-toggle="modal" data-target="#myModal" style="margin-top: 6px;">
                                    <div class="profile-pic-container">
                                        <img class="" style="width: 195px" src="assets/icon/new.JPG">
                                    </div>
                                </button>
                            </div>
                        </c:if>

                        <c:forEach var="dto" items="${sessionScope.MYPROJECTDETAIL}">
                            <div class="" style="padding-top: 10px; float: left; width: 210px; height: 210px; padding: 2px; margin: 10px; margin-right: 15px">
                                <div class="profile-pic-container">
                                    <img class="whiteCard" style="width: 206px;height: 206px;" src="${dto.url}"/>
                                    <c:if test="${user.userId==sessionScope.user.userId}">
                                        <div class="viewGalleryItemTopButtons">
                                            <a href="MainProfessionalServlet?btnAction=deletephoto&txtPhotoId=${dto.photoId}"><span style="color: #f4f4f4">X</span></a>
                                        </div>
                                    </c:if>
                                    <div class="project-meta-location2 text-bold ">
                                        <span class="text-bold one-line">${dto.tilte}</span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <c:if test="${user.userId==sessionScope.user.userId}">
                            <!-- Modal -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <form action="MainProfessionalServlet?btnAction=addPhoto" method="POST" id="testform" enctype="multipart/form-data" accept-charset="UTF-8">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                <h4 class="modal-title" id="myModalLabel">Thêm hình ảnh</h4>
                                                <input type="hidden"  name="txtProjectId" value="${project.projectId}" >

                                            </div>
                                            <div class="modal-body">
                                                <div class="newGalleryFormBody">
                                                    <input required class="form-control" type="text" id="newGalleryName" placeholder="Nhập tiêu đề hình ảnh" name="title" maxlength="71" value=""/>
                                                    <span>
                                                        <b>
                                                        </b>
                                                    </span>
                                                    <br/>
                                                    Upload Image <input required type="file"  name="fileUp" value="" /><br/>
                                                    <textarea rows="5" class="form-control" value="" name="description" placeholder="Miêu tả hình ảnh"></textarea>
                                                    Category <select name="ddlCategory">
                                                        <c:forEach var="cate" items="${sessionScope.CATE}" >
                                                            <option value="${cate.categoryId}">${cate.categoryName}</option>
                                                        </c:forEach>
                                                    </select>

                                                    Style <select name="ddlStyle">
                                                        <c:forEach var="style" items="${sessionScope.STYLE}" >
                                                            <option value="${style.styleId}">${style.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                <button class="btn btn-primary" type="submit">Thêm hình ảnh</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <!--                        end modal-->
                        </c:if>
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

