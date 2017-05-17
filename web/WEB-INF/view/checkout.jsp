<link rel="stylesheet" type="text/css" href="<c:url value='/css/checkout_page.css'/>">
<script src="<c:url value="/js/jquery-validation/dist/jquery.validate.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/jquery-validation/dist/additional-methods.js"/>" type="text/javascript"></script>


<div id="checkoutMain">
    <h2>Checkout</h2>
    <div id="checkoutFormBox">
        <div id="errors">
            <c:if test="${nameError}"><p class="frm-error">Name must not be empty.</p></c:if>
            <c:if test="${addressError}"><p class="frm-error">Address must not be empty.</p></c:if>
            <c:if test="${phoneError}"><p class="frm-error">Error with the phone number.</p></c:if>
            <c:if test="${emailError}"><p class="frm-error">Error with the email.</p></c:if>
            <c:if test="${ccNumberError}"><p class="frm-error">Error with the credit card number.</p></c:if>
            <c:if test="${ccDateError}"><p class="frm-error">Credit card date must be current month and year or later.</p></c:if>
        </div>
        <form id="checkoutForm" action="<c:url value='checkout'/>" method="post">
            <fieldset>
                <label for="frm-name">Name</label>
                <input id="frm-name" class="textField" type="text" size="20" name="name" value="${param.name}">
            </fieldset>
            <fieldset>
                <label for="frm-address">Address</label>
                <input id="frm-address" class="textField" type="text" size="20" name="address" value="${param.address}">
            </fieldset>
            <fieldset>
                <label for="frm-phone">Phone</label>
                <input id="frm-phone" class="textField" type="text" size="20" name="phone" value="${param.phone}">
            </fieldset>
            <fieldset>
                <label for="frm-email">Email</label>
                <input id="frm-email" class="textField" type="email" size="20" name="email" value="${param.email}">
            </fieldset>
            <fieldset>
                <label for="frm-credit">Credit card</label>
                <input id="frm-credit" class="textField" type="text" size="20" name="creditcard" value="${param.creditcard}">
            </fieldset>
            <fieldset>
                <label for="frm-expdate">Exp. date</label>
                <div id="frm-expdate">
                    <select class="selectMenu" name="ccmonth">
                        <c:set var="montharr" value="${['January','February','March','April','May','June','July','August','September','October','November','December']}"/>
                        <c:forEach begin="1" end="12" var="month">
                            <option value="${month}"
                                    <c:if test="${param.ccmonth eq month}">selected</c:if>>
                                    ${month}-${montharr[month - 1]}
                            </option>
                        </c:forEach>
                    </select>
                    <select class="selectMenu" name="ccyear" value="${ccyear}">
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>
                        <option value="2020">2020</option>
                        <option value="2021">2021</option>
                        <option value="2022">2022</option>
                        <option value="2023">2023</option>
                        <option value="2024">2024</option>
                        <option value="2025">2025</option>
                        <option value="2026">2026</option>
                        <option value="2027">2027</option>
                    </select>
                </div>
            </fieldset>
            <fieldset>
                <input id="boldSubmitButton" type="submit" value="Submit">
            </fieldset>
        </form>
    </div>

    <div id="checkoutInfo">
        <span id="checkoutInfoText">
            Your credit card will be charged <strong><fmt:formatNumber value="${cart.total/100}" type="currency"/></strong><br>
            ( <fmt:formatNumber value="${cart.subtotal/100}" type="currency"/> + <fmt:formatNumber value="${initParam.surcharge/100}" type="currency"/>  shipping )
        </span>
    </div>

</div>

<script>
    $(document).ready(function(){
        $('#checkoutForm').validate({
            rules:{
                name:"required",
                address:"required",
                phone:{
                    required:true,
                    phoneUS:true
                },
                email:{
                    required:true,
                    email:true
                },
                creditcard:{
                    required:true,
                    creditcard:true
                },
                ccmonth:"required",
                ccyear:"required"
            }
        });
    });

</script>
