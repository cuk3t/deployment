<%-- 
    Document   : editIdeaBook
    Created on : Jun 14, 2017, 2:21:49 PM
    Author     : cuk3t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>House Decor: Profile </title>
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="assets/css/style.css" rel="stylesheet" />    
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
      
        <link href="assets/css/datepicker.css" rel="stylesheet" />
        <script src="assets/plugins/jquery-1.10.2.js"></script> 
        <script src="assets/js/custom.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.validate.js"></script>
        
    </head>
    <body style="background: #f4f4f4 !important;" >
        <%@include file="header.jsp" %>
        <c:set var="dto" value="${dto}"/>
       
        <div id="mainArea" style="padding-top: 90px">
            <div class="container">
                <div class="hz-main-contents">
                    <div class="fullContentstandardview">
                        <div id="viewGallery1ColContent">
                            <div id="viewGalleryMainNarrow" itemscope="" itemtype="http://schema.org/Article">
                                <div class="view-gallery-header-row">
                                    <div class="clearfix">
					<a href="MyProductServlet?action=show&txtUserID=${dto.userId.userId}" class="colorLink back-to-ideabooks pull-left"><span class="hzi-font hzi-Left-Arrow"></span>&nbsp;Back to <c:out value="${dto.userId.sellerName}"/> <c:out value="${dto.userId.sellerName}"/>'s Shop</a>
                                    </div>
                                    <h1 class="header-1 text-center view-gallery-header" itemprop="headline">${dto.productName}</h1>
                                </div>
                                <div class="ideabook-actions-wrapper " style="margin-top: 25px">
                                    <div class="userActionsRowwhitebuttons">
                                        <div class="whitebuttons-sub-group">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <form method="POST" action="MyProductDetailServlet?action=edit">
                                <input type="hidden" name="txtProductId" value="${dto.productId}"/>
                                <div class="modal-body">
                                        <div class="newGalleryFormBody">
                                            <span>Mã sản phẩm</span><br>
                                            <input readonly="true" class="form-control-2" type="text" id="newGalleryName" placeholder="Mã sản phẩm" name="productID" value="${dto.productId}" style="margin-bottom: 10px"><br>
                                            
                                            <br>
                                            <div class="">
                                            <div><span>Tên Sản Phẩm</span></div>
                                            <input class="form-control" type="text" id="newGalleryName" placeholder="Nhập tên sản phẩm" name="productName" maxlength="71" value="${dto.productName}">
                                            </div>
                                            
                                            <div>
                                                <span class="form-control-2" style="border: none">Bar code</span> <span class="form-control-2" style="border: none">Giá Sản Phẩm</span>
                                                <input class="form-control-2" type="text" id="newGalleryName" placeholder="Bar code" name="barCode" value="${dto.barCode}"> 
                                                <div>
                                                    <input class="form-control-2" type="text" id="newGalleryName" placeholder="Giá sản phẩm" name="price" value="${dto.price}"><label style="padding-top: 40px;">VND</label>
                                                </div>
                                            </div>
                                            <div>
                                            <span class="form-control-4" style="border: none">Số Lượng Trong Kho</span>
                                            <span class="form-control-4" style="border: none">Kích Thước</span>
                                            <span class="form-control-4" style="border: none">Vật Liệu Của Sản Phẩm</span>
                                            <span class="form-control-4" style="border: none">Bảo Hành</span>
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Số lượng sản phẩm" name="quantity" value="${dto.quantity}">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Kích thước" name="size" value="${dto.size}">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Vật chất" name="material" value="${dto.material}">
                                            <input class="form-control-3" type="text" id="newGalleryName" placeholder="Bảo hành" name="warranty" value="${dto.warranty}">
                                            </div>
                                            <br>
                                            
                                            <div class="pro-type-search-inputtext-right" style="">
                                                <select name="style" style="border-radius: 6px;height: 33px;" aria-invalid="false">
                                                    <option value="" >Style</option>
                                                    <option value="1" <c:if test="${dto.styleStyleId.styleId == 1}">  selected="selected"</c:if> >Modern</option>
                                                    <option value="2" <c:if test="${dto.styleStyleId.styleId == 2}">  selected="selected"</c:if> >Victorian</option>
                                                    <option value="3" <c:if test="${dto.styleStyleId.styleId == 3}">  selected="selected"</c:if> >Asian</option>
                                                    <option value="4" <c:if test="${dto.styleStyleId.styleId == 4}">  selected="selected"</c:if> >Orther</option>
                                                </select>
                                            </div>
                                            <div class="pro-type-search-inputtext-right" style="margin-left: 40px; padding-bottom: 10px;">
                                                <select name="category" style="border-radius: 6px;height: 33px;" aria-invalid="false">
                                                    <option value="">Thể Loại</option>
                                                    <option value="1" <c:if test="${dto.categoryCategoryId.categoryId == 1}">  selected="selected"</c:if> >Kitchen</option>
                                                    <option value="2" <c:if test="${dto.categoryCategoryId.categoryId == 2}">  selected="selected"</c:if> >Bath</option>
                                                    <option value="3" <c:if test="${dto.categoryCategoryId.categoryId == 3}">  selected="selected"</c:if> >Bedroom</option>
                                                    <option value="4" <c:if test="${dto.categoryCategoryId.categoryId == 4}">  selected="selected"</c:if> >Living</option>
                                                    <option value="5" <c:if test="${dto.categoryCategoryId.categoryId == 5}">  selected="selected"</c:if> >Dining</option>
                                                    <option value="6" <c:if test="${dto.categoryCategoryId.categoryId == 6}">  selected="selected"</c:if> >Outdoor</option>
                                                    <option value="7" <c:if test="${dto.categoryCategoryId.categoryId == 7}">  selected="selected"</c:if> >Other</option>
                                                </select>
                                            </div>
                                            <input type="hidden" id="allowDuplicate" name="allowDuplicate" value="false">
                                                <textarea rows="5" class="form-control" value="" name="description">${dto.descripsion}</textarea>
                                                
                                        </div>
                                    </div>
