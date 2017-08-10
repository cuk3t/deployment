<%-- 
    Document   : login
    Created on : Jul 9, 2017, 12:03:48 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <meta charset="UTF-8">
        <title>Login & Register form</title>



        <link rel="stylesheet" href="assets/css/style_1.css">


    </head>

    <body>
        <div class="login-wrap">
            <h2>Login</h2>

            <div class="form">
                <form class="" method="post" action="MainServlet">
                    <input  required type="text" placeholder="Email" name="txtUsername"/>
                    <input required type="password" placeholder="Password" name="txtPassword"/>
                    <h5 style="color: red; padding-left: 36px">${requestScope.ERROR}</h5>
                    <button type="submit" name="btAction" id="register" value="login"> 
                        Login
                    </button>
                    <a href="register.jsp"> <p> Don't have an account? Register </p></a>
                </form>
            </div>
        </div>
        <script src='https://code.jquery.com/jquery-1.10.0.min.js'></script>

        <script src="assets/js/index.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/bootstrap-datepicker.js"></script>
        <script src='assets/js/jquery-2.1.1.min.js'></script>
        <script src='assets/js/jquery.validate.js'></script>
    </body>
</html>

