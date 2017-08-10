<%-- 
    Document   : bar-stool-sale
    Created on : May 16, 2017, 4:14:02 PM
    Author     : Lê Đại An
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
        <title>Bar Stool Sale</title>
        <!-- BOOTSTRAP CORE STYLE CSS -->
        <link href="../assets/css/bootstrap.css" rel="stylesheet" />
        <!-- FONT AWESOME STYLE CSS -->
        <link href="../assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <!-- CUSTOM STYLE CSS -->
        <link href="../assets/css/style.css" rel="stylesheet" />    
        <!-- GOOGLE FONT -->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    </head>
    <body style="background: #f4f4f4 !important;">
        <!--HEADER-->
        <%@include file="../header.jsp" %>
        <!--END HEADER-->
        <!--MAIN CONTENT-->
        <div class="container" style="margin-top: 100px;">
            <!--            LeftSide Bar-->
            <nav class="leftSideBar">
                <div class="sidebar">
                    <div class="sidebar-header">Room<span class="toggle down-icon"></span>
                    </div>
                    <div class="sidebar-body">		<ul id="roomFilter">
                            <li class="sidebar-item" compid="opt" posid="0">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/kitchen">
                                    <span class="option-text">Kitchen</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="1">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/bath">
                                    <span class="option-text">Bath</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="2">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/bedroom">
                                    <span class="option-text">Bedroom</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="3">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/living">
                                    <span class="option-text">Living</span>
                                </a>
                            </li>						
                            <li class="sidebar-item" compid="opt" posid="4">			
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/dining">
                                    <span class="option-text">Dining</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="5">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/outdoor">
                                    <span class="option-text">Outdoor</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="6">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/baby-and-kids-photos">
                                    <span class="option-text">Baby &amp; Kids</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="7">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/home-office">
                                    <span class="option-text">Home Office</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="8">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/closet">
                                    <span class="option-text">Storage &amp; Closet</span>
                                </a>
                            </li>
                            <li class="sidebar-item" compid="opt" posid="9">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/exterior">
                                    <span class="option-text">Exterior</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="10">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/basement">
                                    <span class="option-text">Basement</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="11">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/entry">
                                    <span class="option-text">Entry</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="12">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/garage-and-shed">
                                    <span class="option-text">Garage &amp; Shed</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="13">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/home-gym">
                                    <span class="option-text">Gym</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="14">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/home-bar">
                                    <span class="option-text">Home Bar</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="15">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/hall">
                                    <span class="option-text">Hall</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="16">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/laundry-room">
                                    <span class="option-text">Laundry</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="17">
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/staircase">
                                    <span class="option-text">Staircase</span>
                                </a>
                            </li>
                            <li class="hidden sidebar-item" compid="optHidden" posid="18">		
                                <a class="sidebar-item-label" href="https://www.houzz.com/photos/wine-cellar">
                                    <span class="option-text">Wine Cellar</span>
                                </a>
                            </li>
                            <li class="sidebar-item more-items" id="roomFilterMore" compid="optMore">
                                <span class="sidebar-item-label hz-link-style" onclick="HZ.navigation.Utils.expandBox( & quot; roomFilter & quot; )">
                                    More Rooms...
                                </span>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!--        right side content-->
            <div class="rightSideContent">
                <div>
                    <h5>Bar Stool Sale</h5>
                </div>
            </div>
        </div>
        <!--END MAIN CONTENT-->
        <!--FOOTER-->
        <%@include file="../footer.jsp" %>
        <!--END FOOTER-->
    </body>
</html>
