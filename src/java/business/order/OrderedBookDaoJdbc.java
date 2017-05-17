package business.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static business.JdbcUtils.getConnection;
import business.BookstoreDatabaseException.BookstoreQueryDatabaseException;
import business.BookstoreDatabaseException.BookstoreUpdateDatabaseException;

public class OrderedBookDaoJdbc implements OrderedBookDao {

    private static final String CREATE_LINE_ITEM_SQL =
            "INSERT INTO ordered_book (book_id, customer_order_id, quantity) " +
                    "VALUES (?, ?, ?)";

    private static final String FIND_BY_CUSTOMER_ORDER_ID_SQL =
            "SELECT book_id, customer_order_id, quantity " +
                    "FROM ordered_book WHERE customer_order_id = ?";

    @Override
    public void create(Connection connection, long customerOrderId, long bookId, int quantity) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_LINE_ITEM_SQL)) {
            statement.setLong(1, bookId);
            statement.setLong(2, customerOrderId);
            statement.setInt(3, quantity);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new BookstoreUpdateDatabaseException("Failed to insert an order line item, affected row count = " + affected);
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered problem creating a new ordered book ", e);
        }
    }

    @Override
    public List<OrderedBook> findByCustomerOrderId(long customerOrderId) {
        List<OrderedBook> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ORDER_ID_SQL)) {
            statement.setLong(1, customerOrderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(readOrderedBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered problem finding ordered books for customer order "
                    + customerOrderId, e);
        }
        return result;
    }

    private OrderedBook readOrderedBook(ResultSet resultSet) throws SQLException {
        long bookId = resultSet.getLong("book_id");
        long customerOrderId = resultSet.getLong("customer_order_id");
        int quantity = resultSet.getInt("quantity");
        return new OrderedBook(bookId, customerOrderId, quantity);
    }
}
