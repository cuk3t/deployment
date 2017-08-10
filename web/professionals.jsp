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
                    <div class="headerContainer">
                        <div class="pro-landing-hero pro-landing-hero-v2" style="background-image: url('https://st.hzcdn.com/fimgs/dac1fa2108af1794_0383-w964-h350-b0-p0--home-design.jpg')">
                            <div class="pro-landing-hero__content text-center">
                                <div class="header-1main-titlembm">Let Houzz find the best pros for you</div>
                                <h2 class="header-4secondary-titletext-unbold">Over 1.5 million qualified pros worldwide</h2>
                                <div class="navbar-default" style="margin-left: 50px; padding-left: 6px" >
                                    <form action="showProfessionalServlet">
                                    
                                    <div class="pro-type-search-inputtext-left">
                                        <input name="txtProsName" class="form-control input-lg pro-search-input" id="proKeywordSearch" objid="proInputBar" tabindex="1" type="text" autocomplete="off" placeholder="Hãy nhập tên nhà thiết kế" value="">
                                        
                                    </div>
                                    <div class="pro-type-search-inputtext-right" style="    margin-left: 4px;">
                                        <select name="city" style="border-radius: 6px;height: 33px;" aria-invalid="false">
                                            <option value="">Khu Vực</option>
                                            <option value="1">Ho Chi Minh</option>
                                            <option value="2">Ha Noi</option>
                                            <option value="3">Da Nang</option>

                                        </select>
                                    </div>
                                        
                                    <div class="pro-type-search-inputtext-right" style="    margin-left: 4px;">
                                        <select name="typeWork" style="border-radius: 6px;height: 33px;" aria-invalid="false">
                                            <option value="">Thể Loại</option>
                                            <option value="1">Architects Designers</option>
                                            <option value="2">Home Builders</option>
                                            <option value="3">Interior Designers</option>
                                            <option value="4">Landscape Designers</option>
                                            <option value="5">Other Services</option>

                                        </select>
                                    </div>
                                    <div class="pro-search-button-input1" style="    margin-left: 8px;">
                                        <button value="Find Pros" name="btAction" id="proSearchBtn" objid="proSearchBtn" tabindex="3" class="btn btn-success" type="submit">Find Pros</button>
                                    </div>
                                    </form>
                                        
                                </div>
                            </div>
                        </div>
                        <div style="padding: 0px 10px 0px 10px">
                            <h3>14,051,588 Home Design Photos</h3>
                            <div class="browseListBody">
                                <div class="content-rowxl">

                                    <div class="imageArea2" compid="img">
                                        <a href="showProfessionalServlet?btAction=1">
                                            <div class="profile-pic-container">
                                                <img class="imageCover" src="https://st.hzcdn.com/fimgs/d9a130f907509cf7_0862-w221-h221-b0-p0--home-design.jpg"  >
                                                <div class="pro-tile__cover">
                                                    <div class="pro-tile__title">Architects &amp; Building Designers</div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="imageArea2" compid="img">
                                        <a href="showProfessionalServlet?btAction=2">
                                            <div class="profile-pic-container">
                                                <img class="imageCover" src="https://st.hzcdn.com/fimgs/0721a64207509cfe_0870-w221-h221-b0-p0--home-design.jpg"  >
                                                <div class="pro-tile__cover">
                                                    <div class="pro-tile__title">Home Builders</div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="imageArea2" compid="img">
                                        <a href="showProfessionalServlet?btAction=3">
                                            <div class="profile-pic-container">
                                                <img class="imageCover" src="https://st.hzcdn.com/fimgs/f6f13d6007509cff_0868-w221-h221-b0-p0--home-design.jpg"  >
                                                <div class="pro-tile__cover">
                                                    <div class="pro-tile__title">Interior Designers</div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="imageArea2" compid="img">
                                        <a href="showProfessionalServlet?btAction=4">
                                            <div class="profile-pic-container">
                                                <img class="imageCover" src="https://st.hzcdn.com/fimgs/15518d4207585cb4_8698-w221-h221-b0-p0--home-design.jpg"  >
                                                <div class="pro-tile__cover">
                                                    <div class="pro-tile__title">Landscape Designers</div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="imageArea2" compid="img">
                                        <a href="showProfessionalServlet?btAction=5">
                                            <div class="profile-pic-container">
                                                <img class="imageCover" src="https://st.hzcdn.com/fimgs/6ad19ba107c47745_3386-w221-h221-b0-p0--home-design.jpg"  >
                                                <div class="pro-tile__cover">
                                                    <div class="pro-tile__title">Orther Services</div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    
   
                                </div>
                            </div>
                        </div>
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
