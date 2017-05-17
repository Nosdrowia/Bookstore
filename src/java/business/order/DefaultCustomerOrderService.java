package business.order;

import business.BookstoreDatabaseException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultCustomerOrderService implements CustomerOrderService {

    private CustomerOrderDao customerOrderDao;
    private OrderedBookDao orderedBookDao;
    private CustomerDao customerDao;
    private BookDao bookDao;
    private Random random = new Random();

    @Override
    public long placeOrder(String customerName, String address, String phone, String email, String ccNumber,
                           Date ccExpDate, ShoppingCart cart) {

        try (Connection connection = JdbcUtils.getConnection()) {
            return performPlaceOrderTransaction(customerName, address, phone, email,  ccNumber, ccExpDate, cart, connection);
        } catch (SQLException e) {
            throw new BookstoreDatabaseException("Error during close connection for customer order", e);
        }
    }

    @Override
    public CustomerOrderDetails getOrderDetails(long customerOrderId) {
        CustomerOrder order = customerOrderDao.findByCustomerOrderId(customerOrderId);
        Customer customer = customerDao.findByCustomerId(order.getCustomerId());
        List<OrderedBook> orderedBooks = orderedBookDao.findByCustomerOrderId(customerOrderId);
        List<Book> books = orderedBooks
                .stream()
                .map(orderedBook -> bookDao.findByBookId(orderedBook.getBookId()))
                .collect(Collectors.toList());

        return new CustomerOrderDetails(order, customer, orderedBooks, books);
    }

    private long performPlaceOrderTransaction(String customerName, String address, String phone, String email,
                                              String ccNumber, Date ccExpDate, ShoppingCart cart,
                                              Connection connection) {
        try {
            connection.setAutoCommit(false);

            long customerId = customerDao.create(connection, customerName, address, phone, email, ccNumber, ccExpDate);
            long customerOrderId = customerOrderDao.create(connection, cart.getTotal(), generateConfirmationNumber(), customerId);
            for (ShoppingCartItem item : cart.getItems()) {
                orderedBookDao.create(connection, customerOrderId, item.getBookId(), item.getQuantity());
            }
            connection.commit();
            return customerOrderId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new BookstoreDatabaseException("Failed to roll back transaction", e1);
            }
            return 0;
        }
    }

    private int generateConfirmationNumber() {
        return random.nextInt(999999999);
    }

    public void setCustomerOrderDao(CustomerOrderDao customerOrderDao) {
        this.customerOrderDao = customerOrderDao;
    }

    public void setOrderedBookDao(OrderedBookDao orderedBookDao) {
        this.orderedBookDao = orderedBookDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

}
