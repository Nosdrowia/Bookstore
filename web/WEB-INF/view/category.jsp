<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>--%>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/category_page.css'/>">

<section id="category-section">
  <div id="category-menu">
    <ul>

      <c:forEach var="categoryRow" items="${categories}">
        <c:choose>
          <c:when test="${categoryRow.categoryName == param.category || categoryRow.categoryId == books.get(0).getCategoryId()}">
            <li><a class="active" href="#">${categoryRow.categoryName}</a></li>
          </c:when>
          <c:otherwise>
            <li><a href="category?category=${categoryRow.categoryName}">${categoryRow.categoryName}</a></li>
        </c:otherwise>
      </c:choose>
      </c:forEach>

    </ul>
  </div>
</section>
<section id="book-section">

  <c:forEach var="bookRow" items="${books}">
    <c:set var="imgSrcHyphen" value="${fn:replace(bookRow.title,' ','-')}" />
    <c:set var="imgSrcColon" value="${fn:replace(imgSrcHyphen,':','')}" />
    <c:set var="imgSrc" value="${fn:toLowerCase(imgSrcColon)}" />

    <div class="card">
      <div id="book-image" style="background-image: url('${initParam.bookImages}${imgSrc}.jpeg')"></div>
        <%--<img src="${initParam.bookImages}${imgSrc}.jpeg">--%>
      <div class="book-info-container">
        <h4>${bookRow.title}</h4>
        <p>Author:  <span class="author-name">${bookRow.author}</span></p>
        <p class="book-price">Price: <fmt:formatNumber value="${bookRow.price /100}" type="currency"></fmt:formatNumber></p>
        <p>
          <form name="addToCart" action="addToCart" method="post">
            <input type="hidden" name="bookId" value="${bookRow.bookId}" />
            <button class="add-to-cart" type="submit">Add to Cart</button>
          </form>
        </p>

        <c:if test="${bookRow.isPublic}">
          <p>
            <a href="#">Read Now</a>
          </p>
        </c:if>
      </div>
    </div>
  </c:forEach>

</section>