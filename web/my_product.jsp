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
                                            <a class="sidebar-item-label-2" href="ProfileServlet" >
                                                <span class="option-text">Hồ Sơ</span>
                                            </a>
                                        </li>

                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="MainProfessionalServlet?btnAction=load&txtUserId=${sessionScope.user.userId}" >
                                                <span class="option-text">Dự Án</span>
                                            </a>
                                        </li>
                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="IdeaBooksServlet?txtUserID=${user.userId}">
                                                <span class="option-text">Ý Tưởng</span>
                                            </a>
                                        </li
                                        <li class="sidebar-item">			
                                            <a class="sidebar-item-label-2" href="ShoppingCartServlet?method=order" >
                                                <span class="option-text">Mua Hàng</span>
                                            </a>
                                        </li>
                                        <c:if test="${user.roleId.roleId == 3}">                           
                                            <li class="sidebar-item">			
                                            <a class="sidebar-item-label" href="#" >
                                                <span class="option-text" style="color: #3d8901">Cửa Hàng</span>
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
            
                <div class="hz-one-card-right-content">
                
                <div class="hz-main-contents">
                    <div id="userGalleriesBody" scopeid="userGalleriesBody" class="comp-box">
                        
                        <div class="header-1 top"> <h2>Cửa Hàng Của Bạn </h2></div>                        
                        <div class="" style="padding-top: 10px; float: left; margin-right: 15px;margin-left: 10px;padding-right: 50px;">
                            <button data-toggle="modal" data-target="#myModal" style="margin-top: 6px;">
                                <div class="profile-pic-container">
                                    <img class="" style="width: 171px" src="assets/icon/addproduct.JPG">

                                </div>
                            </button>
                        </div>
                        <c:forEach var="dto" items="${listDTO}">
                        <div class="topic-group-column">
                            <div class="topic-group">
                                <a class="topic-thumb img-responsive-wrapper img-responsive-square" href="MyProductDetailServlet?action=showDetail&txtProductID=${dto.productId}">
                                    <img src="${dto.productPhotoCollection[0].url}" style="width: 180px; height: 180px">
                                    <div class="image-cover"></div>
                                </a>
                                <div class="topic-content">
                                    <span class="topic-title" style="font-size: 18px;">${dto.productName}</span>
                                    <ul class="sub-topics-list list-unstyled mtm">
                                        <li><a class="text-gray-light text-s text-dt-m" href="">Số lượng:${dto.quantity}</a></li>                                       
                                    </ul>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                        
                               <!-- Modal -->
                        <form action="MyProductServlet?action=create" method="POST">
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                        <h4 class="modal-title" id="myModalLabel">Thêm sản phẩm mới</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="newGalleryFormBody">
                                            <input class="form-control-2" type="text" id="newGalleryName" placeholder="Mã sản phẩm" name="productCode"  value="" style="margin-bottom: 10px"><br>
                                            <input class="form-control" type="text" id="newGalleryName" placeholder="Nhập tên sản phẩm" name="productName" maxlength="71" value="">
                                            <br>                                            
                                            <input class="form-control-2" type="text" id="newGalleryName" placeholder="Bar code" name="barCode"  value=""> 
                                            <input class="form-control-2" type="text" id="newGalleryName" placeholder="Giá sản phẩm" name="price"  value="">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Số lượng sản phẩm" name="quantity"  value="">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Kích thước" name="size"  value="">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Vật chất" name="material"  value="">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Bảo hành" name="warranty"  value="">
                                            <div class="pro-type-search-inputtext-right" style="">
                                                <select name="style" style="border-radius: 6px;height: 33px;" aria-invalid="false">
                                                    <option value="">Style</option>
                                                    <option value="1">Modern</option>
                                                    <option value="2">Victorian</option>
                                                    <option value="3">Asian</option>
                                                    <option value="4">Orther</option>
                                                </select>
                                            </div>
                                            <div class="pro-type-search-inputtext-right" style="margin-left: 40px; padding-bottom: 10px;">
                                                <select name="category" style="border-radius: 6px;height: 33px;" aria-invalid="false">
                                                    <option value="">Thể Loại</option>
                                                    <option value="1">Kitchen</option>
                                                    <option value="2">Bath</option>
                                                    <option value="3">Bedroom</option>
                                                    <option value="4">Living</option>
                                                    <option value="5">Dining</option>
                                                    <option value="6">Outdoor</option>
                                                    <option value="7">Other</option>
                                                </select>
                                            </div>
                                            <input type="hidden" id="allowDuplicate" name="allowDuplicate" value="false" >
                                                <textarea rows="5" class="form-control" value="" name="description"></textarea>
                                                
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button class="btn btn-primary">Thêm ý tưởng</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </form>
                    <!--                        end modal-->
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
