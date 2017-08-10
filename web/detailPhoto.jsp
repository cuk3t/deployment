<%-- 
    Document   : detailPhoto
    Created on : Jul 3, 2017, 12:40:13 PM
    Author     : cuk3t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <title>House Decor: Photo </title>
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="assets/css/style.css" rel="stylesheet" />        
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
        <link href="assets/css/datepicker.css" rel="stylesheet" />  
    </head>
    <body>
    
    <div class="modal fade in" id="portfolioBig1" tabindex="-1" role="dialog" aria-hidden="false" style="display: block;">
        <%@include file="header.jsp" %>
        <div class="modal-content">
            
            <div class="container">
                <div class="row" style=" padding-bottom: 100px;padding-top: 84px;">
                    <div class="col-md-12">
                        <div class="modal-body" style="margin: auto">
                            <a href='javascript:history.go(-1)'>
                            <span class="fa fa-scissors fa-3x pull-right" >CLOSE</span>
                            </a>
                            <form action="SavePhotoToIdeaBookServlet" method="POST">
                            
                                <c:set var="ideaBookPhoto" value="${ideaBookPhoto}"/>
                                <hr>
                                <h1>${ideaBookPhoto.tilte}</h1>
                                <input type="hidden" name="txtPhotoID" value="${ideaBookPhoto.photoId}"/>
                                <br>
                                <img  src="${ideaBookPhoto.url}" class="img-responsive " style="max-height: 560px;max-width: 680px;float: left" alt="">
                                <div style="float: left; margin-left: 50px">
                                    
                                    
                                    <select name="txtIdeaBookID" style="border-radius: 6px;height: 33px; width: 300px" aria-invalid="false">                                              
                                        <c:forEach var="dto" items="${listDTO}">
                                        <option value="${dto.ideaBookId}"><c:out value="${dto.title}"/></option>
                                        </c:forEach>
                                    </select>
                                    
                                    <textarea name="txtComment" rows="5" class="form-control" placeholder="Add notes, What do you like about this photo?" value="" style="width: 300px; margin-top: 10px" ></textarea>
                                    <button style="margin-top: 10px" class="btn btn-success">Lưu về Ý tưởng</button>
                                     <p><c:if test="${duplicatePhoto != null}">
                                        <c:out value="${duplicatePhoto}"/>
                                    </c:if></p>    
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
