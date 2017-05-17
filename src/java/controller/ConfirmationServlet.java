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
import business.order.OrderedBook;
import business.order.OrderedBookDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
import java.util.List;

/**
 * Created by josh on 4/28/17.
 */
@WebServlet(name = "Confirmation",
        urlPatterns = {"/confirmation"})
public class ConfirmationServlet extends BookstoreServlet{

    private CustomerOrderService orderedBooks;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
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


        // use RequestDispatcher to forward request internally
        forwardToJsp(request, response, userPath);
    }

}