<!--                                <div class="inlineEditDesc" style="margin-top: 10px">
                                    <textarea rows="3" class="form-control" value="" name="txtDes">${dto.descripsion}</textarea>
                                </div>                                -->
                                <button class="btn btn-primary" style="margin-top: 5px;float: right;">Lưu</button>                          
                                
                            </form>                            
                            <form method="POST" action="">
                                <input type="hidden" name="txtProductkId" value="${dto.productId}"/>
                                <button class="btn btn-danger" style="margin-top: 5px; margin-right: 3px;float: right;">Delete</button>
                            </form>                         
                        </div>
                        
                        <div id="gallerySpaces" style="margin-top: 10px">
                            <div class="viewSpaces-heading">
                                <i class="fa fa-picture-o fa-2x" style="border-radius: 10%;background-color: #3a8a00;"></i>
                                <span class="viewSpaces-heading-title text-bold">Photos &amp; Products</span>
                            </div>
                            <div>                                
                                <div style="margin-top: 10px; float: left; margin-left: 15px; margin-right: 10px">
                                    <button data-toggle="modal" data-target="#myModal" style="margin-top: 6px;">
                                        <div class="profile-pic-container">
                                            <img class="" style="width: 195px" src="assets/icon/new.JPG">
                                        </div>
                                    </button>
                                </div>
                                
                                <c:if test="${user.userId == dto.userId.userId}">
                                    <c:forEach var="productPhotoCollection" items="${dto.productPhotoCollection}">  
                                        <form action="EditIdeaBookPhotoServlet" method="POST">
                                            <input type="hidden" id="txtPhotoId" name="txtPhotoId" value="${productPhotoCollection.photoId}"/>
                                            <input type="hidden" id="txtIdeaBookId" name="txtProductId" value="${dto.productId}"/>
                                            <div class="image_ideaBook">  
                                                <div class="profile-pic-container" style="width:206px ">
                                                    <img class="whiteCard" style="width: 206px;height: 206px;" src="${productPhotoCollection.url}">

                                                    <div class="viewGalleryItemTopButtons">
                                                        <a href=""><span style="color: #f4f4f4">X</span></a>
                                                    </div>
                                                   
                                                </div>                             
                                            </div>
                                        </form>                                        
                                    </c:forEach>
                                </c:if>                               
                                <!-- Modal -->
                        <form action="AddImageToProductServlet" method="POST" id="testform" enctype="multipart/form-data" accept-charset="UTF-8">
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                        <h4 class="modal-title" id="myModalLabel">Thêm hình ảnh cho sản phẩm</h4>
                                        <input type="hidden"  name="txtProductId" value="${dto.productId}" >
                                        
                                    </div>
                                    <div class="modal-body">
                                        <div class="newGalleryFormBody">
                                            <input required class="form-control" type="text" id="newGalleryName" placeholder="Nhập tiêu đề hình ảnh" name="newGalleryName" maxlength="71" value="">
                                                <span><b>
                                                        
                                                    </b>
                                                </span>
                                            <br>
                                            Upload Image <input required type="file"  name="fileUp" value="" > <br/>
                                                <textarea rows="5" class="form-control" value="" name="GalleryDescription" placeholder="Miêu tả hình ảnh"></textarea>
                                                
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button class="btn btn-primary">Thêm hình ảnh</button>
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
        </div>
        <script type="text/javascript">
        
        $( document ).ready(function(){
                $('#testform').validate({

                    rules: {
                        fileUp: {
                            accept: "bmp|png|jpg"
                        }
                    }//end rule
                });
                debugger;
                $(".txtDescription.form-control").on("change input paste keyup", function() {
                    updateIdeaBookPhoto(this);
                 });
            });
        </script>
        <%@include file="footer.jsp" %>
    </body>

</html>


