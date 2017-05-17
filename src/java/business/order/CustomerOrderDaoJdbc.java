package business.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import business.BookstoreDatabaseException.BookstoreQueryDatabaseException;
import business.BookstoreDatabaseException.BookstoreUpdateDatabaseException;

import static business.JdbcUtils.getConnection;

public class CustomerOrderDaoJdbc implements CustomerOrderDao {

    private static final String CREATE_ORDER_SQL =
            "INSERT INTO customer_order (amount, confirmation_number, customer_id) " +
                    "VALUES (?, ?, ?)";

    private static final String FIND_ALL_SQL =
            "SELECT customer_order_id, customer_id, amount, date_created, confirmation_number " +
                    "FROM customer_order";

    private static final String FIND_BY_CUSTOMER_ID_SQL =
            "SELECT customer_order_id, customer_id, amount, date_created, confirmation_number " +
                    "FROM customer_order WHERE customer_id = ?";

    private static final String FIND_BY_CUSTOMER_ORDER_ID_SQL =
            "SELECT customer_order_id, customer_id, amount, date_created, confirmation_number " +
                    "FROM customer_order WHERE customer_order_id = ?";

    @Override
    public long create(Connection connection, int amount, int confirmationNumber, long customerId) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, amount);
            statement.setInt(2, confirmationNumber);
            statement.setLong(3, customerId);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new BookstoreUpdateDatabaseException("Failed to insert an order, affected row count = " + affected);
            }
            long customerOrderId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customerOrderId = rs.getLong(1);
            } else {
                throw new BookstoreQueryDatabaseException("Failed to retrieve customerOrderId auto-generated key");
            }
            return customerOrderId;
        } catch (SQLException e) {
            throw new BookstoreUpdateDatabaseException("Encountered problem creating a new customer ", e);
        }
    }

    @Override
    public List<CustomerOrder> findAll() {
        List<CustomerOrder> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                CustomerOrder customerOrder = readCustomerOrder(resultSet);
                result.add(customerOrder);
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered problem finding all orders", e);
        }
        return result;
    }

    @Override
    public CustomerOrder findByCustomerOrderId(long customerOrderId) {
        CustomerOrder result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ORDER_ID_SQL)) {
            statement.setLong(1, customerOrderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readCustomerOrder(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered problem finding customer order id = " + customerOrderId, e);
        }
        return result;
    }

    @Override
    public List<CustomerOrder> findByCustomerId(long customerId) {
        List<CustomerOrder> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ID_SQL)) {
            statement.setLong(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CustomerOrder customerOrder = readCustomerOrder(resultSet);
                    result.add(customerOrder);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered problem finding customer id = " + customerId, e);
        }
        return result;
    }

    private CustomerOrder readCustomerOrder(ResultSet resultSet) throws SQLException {
        Long customerOrderId = resultSet.getLong("customer_order_id");
        int amount = resultSet.getInt("amount");
        Date dateCreated = resultSet.getTimestamp("date_created");
        int confirmationNumber = resultSet.getInt("confirmation_number");
        Long customerId = resultSet.getLong("customer_id");
        return new CustomerOrder(customerOrderId, amount, dateCreated, confirmationNumber, customerId);
    }

}
