package business.order;

import business.book.Book;
import business.customer.Customer;

import java.util.Collections;
import java.util.List;

public class CustomerOrderDetails {

    private CustomerOrder customerOrder;
    private Customer customer;
    private List<OrderedBook> orderedBooks;
    private List<Book> books;

    public CustomerOrderDetails(CustomerOrder customerOrder, Customer customer,
                                List<OrderedBook> orderedBooks, List<Book> books) {
        this.customerOrder = customerOrder;
        this.customer = customer;
        this.orderedBooks = orderedBooks;
        this.books = books;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<OrderedBook> getOrderedBooks() {
        return Collections.unmodifiableList(orderedBooks);
    }
}
