<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/confirmation_page.css'/>">

<section id="confirmation-section">
    <div id="confirmation-title-date">
        <div class="center"><h2>Confirmation: ${orderDetails.customerOrder.confirmationNumber}</h2></div>
        <p><fmt:formatDate type="both" timeStyle="long" value="${orderDetails.customerOrder.dateCreated}"/></p>
    </div>
    <table>
        <tr>
            <th>Book</th>
            <th>Quantity</th>
            <th>Price</th>
        </tr>

        <c:forEach var="book" items="${orderDetails.orderedBooks}" varStatus="iter">
            <tr>
                <td>${orderDetails.books[iter.index].title}</td>
                <td>${book.quantity}</td>
                <td><fmt:formatNumber value="${orderDetails.books[iter.index].price/100}" type="currency"/></td>
            </tr>
        </c:forEach>
            <tr>
                <td>-Delivery surcharge--</td>
                <td></td>
                <td><fmt:formatNumber value="${initParam.surcharge/100}" type="currency"/></td>
            </tr>
            <tr>
                <td><h3>Total</h3></td>
                <td></td>
                <td><fmt:formatNumber value="${orderDetails.customerOrder.amount/100}" type="currency"/></td>
            </tr>

    </table>

    <div id="customer-information">
        <h2>Customer Information</h2>
        <p>Name: ${orderDetails.customer.customerName}</p>
        <p>Email: ${orderDetails.customer.email}</p>
        <p>Address: ${orderDetails.customer.address}</p>
        <p>CC Number: ************${fn:substring(orderDetails.customer.ccNumber,12 ,16)} Exp: <fmt:formatDate value="${orderDetails.customer.ccExpDate}" pattern="MMM-yyyy"/></p>
    </div>
</section>
