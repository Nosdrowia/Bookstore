package controller;

import business.ApplicationContext;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.category.Category;
import business.category.CategoryDao;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Category",
        urlPatterns = {"/category","/addToCart"})
public class CategoryServlet extends BookstoreServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private CategoryDao categoryDao;
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
        categoryDao = applicationContext.INSTANCE.getCategoryDao();

    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // use this if you have a search bar in your header
        String categoryName = request.getParameter("category");

        String categoryId_Param = request.getParameter("categoryId");

        if(!StringUtils.isNullOrEmpty(categoryId_Param)) {
            Long categoryId = Long.parseLong(categoryId_Param);
            Category name = categoryDao.findByCategoryId(categoryId);
            categoryName = name.getCategoryName();
        }

        session.setAttribute("categoryName", categoryName);

        List<Category> categories = categoryDao.findAll();
        session.setAttribute("categories",categories);

        Long category;
        Category selectedCategory = categoryDao.findByCategoryName(categoryName);
        if(selectedCategory == null){
            selectedCategory = categoryDao.findByCategoryId(1);
        }
        category = selectedCategory.getCategoryId();
        List<Book> books = bookDao.findByCategoryId(category);
        session.setAttribute("books",books);

        session.setAttribute("selectedCategory",selectedCategory.getCategoryName());

        // use RequestDispatcher to forward request internally
        forwardToJsp(request, response, "/category");

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if("/addToCart".equals(userPath)){
            if(cart == null){
                cart = new ShoppingCart();
                session.setAttribute("cart",cart);
            }

            String bookId = request.getParameter("bookId");
            if(!bookId.isEmpty()){
                Book book = bookDao.findByBookId(Integer.parseInt(bookId));
                cart.addItem(book);
            }

            userPath = "/category";
        }


        forwardToJsp(request,response,userPath);
    }
}