
package business.cart;

import business.book.Book;

public class ShoppingCartItem {

    private Book book;
    private int quantity;

    public ShoppingCartItem(Book book) {
        this.book = book;
        quantity = 1;
    }

    public long getBookId() {
        return book.getBookId();
    }

    public int getPrice() {
        return book.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return quantity * getPrice();
    }

    public Book getBook() {
        return book;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

