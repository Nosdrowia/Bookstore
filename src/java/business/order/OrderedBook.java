package business.order;

public class OrderedBook {

    private long bookId;
    private long customerOrderId;
    private int quantity;

    public OrderedBook(long bookId, long customerOrderId, int quantity) {
        this.bookId = bookId;
        this.customerOrderId = customerOrderId;
        this.quantity = quantity;
    }

    public long getBookId() {
        return bookId;
    }

    public long getCustomerOrderId() {
        return customerOrderId;
    }

    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "OrderedBook{" +
                "bookId=" + bookId +
                ", customerOrderId=" + customerOrderId +
                ", quantity=" + quantity +
                '}';
    }
}