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
					<a href="IdeaBooksServlet?txtUserID=${dto.userId.userId}" class="colorLink back-to-ideabooks pull-left"><span class="hzi-font hzi-Left-Arrow"></span>&nbsp;Back to <c:out value="${dto.userId.firstname}"/> <c:out value="${dto.userId.lastname}"/>'s Ideabooks</a>
                                    </div>
                                    <h1 class="header-1 text-center view-gallery-header" itemprop="headline">${dto.title}</h1>
                                </div>
                                <div class="ideabook-actions-wrapper " style="margin-top: 25px">
                                    <div class="userActionsRowwhitebuttons">
                                        <div class="whitebuttons-sub-group">
<!--                                            <a class="hzBtnbutton_baseIcon" href="">
                                                <span  class="fa fa-pencil"></span>
                                                <span class="button-label">Edit</span>
                                            </a>
                                            <a class="hzBtnbutton_baseIcon" href="">
                                                <span  class="fa fa-print"></span>
                                                <span class="button-label">Print</span>
                                            </a>
                                            <a class="hzBtnbutton_baseIcon" href="">
                                                <span  class="fa fa-thumbs-o-up"></span>
                                                <span class="button-label">Like</span>
                                            </a>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <form method="POST" action="EditIdeaBookServlet">
                                <input type="hidden" name="txtIdeabookId" value="${dto.ideaBookId}"/>
                                <div class="inlineEditDesc" style="margin-top: 10px">
                                    <textarea <c:if test="${user.userId != dto.userId.userId}">readonly="readonly"</c:if> rows="3" class="form-control" value="" name="txtDes">${dto.description}</textarea>
                                </div>
                                <c:if test="${user.userId == dto.userId.userId}">
                                    <button class="btn btn-primary" style="margin-top: 5px;float: right;">Lưu</button>
                                </c:if>
                                
                            </form>
                            <c:if test="${user.userId == dto.userId.userId}">
                                <form method="POST" action="DeleteIdeaBookServlet">
                                    <input type="hidden" name="txtIdeabookId" value="${dto.ideaBookId}"/>

                                    <button class="btn btn-danger" style="margin-top: 5px; margin-right: 3px;float: right;">Delete</button>
                                </form>  
                            </c:if>
                              
                            
                        </div>
                        
                        <div id="gallerySpaces" style="margin-top: 10px">
                            <div class="viewSpaces-heading">
                                <i class="fa fa-picture-o fa-2x" style="border-radius: 10%;background-color: #3a8a00;"></i>
                                <span class="viewSpaces-heading-title text-bold">Photos &amp; Products</span>
                            </div>
                            <div>
                                <c:if test="${user.userId == dto.userId.userId}">
                                    <div style="margin-top: 10px; float: left; margin-left: 15px; margin-right: 10px">
                                        <button data-toggle="modal" data-target="#myModal" style="margin-top: 6px;">
                                            <div class="profile-pic-container">
                                                <img class="" style="width: 195px" src="assets/icon/new.JPG">
                                            </div>
                                        </button>
                                    </div>
                                </c:if>
                                <c:if test="${user.userId == dto.userId.userId}">
                                    <c:forEach var="ideaBookPhotoCollection" items="${dto.ideaBookPhotoCollection}">  
                                        <form action="EditIdeaBookPhotoServlet" method="POST">
                                            <input type="hidden" id="txtPhotoId" name="txtPhotoId" value="${ideaBookPhotoCollection.photoId}"/>
                                            <input type="hidden" id="txtIdeaBookId" name="txtIdeabookId" value="${dto.ideaBookId}"/>
                                            <div class="image_ideaBook">  
                                                <div class="profile-pic-container" style="width:206px ">
                                                    <img class="whiteCard" style="width: 206px;height: 206px;" src="${ideaBookPhotoCollection.url}">

                                                    <div class="viewGalleryItemTopButtons">
                                                        <a href="DeleteIdeaBookPhotoServlet?txtPhotoId=${ideaBookPhotoCollection.photoId}&txtIdeabookId=${dto.ideaBookId}"><span style="color: #f4f4f4">X</span></a>
                                                    </div>
                                                    <textarea class="txtDescription form-control" id="txtDescription" rows="2" class="" value="" name="txtDescriptionPhoto">${ideaBookPhotoCollection.description}</textarea>
                                                </div>                             
                                            </div>
                                        </form>                                        
                                    </c:forEach>
                                </c:if>
                                
                                <c:forEach var="IdeaBookPhotoRef" items="${dto.ideaBookPhotoRefCollection}">
                                    <form action="EditIdeaBookPhotoServlet" method="POST">
                                        <input type="hidden" id="txtPhotoId" name="txtPhotoId" value="${IdeaBookPhotoRef.ideaBookPhoto.photoId}"/>
                                        <input type="hidden" id="txtIdeaBookId" name="txtIdeabookId" value="${dto.ideaBookId}"/>
                                        <div class="image_ideaBook">  
                                            <div class="profile-pic-container" style="width:206px ">
                                                <a href="ShowPhotoDetailServlet?txtPhotoID=${IdeaBookPhotoRef.ideaBookPhoto.photoId}">
                                                <img class="whiteCard" style="width: 206px;height: 206px;" src="${IdeaBookPhotoRef.ideaBookPhoto.url}">
                                                </a>
                                                <c:if test="${user.userId == dto.userId.userId}">
                                                    <div class="viewGalleryItemTopButtons">
                                                        <a href="DeleteIdeaBookPhotoServlet?txtPhotoId=${IdeaBookPhotoRef.ideaBookPhoto.photoId}&txtIdeabookId=${dto.ideaBookId}"><span style="color: #f4f4f4">X</span></a>        
                                                    </div>
                                                </c:if>
                                                    <textarea <c:if test="${user.userId != dto.userId.userId}">readonly="readonly" </c:if> class="txtDescription form-control" id="txtDescription" rows="2" class="" value="" name="txtDescriptionPhoto">${IdeaBookPhotoRef.comment}</textarea>
                                            </div>                             
                                        </div>
                                    </form>
                                
                                </c:forEach>
                                <!-- Modal -->
                        <form action="AddImageToIdeaBookServlet" method="POST" id="testform" enctype="multipart/form-data" accept-charset="UTF-8">
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                        <h4 class="modal-title" id="myModalLabel">Thêm hình ảnh</h4>
                                        <input type="hidden"  name="txtIdeabookId" value="${dto.ideaBookId}" >
                                        
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
<script type="text/javascript">
    function updateIdeaBookPhoto(evt){
        var id = $(evt).parent().parent().parent().find('#txtPhotoID');
        debugger;
        $.ajax({
		url :  'EditIdeaBookPhotoServlet',
		data : {
			txtDescription : $(evt).val(),
			txtPhotoId : $(evt).parent().parent().parent().find('#txtPhotoId').val(),
			txtIdeaBookId : $(evt).parent().parent().parent().find('#txtIdeaBookId').val(),
		},
		type : 'POST',
		success : function(res) {
			try {
				
			} catch (e) {

			}
		},
		complete : function() {
			
		}
        })
    }
    
</script>
</html>


