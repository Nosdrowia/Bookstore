package controller;

import business.ApplicationContext;
import business.Validator;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.category.Category;
import business.category.CategoryDao;
import business.order.CustomerOrderDetails;
import business.order.CustomerOrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "Checkout", urlPatterns = {"/checkout"})
@ServletSecurity(@HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class CheckoutServlet extends BookstoreServlet {

    private CustomerOrderService orderService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        orderService = ApplicationContext.INSTANCE.getOrderService();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if(cart == null || cart.getNumberOfItems() == 0){
            userPath = "/home";
        }
        else{
            cart.calculateTotal(request.getServletContext().getInitParameter("surcharge"));
        }




        // use RequestDispatcher to forward request internally
        forwardToJsp(request, response, userPath);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Validator validator = new Validator();

        if (cart == null) {
            forwardToJsp(request, response, userPath);
            return;
        }

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String ccNumber = request.getParameter("creditcard");
        String ccMonth = request.getParameter("ccmonth");
        String ccYear = request.getParameter("ccyear");

        Date ccDate = getCreditcardDate(ccMonth, ccYear);

        boolean validationError = validator.validateForm(name, address, phone, email,
                ccNumber, ccDate, request);
        if (validationError) {
            request.setAttribute("validationErrorFlag", true);
            forwardToJsp(request, response, userPath);
            return;
        }

        long orderId = orderService.placeOrder(name, address, phone, email, ccNumber, ccDate, cart);

        if (orderId == 0) {
            request.setAttribute("transactionErrorFlag", true);
            forwardToJsp(request, response, "/checkout");
            return;
        } else {
            forgetSession(request.getSession());
            CustomerOrderDetails orderDetails = orderService.getOrderDetails(orderId);
            request.getSession().setAttribute("orderDetails", orderDetails);
            response.sendRedirect(request.getContextPath() + "/confirmation");
            return;
        }

    }

    private void forgetSession(HttpSession session) {
        session.setAttribute("cart", null); // wipe out cart explicitly
        // wipe out all other session attributes
        final Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            session.setAttribute(attributeNames.nextElement(), null);
        }
    }

    private Date getCreditcardDate(String monthString, String yearString) {
        Integer month = Integer.parseInt(monthString) - 1;
        Integer year = Integer.parseInt(yearString);

        Calendar ccDate = Calendar.getInstance();
        ccDate.clear();
        ccDate.set(Calendar.YEAR,year);
        ccDate.set(Calendar.MONTH,month);

        Date date = ccDate.getTime();

        return date;
    }

}
