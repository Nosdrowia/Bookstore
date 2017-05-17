package controller;

import business.ApplicationContext;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.category.Category;
import business.category.CategoryDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Cart",
        urlPatterns = {"/cart","/increment","/decrement"})
public class CartServlet extends BookstoreServlet {

    private BookDao bookDao;
    private CategoryDao categoryDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        bookDao = applicationContext.getBookDao();
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

        // use RequestDispatcher to forward request internally
        forwardToJsp(request, response, "/cart");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        switch (userPath){
            case "/increment":
            {
                String bookId = request.getParameter("bookId");
                if(!bookId.isEmpty()){
                    Book book = bookDao.findByBookId(Integer.parseInt(bookId));
                    cart.increment(book);
                }

                break;
            }
            case "/decrement":
            {
                String bookId = request.getParameter("bookId");
                if(!bookId.isEmpty()){
                    Book book = bookDao.findByBookId(Integer.parseInt(bookId));
                    cart.decrement(book);
                }
                break;
            }
            default:
                break;
        }


        forwardToJsp(request,response,"/cart");
    }
}
