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

        <title>Quản lý tài khoản</title>

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
                        <div class="clearfix"></div>

                        <div class="row">
                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <h2>Quản lý tài khoản
                                        <c:if test="${sessionScope.ACCOUNTLIST[0][7]==-1}">
                                            <small>Bị chặn</small>
                                        </c:if>
                                        <c:if test="${sessionScope.ACCOUNTLIST[0][7]==1}">
                                            <small>Member</small>
                                        </c:if>
                                        <c:if test="${sessionScope.ACCOUNTLIST[0][7]==2}">
                                            <small>Professional</small>
                                        </c:if>
                                    </h2>
                                    <div class="x_content">
                                        <table id="datatable" class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Loại</th>
                                                    <th>Tên chủ TK</th>
                                                    <th>Số điện thoại</th>
                                                    <th>Email</th>
                                                    <th>Ngày đăng ký</th>
                                                    <th></th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${sessionScope.ACCOUNTLIST}" var="dto" varStatus="count">
                                                    <tr>
                                                        <td>
                                                            <c:if test="${dto[7]==1}">
                                                                Member
                                                            </c:if>
                                                            <c:if test="${dto[7]==2}">
                                                                Professional
                                                            </c:if>
                                                            <c:if test="${dto[7]==3}">
                                                                Seller
                                                            </c:if>
                                                        </td>
                                                        <td class="txt-oflo">${dto[2]} ${dto[1]}</td>
                                                        <td class="txt-oflo">${dto[3]}</td>
                                                        <td class="txt-oflo">${dto[4]}</td>
                                                        <td class="txt-oflo"><fmt:formatDate type="date" value = "${dto[5]}" /></td>
                                                        <td>
                                                            <form action="MainAdminServlet" method="POST">
                                                                <input type="hidden" name="txtUserId" value="${dto[0]}" />
                                                                <button type="submit" class="btn btn-primary" name="btnAction" value="ViewDetail">Chi tiết</button>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <c:if test="${dto[6]==1}">
                                                                <form action="MainAdminServlet" method="POST">
                                                                    <input type="hidden" name="txtUserId" value="${dto[0]}" />
                                                                    <button type="submit" class="btn btn-warning" name="btnAction" value="block">Khóa tài khoản</button>
                                                                </form>
                                                            </c:if>
                                                            <c:if test="${dto[6]==-1}">
                                                                <form action="MainAdminServlet" method="POST">
                                                                    <input type="hidden" name="txtUserId" value="${dto[0]}" />
                                                                    <button type="submit" class="btn btn-success" name="btnAction" value="accept">Mở tài khoản</button>
                                                                </form>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /page content -->

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
