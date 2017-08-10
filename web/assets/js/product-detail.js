var total;

$(document).ready(function () {
    registerEvent();
    getTotalCartPrice();
    displayTotalCartPrice();
});

function getTotalCartPrice() {
    total = 0;
    $("#cart-sub-items").empty();
    $.each($(".cart-item"), function (index, item) {
        var productId = $(item).attr("data-value");
        var productPrice = $(item).find("#item-price").html();
        var productQuantity = $(item).find("#cart-quantity-" + productId).text();
        var productName = $(item).find("#product-name-" + productId).text();
        total += productPrice * productQuantity;
        var subTotalItem = "<div class=\"od-label\" style=\"text-overflow: ellipsis; white-space: nowrap; overflow: hidden;\" title=\"" + productName + "\">" + productName + "</div><div id=\"os_subtotal\" class=\"value\">" + (productPrice * productQuantity) + " VNĐ</div>";
        $("#cart-sub-items").append(subTotalItem);
    });
}

function displayTotalCartPrice() {
    $("#cart-total").html(total + " VNĐ");
}

function registerEvent() {
    $("#btn-add-to-cart").off("click").on("click", function () {

        var productId = $("#product-id").val();
        var quantity = $("#qtySelector option:selected").val();
        addToCart(productId, quantity);
    });
}

function addToCart(productId, quantity) {
    var URL = "ShoppingCartServlet";
    var requestData = {
        productId: productId,
        quantity: quantity,
        method: "add"
    };
    $.ajax({
        url: URL,
        type: "POST",
        data: requestData,
        success: function (response) {
            $("#add-cart-modal").modal("show");
        },
        error: function (jqXHR, status, error) {
            console.log(status);
        }
    });
}

function removeCartItem(productId) {
    var URL = "ShoppingCartServlet";
    var requestData = {
        productId: productId,
        method: "remove"
    };
    $.ajax({
        url: URL,
        type: "POST",
        data: requestData,
        success: function (response) {
            $("#shopping-cart-items").children("#cart-id-" + productId).remove();
            getTotalCartPrice();
            displayTotalCartPrice();
        },
        error: function (jqXHR, status, error) {
            console.log(status);
        }
    });
}

function plusQuantity(productId) {
    var URL = "ShoppingCartServlet";
    var requestData = {
        productId: productId,
        method: "plus"
    };

    $.ajax({
        url: URL,
        type: "POST",
        data: requestData,
        success: function (response) {
            var currentQuantity = $("#cart-quantity-" + productId).text();
            var plusQuantity = parseInt(currentQuantity) + 1;
            $("#cart-quantity-" + productId).text(plusQuantity);
            $("#btn-minus-quantity-" + productId).removeAttr("disabled");
            getTotalCartPrice();
            displayTotalCartPrice();
        },
        error: function (jqXHR, status, error) {
            console.log(status);
        }
    });
}

function minusQuantity(productId) {
    var URL = "ShoppingCartServlet";
    var requestData = {
        productId: productId,
        method: "minus"
    };
    $.ajax({
        url: URL,
        type: "POST",
        data: requestData,
        success: function (response) {
            var currentQuantity = $("#cart-quantity-" + productId).text();
            var minusQuantity = parseInt(currentQuantity) - 1;
            $("#cart-quantity-" + productId).text(minusQuantity);
            if (minusQuantity === 1) {
                $("#btn-minus-quantity-" + productId).attr("disabled", "");
            }
            getTotalCartPrice();
            displayTotalCartPrice();
        },
        error: function (jqXHR, status, error) {
            console.log(status);
        }
    });
}