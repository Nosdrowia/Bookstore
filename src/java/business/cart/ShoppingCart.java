
package business.cart;

import business.book.Book;
import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {

    List<ShoppingCartItem> items;
    int numberOfItems;
    int total;

    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>'s
     * <code>items</code> list. If item of the specified <code>book</code>
     * already exists in shopping cart list, the quantity of that item is
     * incremented, and the original price remains unchanged.
     *
     * @see ShoppingCartItem
     */
    public synchronized void addItem(Book book) {

        boolean newItem = true;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getBookId() == book.getBookId()) {

                newItem = false;
                scItem.setQuantity(scItem.getQuantity() + 1);
            }
        }

        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(book);
            items.add(scItem);
        }
    }

    /**
     * Updates the <code>ShoppingCartItem</code> of the specified
     * <code>book</code> to the specified quantity. If '<code>0</code>'
     * is the given quantity, the <code>ShoppingCartItem</code> is removed
     * from the <code>ShoppingCart</code>'s <code>items</code> list.
     *
     * @param quantity the number which the <code>ShoppingCartItem</code> is updated to
     * @see ShoppingCartItem
     */
    public synchronized void update(Book book , int quantity) {

        ShoppingCartItem item = null;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getBookId() == book.getBookId()) {

                if (quantity != 0) {
                    // set item quantity to new value
                    scItem.setQuantity(quantity);
                } else {
                    // if quantity equals 0, save item and break
                    item = scItem;
                    break;
                }
            }
        }

        if (item != null) {
            // remove from cart
            items.remove(item);
        }
    }

    public synchronized void increment(Book book){
        for(ShoppingCartItem sciItem : items){
            if(sciItem.getBookId() == book.getBookId()){
                int newQuantity = sciItem.getQuantity() + 1;

                if(newQuantity < 100){
                    sciItem.setQuantity(newQuantity);
                }
            }
        }
    }

    public synchronized void decrement(Book book){
        ShoppingCartItem item = null;

        for(ShoppingCartItem sciItem : items) {
            if(sciItem.getBookId() == book.getBookId()){
                int newQuantity = sciItem.getQuantity() - 1;

                if(newQuantity == 0){
                    item = sciItem;
                    break;
                }
                else{
                    sciItem.setQuantity(newQuantity);
                }
            }
        }

        if(item != null){
            items.remove(item);
        }
    }

    /**
     * Returns the list of <code>ShoppingCartItems</code>.
     *
     * @return the <code>items</code> list
     * @see ShoppingCartItem
     */
    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    /**
     * Returns the sum of quantities for all items maintained in shopping cart
     * <code>items</code> list.
     *
     * @return the number of items in shopping cart
     * @see ShoppingCartItem
     */
    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (ShoppingCartItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

    /**
     * Returns the sum of the book price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost excluding the surcharge.
     *
     * @return the cost of all items times their quantities
     * @see ShoppingCartItem
     */
    public synchronized int getSubtotal() {

        int amount = 0;

        for (ShoppingCartItem scItem : items) {

            amount += (scItem.getQuantity() * scItem.getPrice());
        }

        return amount;
    }

    /**
     * Calculates the total cost of the order. This method adds the subtotal to
     * the designated surcharge and sets the <code>total</code> instance variable
     * with the result.
     *
     * @param surcharge the designated surcharge for all orders
     * @see ShoppingCartItem
     */
    public synchronized void calculateTotal(String surcharge) {

        int amount = 0;

        amount = this.getSubtotal();
        amount +=  Integer.parseInt(surcharge);

        total = amount;
    }

    /**
     * Returns the total cost of the order for the given
     * <code>ShoppingCart</code> instance.
     *
     * @return the cost of all items times their quantities plus surcharge
     */
    public synchronized int getTotal() {

        return total;
    }

    /**
     * Empties the shopping cart. All items are removed from the shopping cart
     * <code>items</code> list, <code>numberOfItems</code> and
     * <code>total</code> are reset to '<code>0</code>'.
     *
     * @see ShoppingCartItem
     */
    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}
