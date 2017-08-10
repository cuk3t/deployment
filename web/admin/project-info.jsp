<%-- 
    Document   : manage-member
    Created on : May 25, 2017, 3:21:16 PM
    Author     : Lê Đại An
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Meta, title, CSS, favicons, etc. -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Duyệt Project</title>

        <!-- Bootstrap -->
        <link href="admin/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="admin/vendors/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <!-- NProgress -->
        <link href="admin/vendors/nprogress/nprogress.css" rel="stylesheet">
        <!-- iCheck -->
        <link href="admin/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
        <!-- Datatables -->
        <link href="admin/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="admin/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
        <link href="admin/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
        <link href="admin/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
        <link href="admin/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">

        <!-- Custom Theme Style -->
        <link href="admin/build/css/custom.min.css" rel="stylesheet">
    </head>

    <body class="nav-md">
        <div class="container body">
            <div class="main_container">
                <div class="col-md-3 left_col">
                    <div class="left_col scroll-view">
                        <div class="navbar nav_title" style="border: 0;">
                            <div class="site_title"><i class="fa fa-paw"></i> <span>House Decor</span></div>
                        </div>

                        <div class="clearfix"></div>
                        <br />

                        <!-- sidebar menu -->
                        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                            <div class="menu_section">
                                <h3>Quản lý</h3>
                                <ul class="nav side-menu">
                                    <li><a><i class="fa fa-address-card-o" aria-hidden="true"></i> Tài khoản <span class="fa fa-chevron-down"></span></a>
                                        <ul class="nav child_menu">
                                            <li><a href="MainAdminServlet?btnAction=mem"> Member </a></li>
                                            <li><a href="MainAdminServlet?btnAction=pro"> Professional </a></li>
                                            <li><a href="MainAdminServlet?btnAction=blacklist"> Danh sách bị chặn </a></li>
                                        </ul>
                                    </li>
                                    <li><a><i class="fa fa-product-hunt"></i> Sản phẩm <font color="red">Mới</font><span class="fa fa-chevron-down"></span></a>
                                        <ul class="nav child_menu">
                                            <li><a href="ManageProductServlet?btnAction=waiting"> Danh sách chờ duyệt </a></li>
                                            <li><a href="ManageProductServlet?btnAction=approved"> Danh sách đã duyệt </a></li>
                                            <li><a href="ManageProductServlet?btnAction=blocked"> Danh sách bị chặn </a></li>
                                        </ul>
                                    </li>
                                    <li><a><i class="fa fa-lightbulb-o"></i> Ideabooks <font color="red">Mới</font><span class="fa fa-chevron-down"></span></a>
                                        <ul class="nav child_menu">
                                            <li><a href="ManageIdeabookServlet?btnAction=waiting"> Danh sách chờ duyệt </a></li>
                                            <li><a href="ManageIdeabookServlet?btnAction=approved"> Danh sách đã duyệt </a></li>
                                        </ul>
                                    </li>
                                    <li><a><i class="fa fa-building-o"></i> Projects <font color="red">Mới</font><span class="fa fa-chevron-down"></span></a>
                                        <ul class="nav child_menu">
                                            <li><a href="ManageProjectServlet?btnAction=waiting"> Danh sách chờ duyệt </a></li>
                                            <li><a href="ManageProjectServlet?btnAction=approved"> Danh sách đã duyệt </a></li>
                                        </ul>
                                    </li>
                                    <li><a href="MainAdminServlet?btnAction=LoadSellers"><i class="fa fa-sellsy"></i> Nhà cung cấp </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- /sidebar menu -->

                        <!-- /menu footer buttons -->
                        <div class="sidebar-footer hidden-small">
                            <a data-toggle="tooltip" data-placement="top" title="Đăng xuất" href="MainAdminServlet?btnAction=logout">
                                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                            </a>
                        </div>
                        <!-- /menu footer buttons -->
                    </div>
                </div>

                <!-- top navigation -->
                <div class="top_nav">
                    <div class="nav_menu">
                        <nav>
                            <div class="nav toggle">
                                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                            </div>

                            <ul class="nav navbar-nav navbar-right">
                                <li class="">
                                    <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                        Welcome, Admin
                                        <span class=" fa fa-angle-down"></span>
                                    </a>
                                    <ul class="dropdown-menu dropdown-usermenu pull-right">
                                        <li><a href="MainAdminServlet?btnAction=logout"><i class="fa fa-sign-out pull-right"></i>Đăng xuất</a></li>
                                    </ul>
                                </li>

                                <li role="presentation" class="dropdown">
                                    <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                                        <i class="fa fa-envelope-o"></i>
                                        <span class="badge bg-green">6</span>
                                    </a>
                                    <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                                        <li>
                                            <a>
                                                <span class="image"><img src="images/img.jpg" alt="Profile Image" /></span>
                                                <span>
                                                    <span>John Smith</span>
                                                    <span class="time">3 mins ago</span>
                                                </span>
                                                <span class="message">
                                                    Film festivals used to be do-or-die moments for movie makers. They were where...
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <div class="text-center">
                                                <a>
                                                    <strong>See All Alerts</strong>
                                                    <i class="fa fa-angle-right"></i>
                                                </a>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <!-- /top navigation -->

                <!-- page content -->
                <div class="right_col" role="main">
                    <div class="">
                        <c:set value="${sessionScope.DETAIL}" var="detail"/>
                        <!-- form Project -->
                        <div class="col-md-4 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Thông tin Project</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <form class="form-horizontal form-label-left">

                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Tên dự án</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.projectName}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Địa chỉ</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <textarea class="form-control" readonly="true">${detail.address}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Giá</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.costString}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Website</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <a href="${detail.website}">
                                                    <input type="text" class="form-control" readonly="true" value="${detail.website}">
                                                </a>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Năm</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.year}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Tài khoản</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <a href="MainAdminServlet?btnAction=viewdetail&txtUserId=${detail.userId.userId}">
                                                    <input type="text" class="form-control" readonly="true" value="${detail.userId.lastname} ${detail.userId.firstname}">
                                                </a>
                                                </input>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Email</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.userId.email}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Điện thoại</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.userId.phoneNumber}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label style="color: red">${requestScope.ERROR}</label>
                                            <br/>
                                            <c:if test="${detail.status==0}">
                                                <button form="project" type="submit" class="btn btn-success" name="btnAction" value="accept">Hoàn Thành</button>
                                            </c:if>
                                            <c:if test="${detail.status==1}">
                                                <label style="color: red">Đã được duyệt</label>
                                            </c:if>
                                        </div>
                                    </form>
                                    <form id="project" action="ManageProjectServlet" method="POST">
                                        <input type="hidden" name="txtProjectId" value="${detail.projectId}" />
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- /form Project -->

                        <!-- form detail -->
                        <c:if test="${sessionScope.LIST != null}">
                            <c:set var="select" value ="${sessionScope.SELECT}"/>
                            <c:set var="list" value="${sessionScope.LIST}"/>
                            <div class="col-md-8 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>Chi tiết hình ảnh</h2>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">
                                        <div class="col-md-5">
                                            <img src="${list[select].url}" style="width: 100%">
                                        </div>

                                        <div class="col-md-7">
                                            <form class="form-horizontal form-label-left">

                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-3">Tiêu đề</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-9">
                                                        <input type="text" class="form-control" readonly="true" value="${list[select].tilte}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-3">Mô tả</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-9">
                                                        <textarea class="form-control" readonly="true">${list[select].description}</textarea>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-3">Thể loại</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-9">
                                                        <input type="text" class="form-control" readonly="true" value="${list[select].categoryId.categoryName}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-3">Phong cách</label>
                                                    <div class="col-md-9 col-sm-9 col-xs-9">
                                                        <input type="text" class="form-control" readonly="true" value="${list[select].styleId.name}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="col-md-9 col-md-offset-3">
                                                        <c:if test="${list[select].status==-1}">
                                                            <button form="photo" type="submit" class="btn btn-success" name="btnAction" value="acceptPhoto">Chấp nhận</button>
                                                        </c:if>
                                                        <c:if test="${list[select].status==0}">
                                                            <button form="photo" type="submit" class="btn btn-success" name="btnAction" value="acceptPhoto">Chấp nhận</button>
                                                            <button form="photo" type="submit" class="btn btn-warning" name="btnAction" value="blockPhoto">Từ chối</button>
                                                        </c:if>
                                                        <c:if test="${list[select].status==1}">
                                                            <button form="photo" type="submit" class="btn btn-warning" name="btnAction" value="blockPhoto">Từ chối</button>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </form>
                                            <form id="photo" action="ManageProjectServlet" method="POST">
                                                <input type="hidden" name="page" value="project" />
                                                <input type="hidden" name="txtPhotoId" value="${list[select].photoId}" />
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <!-- /form detail -->
                        <!-- form list photo -->
                        <div class="clearfix"></div>
                        <c:if test="${sessionScope.LIST!=null}">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="x_panel">
                                        <div class="x_title">
                                            <h2>Hình ảnh</h2>
                                            <div class="clearfix"></div>
                                        </div>
                                        <div class="x_content">

                                            <div class="row">
                                                <c:forEach items="${list}" var="dto" varStatus="count">
                                                    <div class="col-md-55">
                                                        <div class="thumbnail">
                                                            <div class="image view view-first">
                                                                <a href="ManageProjectServlet?btnAction=select&id=${dto.photoId}&form=1">
                                                                    <c:if test="${dto.status==-1}">
                                                                        <div style="postition: relative; top:0; left:0">
                                                                            <img style="width: 100%; display: block;" src="${dto.url}" alt="image"style="filter: blur(1px); postition: relative; top:0; left:0 " />
                                                                            <img src="admin/incorrect.png" height="36dp" width="48dp" style="position: absolute; top: 8px; display: block"/>
                                                                        </div>
                                                                    </c:if>
                                                                    <c:if test="${dto.status==0}">
                                                                        <img style="width: 100%; display: block;" src="${dto.url}" alt="image" />
                                                                    </c:if>
                                                                    <c:if test="${dto.status==1}">
                                                                        <div style="postition: relative; top:0; left:0">
                                                                            <img style="width: 100%; display: block;" src="${dto.url}" alt="image" style="filter: blur(1px); postition: relative; top:0; left:0 "/>
                                                                            <img src="admin/correct.png" height="36dp" width="48dp" style="position: absolute; top: 8px; display: block"/>
                                                                        </div>
                                                                    </c:if>
                                                                </a>
                                                            </div>
                                                            <div class="caption">
                                                                <p>${dto.tilte}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
                <!-- /page content -->
            </div>
        </div>

        <!-- jQuery -->
        <script src="admin/vendors/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="admin/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
        <!-- FastClick -->
        <script src="admin/vendors/fastclick/lib/fastclick.js"></script>
        <!-- NProgress -->
        <script src="admin/vendors/nprogress/nprogress.js"></script>
        <!-- iCheck -->
        <script src="admin/vendors/iCheck/icheck.min.js"></script>
        <!-- Datatables -->
        <script src="admin/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="admin/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
        <script src="admin/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
        <script src="admin/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
        <script src="admin/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
        <script src="admin/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
        <script src="admin/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
        <script src="admin/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
        <script src="admin/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
        <script src="admin/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
        <script src="admin/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
        <script src="admin/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
        <script src="admin/vendors/jszip/dist/jszip.min.js"></script>
        <script src="admin/vendors/pdfmake/build/pdfmake.min.js"></script>
        <script src="admin/vendors/pdfmake/build/vfs_fonts.js"></script>

        <!-- Custom Theme Scripts -->
        <script src="admin/build/js/custom.min.js"></script>

    </body>
</html>

