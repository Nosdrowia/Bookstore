package business.order;

import java.sql.Connection;
import java.util.List;

/**
 * Created by gregory on 4/7/17.
 */
public interface OrderedBookDao {

    public void create(Connection connection, long customerOrderId, long bookId, int quantity);

    public List<OrderedBook> findByCustomerOrderId(long customerOrderId);
}
