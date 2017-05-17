package controller;

import business.ApplicationContext;
import business.book.Book;
import business.book.BookDao;
import business.category.CategoryDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Homepage",
        urlPatterns = {"/home"})
public class HomepageServlet extends BookstoreServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    private BookDao bookDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        ApplicationContext applicationContext = ApplicationContext.INSTANCE;

        bookDao = applicationContext.INSTANCE.getBookDao();

    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        List<Book> books = bookDao.getRandomBooks();
        request.setAttribute("books",books);

        // use RequestDispatcher to forward request internally
        forwardToJsp(request, response, "/homepage");
    }
}