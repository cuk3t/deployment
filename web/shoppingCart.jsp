<%-- 
    Document   : shoppingCart
    Created on : Jul 31, 2017, 12:31:41 PM
    Author     : cuk3t
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />   
        <link href="assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" />
        <script src="assets/plugins/jquery-1.10.2.js"></script> 
        <script src="assets/js/product-detail.js"></script> 
    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top">
        <%@include file="header.jsp" %>
        <div class="container" style="padding-top: 100px">
            <div id="coPage">
                <h1 class="header-1 top">Your Shopping Cart</h1>
                <div id="coMain">
                    <div class="coBox shoppingCart">
                        <div class="coBoxHead">
                            <div class="itemImage"><b>Your Items</b></div>
                            <div class="itemDetail">&nbsp;</div>
                            <div class="itemPrice"><b>Price</b></div>
                            <div class="itemQuantity"><b>Qty</b></div>
                        </div>
                        <div class="coBoxBody" id="shopping-cart-items">
                            <c:forEach var="item" items="${cart}">
                                <div id="cart-id-${item.product.productId}" class="cart-item" data-value="${item.product.productId}">
                                    <div class="coProductGroup">
                                        <div class="item first" id="cItm_27518004">
                                            <div class="itemImage">
                                                <a href="" compid="img" objid="61906617">
                                                    <img src="${item.product.productPhotoCollection[0].url}" width="100" height="100">
                                                </a>
                                            </div>
                                            <div class="itemDetail">
                                                <div class="title">
                                                    <a href="" class="header-5 no-margin colorLink" style="color: #3d8901;font-size: 16px; font-weight: bold" id="product-name-${item.product.productId}" objid="61906617">${item.product.productName} </a>
                                                </div>
                                                <div class="vendor warning">Kích thước: ${item.product.size}</div>
                                                <div class="vendor warning">Vật liệu: ${item.product.material}</div>
                                                <div class="vendor warning">Bảo hành: ${item.product.warranty}</div>
                                            </div>
                                            <div class="itemPrice">
                                                <p class="header-5" style="font-size: 16px;font-weight: bold;">
                                                    <span id="item-price">${item.product.price}</span> vnd
                                                </p>
                                            </div>
                                            <div class="itemQuantity">
                                                <div class="quantity">
                                                    <div class="inline-dropdown-container">
                                                        <input type="hidden" value="${item.product.productId}" id="cart-product-id"/>

                                                        <input type="button" value="+" onclick="plusQuantity('${item.product.productId}');"/>
                                                        <span id="cart-quantity-${item.product.productId}"><c:out value="${item.quantity}"/></span>
                                                        <c:choose>
                                                            <c:when test="${item.quantity > 1}">
                                                                <input type="button" value="-" onclick="minusQuantity('${item.product.productId}');" id="btn-minus-quantity-${item.product.productId}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="button" value="-" onclick="minusQuantity('${item.product.productId}');" id="btn-minus-quantity-${item.product.productId}" disabled=""/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>

                                                    <div class="actions"><a href="javascript:removeCartItem('${item.product.productId}');">Remove</a></div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div id="coSecondary">
                    <div id="orderSummaryBox" class="parked">
                        <div class="coBox">
                            <div class="coBoxHead text-bold" style="font-weight: bold;padding-left: 15px;">Chi tiết đơn hàng</div>
                            <div class="coBoxBody text-l text-dt-s">
                                <div class="itemize" id="cart-sub-items">
                                    
                                </div>
                                <div id="creditCouponLine" class="itemize hide">
					<div class="od-label">Gift Card</div>
					<div class="value" id="os_credit_coupon"></div>
				</div>
                                <div class="itemize total">
                                    <div class="od-label">Total</div>
                                    <div id="cart-total" class="value">$0.00</div>
                                </div>
                            </div>
                        </div>
                        <div class="continue">
                            <a id="cartCheckoutBtn" data-signup="0" class="hzBtn primary" href="ShoppingCartServlet?method=order">Proceed to Checkout</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
