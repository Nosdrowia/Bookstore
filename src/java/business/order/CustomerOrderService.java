package business.order;

import business.cart.ShoppingCart;

import java.util.Date;

public interface CustomerOrderService {

    long placeOrder(String name, String address, String phone, String email, String ccNumber,
                    Date ccExpDate, ShoppingCart cart);

    CustomerOrderDetails getOrderDetails(long customerOrderId);

}
