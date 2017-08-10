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
<!--                        <div class="profile-pro-actions">
                            <a class="btn-white" style="text-decoration: none;" href="#">
                                <i class="fa fa-pencil fa" style="margin-right: 2px;"></i>Edit Profile
                            </a>
                        </div>-->
                        <div class="side-bar">

                            <ul class="list-inline" style="margin-top: 11px;">
                                <li class=""><div class="profile-pic-placeholder"></div></li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label" href="#" >
                                        <span class="option-text" style="color: #3d8901">Hồ Sơ</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="MainProfessionalServlet?btnAction=load&txtUserId=${sessionScope.user.userId}" >
                                        <span class="option-text">Dự Án</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="IdeaBooksServlet?txtUserID=${user.userId}" >
                                        <span class="option-text">Ý Tưởng</span>
                                    </a>
                                </li>
                                
                                <li class="sidebar-item">			
                                    <a class="sidebar-item-label-2" href="ShoppingCartServlet?method=order" >
                                        <span class="option-text">Mua Hàng</span>
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
                                        <span>Password</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="hz-one-card-right-content">
                
                <div class="hz-main-contents">
                    <form name="editProfile" method="POST" action="UpdateProfileServlet">
                        <section class="editProfileSectionfirstclearfix">
                            <h2>Account Information</h2>
                            <div class="form-group">
                            <label class="">Email <small>(private)</small></label>
                            <div style="overflow: hidden;" class="inline-btn"><input class="form-control input-lg edit-profile-input" readonly="" type="text" name="email" value="${dto.email}" autocomplete="off">
                            </div>
                            </div>
                        </section>
                        <section class="editProfileSection">
                            <h2>Profile Information</h2>
                            <div class="form-group">
                                <label class="">First name <small>(publicly displayed)</small></label>
                                <input required class="form-control input-lg edit-profile-input" type="text" id="firstName" name="firstName" value="${dto.firstname}">
                            </div>
                            <div class="form-group">
                                <label class="">Last name <small>(publicly displayed)</small></label>
                                <input required class="form-control input-lg edit-profile-input" type="text" id="lastName" name="lastName" value="${dto.lastname}">
                            </div>
                            <div class="form-group">
                                <label class="">Birthday</label>
                                <input required type="datetime" id="timeCheckIn" class="form-control input-lg edit-profile-input" placeholder="Birthday" name="birthDay" 
                                       value="<fmt:formatDate pattern = "dd/MM/yyy" value = "${dto.dateOfBirth}" />"/>
                            </div>
                            <div class="form-group">
                                <label class="">Gender </label>
                                
                                <input type="radio" id="male" name="gender" <c:if test="${userEntity.gender}" >checked</c:if> value="1">Male
                                <input type="radio"  id="famale" name="gender" <c:if test="${!userEntity.gender}" >checked</c:if> value="0">Female
                            </div>
                            <div class="form-group">
                                <label class="">Phone </label>
                                <input required class="form-control input-lg edit-profile-input" type="text" id="phone" name="phone" value="${userEntity.phoneNumber}">
                            </div>
                            <div class="form-group">
                                <label class="">City </label>
                                <select name="city"> 
                                    <option value="HCM" <c:if test="${userEntity.cityCode.cityName=='HCM'}" >selected</c:if> >Ho Chi Minh</option>
                                    <option value="HN" <c:if test="${userEntity.cityCode.cityName=='HN'}" >selected</c:if> >Ha Noi</option>
                                    <option value="DN" <c:if test="${userEntity.cityCode.cityName=='DN'}" >selected</c:if> >Da Nang</option>
                                   
                                </select>
                            </div>
                            <div class="form-group">
                                <label class="">Address </label><br/>
                                <textarea rows="3" cols="35" value="" name="address"style="font-size: 12px">${userEntity.primaryAddress}</textarea>
                            </div>
                            <div class="form-group">
                                <label class="">About me </label><br/>
                                <textarea rows="3" cols="35" value="" name="aboutMe" style="font-size: 12px">${userEntity.aboutMe}</textarea>
                            </div>
                            <div class="welcome-tour__footer-container">
                            <button class="btn btn-success ">Update</button>
                            </div>
                        </section>
                    </form>
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
       <script>
            $(function () {
                var nowTemp = new Date();
                var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

                var checkin = $('#timeCheckIn').datepicker({
                    onRender: function (date) {
                        return date.valueOf() > now.valueOf() ? 'disabled' : '';
                    },
                    format:'dd/mm/yyyy'
                }).on('changeDate', function (ev) {}).data('datepicker');
                
            });
        </script>
    </body>
</html>
