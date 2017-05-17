<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>--%>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/cart_page.css'/>">

<section id="cart-summary">

  <c:choose>
    <c:when test="${empty cart or cart.numberOfItems == 0}">
      <h2>Your cart is empty!</h2>
      <a href="category" id="continue" type="button">Continue Shopping</a>
    </c:when>
    <c:otherwise>
      <h2>Shopping Cart: ${(empty cart) ? 0:cart.numberOfItems} items</h2>
      <a class="button" href="category?category=${selectedCategory}" id="continue">Continue Shopping</a>
      <a class="button" href="checkout" id="checkout">Checkout</a>
    </c:otherwise>
  </c:choose>

</section>

<section id="cart-table-section">
  <table id="cart-table">

    <c:if test="${!(empty cart) and cart.numberOfItems >= 1}">

      <h3>Subtotal: <fmt:formatNumber value="${cart.subtotal/100}" type="currency"/></h3>


      <c:forEach var="item" items="${cart.items}">
        <c:set var="imgSrcHyphen" value="${fn:replace(item.book.title,' ','-')}" />
        <c:set var="imgSrcColon" value="${fn:replace(imgSrcHyphen,':','')}" />
        <c:set var="imgSrc" value="${fn:toLowerCase(imgSrcColon)}" />
        <tr>
          <td><img src="${initParam.bookImages}${imgSrc}.jpeg"></td>
          <td>Price: <fmt:formatNumber value="${item.price/100}" type="currency"/></td>
          <td>Qty:${item.quantity}</td>
          <td id="qtyBtns">
            <form action="decrement" method="post">
              <input type="hidden" name="bookId" value="${item.book.bookId}">
              <input class="qtyButton" type="image" src="img/site_images/decrement.png"/>
            </form>
            <form action="increment" method="post">
              <input type="hidden" name="bookId" value="${item.book.bookId}">
              <input class="qtyButton" type="image" src="img/site_images/increment.png"/>
            </form>
          </td>
        </tr>

      </c:forEach>
    </c:if>

  </table>
</section>
