<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
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
        <script src="assets/plugins/jquery-1.10.2.js"></script> 
        
        <script>
        $(document).ready(function(){
          $('.category').on('change', function(){
            var category_list = [];
            $('#filters :input:checked').each(function(){
              var category = $(this).val();
              category_list.push(category);
            });

            if(category_list.length == 0)
              $('.resultblock').fadeIn();
            else {
              $('.resultblock').each(function(){
                var item = $(this).attr('data-tag');
                if(jQuery.inArray(item,category_list) > -1)
                  $(this).fadeIn('slow');
                else
                  $(this).hide();
              });
            }   
          });
        }); 
        </script>
    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top" style="background: #f4f4f4 !important;">
        <!--NAVBAR SECTION-->
        <%@include file="header.jsp" %>
        <!--END NAVBAR SECTION-->

        <div class="" style="margin-top: 100px;">
            <!--            LeftSide Bar-->
            <nav class="leftSideBar">
                <div class="sidebar">
                    <div class="sidebar-header" style="PADDING-LEFT: 13PX;">Category<span class="toggle down-icon"></span>
                    </div>
                    <div class="sidebar-body">		
                        <ul id="roomFilter">
                            <li class="sidebar-item" compid="opt" posid="0">			
                                <a class="sidebar-item-label_12" href="ShowListPhotoByCategoryServlet?txtCategoryName=Kitchen">
                                    <span class="option-text">Kitchen</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="1">			
                                <a class="sidebar-item-label_12" href="ShowListPhotoByCategoryServlet?txtCategoryName=Bath">
                                    <span class="option-text">Bath</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="2">			
                                <a class="sidebar-item-label_12" href="ShowListPhotoByCategoryServlet?txtCategoryName=Bedroom">
                                    <span class="option-text">Bedroom</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="3">			
                                <a class="sidebar-item-label_12" href="ShowListPhotoByCategoryServlet?txtCategoryName=Living">
                                    <span class="option-text">Living</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="4">			
                                <a class="sidebar-item-label_12" href="ShowListPhotoByCategoryServlet?txtCategoryName=Dining">
                                    <span class="option-text">Dining</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="5">			
                                <a class="sidebar-item-label_12" href="ShowListPhotoByCategoryServlet?txtCategoryName=Outdoor">
                                    <span class="option-text">Outdoor</span>
                                </a>
                            </li>        						
                            </ul>
                    </div>
                    <div class="sidebar-header" style="PADDING-LEFT: 13PX;">Style<span class="toggle down-icon"></span>
                    </div>
                    <div class="sidebar-body">
                        <div id="filters">
                        <ul id="filterblock">
                            <li class="sidebar-item" compid="opt" posid="0">			
                                <input id="check1" type="checkbox" name="check" value="1" class="category">
                                    <span for="check1">Modern</span>                 
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="1">
                                <input id="check1" type="checkbox" name="check" value="2" class="category">
                                <span for="check1">Victorian</span>                                 
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="2">
                                <input id="check1" type="checkbox" name="check" value="3" class="category">
                                <span for="check1">Asian</span>   
                            </li>						                                 						
                        </ul>
                        </div>
                    </div>
                </div>
            </nav>
            <!--        right side content-->
            <div class="rightSideContent">
                <div class="browsePhotos">
                    <div class="headerContainer">
                        <div style="padding: 0px 10px 0px 10px">
                            <h3>14,051,588 Home Design Photos</h3>
                        <div class="browseListBody">
                            <div class="content-rowxl">
                                <c:forEach var="dto" items="${listDTO}">
                                    <div class="resultblock" data-tag="${dto.styleId}">
                                        <a href="ShowPhotoDetailServlet?txtPhotoID=${dto.photoID}">
                                            <div class="searchresults imageArea">                            
                                                <img src="<c:out value="${dto.url}"/>" width="250px" height="270px" > 
                                                <p style="color: #9f9f9f"><c:out value="${dto.projectName}"/></p>
                                                <h6 style="margin-top: 10px; color: #3d8901"><c:out value="${dto.email}"/></h6>    
                                            </div>
                                            
                                        </a>
                                    </div>
                                        
                                </c:forEach>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- JAVASCRIPT FILES PLACED AT THE BOTTOM TO REDUCE THE LOADING TIME  -->
        <!-- CORE JQUERY  -->
        
        <!-- BOOTSTRAP SCRIPTS  -->
        <script src="assets/plugins/bootstrap.js"></script>
        <!-- EASING SCROLL SCRIPTS PLUGIN  -->
        <script src="assets/plugins/jquery.easing.min.js"></script>
        <!-- CUSTOM SCRIPTS   -->
        <script src="assets/js/custom.js"></script>
        
        
    </body>
</html>
