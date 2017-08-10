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

        <title>Thông tin sản phẩm</title>

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
                            <<div class="site_title"><i class="fa fa-paw"></i> <span>House Decor</span></div>
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
                        <!-- form seller -->
                        <div class="col-md-5 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Nhà cung cấp</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <br />
                                    <form class="form-horizontal form-label-left">
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Mã số thuế</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.sellerInfoUserId.taxNumber}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Người bán</label>

                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.sellerInfoUserId.sellerName}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Địa chỉ</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <textarea class="form-control" readonly="true">${detail.sellerInfoUserId.storeAddress}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-sm-3 col-xs-3">Số điện thoại</label>
                                            <div class="col-md-9 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.sellerInfoUserId.phone}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-3"></div>
                                            <div class="col-md-9">
                                                <button form="account" class="btn btn-success" name="btnAction" value="sellerinfo">Thông tin công ty</button>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="account" action="MainAdminServlet" method="POST">
                                        <input type="hidden" name="txtUserId" value="${detail.sellerInfoUserId.userId}" />
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- /form seller -->

                        <!-- form product -->
                        <div class="col-md-7 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Sản phẩm</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <br />
                                    <form class="form-horizontal form-label-left">
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Tên sản phẩm</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.productName}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Barcode</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.barCode}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Mô tả</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <textarea class="form-control" readonly="true">${detail.descripsion}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Chất liệu</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.material}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Kích thước</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.size}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Số lượng</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="number" class="form-control" readonly="true" value="${detail.quantity}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Giá</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.priceString}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Thể loại</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.categoryCategoryId.categoryName}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-2 col-sm-3 col-xs-3">Phong cách</label>
                                            <div class="col-md-10 col-sm-9 col-xs-9">
                                                <input type="text" class="form-control" readonly="true" value="${detail.styleStyleId.name}">
                                            </div>
                                        </div>
                                        <div class="form-group">

                                            <div class="col-md-9 col-md-offset-3">
                                                <c:if test="${detail.status==-1}">
                                                    <button type="submit" class="btn btn-success" name="btnAction" value="accept" form="product">Chấp nhận</button>
                                                </c:if>
                                                <c:if test="${detail.status==0}">
                                                    <button type="submit" class="btn btn-success" name="btnAction" value="accept" form="product">Chấp nhận</button>
                                                    <button type="submit" class="btn btn-warning" name="btnAction" value="block" form="product">Từ chối</button>
                                                </c:if>
                                                <c:if test="${detail.status==1}">
                                                    <button type="submit" class="btn btn-warning" name="btnAction" value="block" form="product">Từ chối</button>
                                                </c:if>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="product" action="ManageProductServlet" method="POST">
                                        <input type="hidden" name="txtProductId" value="${detail.productId}" />
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!-- /form product -->

                        <div class="clearfix"></div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>Hình ảnh</h2>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">
                                        <div class="row">
                                            <c:forEach items="${sessionScope.LIST}" var="list" varStatus="count">
                                                <div class="col-md-55">
                                                    <div class="thumbnail">
                                                        <div class="image view view-first">
                                                            <img style="width: 100%; display: block;" src="admin/${list.url}" alt="image" />
                                                        </div>
                                                        <div class="caption" style="color: black">
                                                            <p>${list.title}</p>
                                                        </div>
                                                    </div>
                                                    <div class="content">
                                                        <p>Mô tả: ${list.description}</p>
                                                        <p>Thể loại: ${list.categoryId.categoryName}</p>
                                                        <p>Phong cách: ${list.styleId.name}</p>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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