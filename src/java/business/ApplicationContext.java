
package business;

import business.book.BookDao;
import business.book.BookDaoJdbc;
import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.order.*;

public class ApplicationContext {

    private CategoryDao categoryDao;
    private BookDao bookDao;
    private DefaultCustomerOrderService customerOrderService;
    private CustomerOrderDetails customerOrderDetails;
    private CustomerDao customerDao;
    private CustomerOrderDao customerOrderDao;
    private OrderedBookDao orderedBookDao;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

        categoryDao = new CategoryDaoJdbc();
        bookDao = new BookDaoJdbc();
        customerDao = new CustomerDaoJdbc();
        customerOrderDao = new CustomerOrderDaoJdbc();
        orderedBookDao = new OrderedBookDaoJdbc();

        customerOrderService = new DefaultCustomerOrderService();
        DefaultCustomerOrderService service = (DefaultCustomerOrderService) customerOrderService;
        service.setBookDao(bookDao);
        service.setCustomerDao(customerDao);
        service.setCustomerOrderDao(customerOrderDao);
        service.setOrderedBookDao(orderedBookDao);
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }
    public BookDao getBookDao(){return bookDao;}
    public DefaultCustomerOrderService getOrderService(){return customerOrderService;}
    public CustomerDao getCustomerDao() {return customerDao;}
    public CustomerOrderDao getCustomerOrderDao() {return customerOrderDao;}
    public OrderedBookDao getOrderedBookDao() {return orderedBookDao;}
}
