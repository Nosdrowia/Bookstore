<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>--%>

<%--<sql:query var="books" dataSource="jdbc/josh_bookstore_db">
  SELECT * FROM book ORDER BY RAND() LIMIT 8
</sql:query>--%>

<section id="welcome">
  <header id="welcome-header">
    <h2>WELCOME</h2>
  </header>
  <div  id="welcome-message">
    <p>
      Welcome to Paper Wings, A place for all things aviation. We carry a wide-selection of books on
      different types of aircraft, aviation history, aircraft engineering, and more!!! If we don't have it here,
      we can get it, just ask.
    </p>
    <p>
      Please take a look around, and if you would like, send us a message to let us know what you think.
      Thanks for visiting!
    </p>
  </div>
</section>
<div id="section-separator">
  <div id="separator-img">
    <img src="${initParam.siteImages}planeSep.png" alt="Small airplane">
  </div>
</div>
<section id="popular-section">
  <header id="popular-header">
    <h3>POPULAR ITEMS</h3>
  </header>
  <div id="popular-gallery">
    <c:forEach var="bookRow" items="${books}">
      <c:set var="imgSrcHyphen" value="${fn:replace(bookRow.title,' ','-')}" />
      <c:set var="imgSrcColon" value="${fn:replace(imgSrcHyphen,':','')}" />
      <c:set var="imgSrc" value="${fn:toLowerCase(imgSrcColon)}" />

      <a href="category?categoryId=${bookRow.categoryId}"><div class="img" style="background-image: url(${initParam.bookImages}${imgSrc}.jpeg);"></div></a>

    </c:forEach>

  </div>
</section>