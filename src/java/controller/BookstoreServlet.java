package controller;

import business.ApplicationContext;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.category.CategoryDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Bookstore",
        loadOnStartup = 1)
public class BookstoreServlet extends HttpServlet {

    protected void forwardToJsp(HttpServletRequest request,
                                HttpServletResponse response, String userPath) {

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void doTemporaryRedirect(HttpServletRequest request, HttpServletResponse response, String location) {
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", getServletContext().getContextPath() + location);
    }
}