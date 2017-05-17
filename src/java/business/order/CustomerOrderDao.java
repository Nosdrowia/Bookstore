package business.order;

import java.sql.Connection;
import java.util.List;

public interface CustomerOrderDao {

    public long create(Connection connection, int amount, int confirmationNumber, long customerId);

    public List<CustomerOrder> findAll();

    public CustomerOrder findByCustomerOrderId(long customerOrderId);

    public List<CustomerOrder> findByCustomerId(long customerId);
}
