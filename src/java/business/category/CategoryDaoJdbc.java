package business.category;

import business.BookstoreDatabaseException.BookstoreQueryDatabaseException;
import business.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements CategoryDao {

    private static final String FIND_ALL_SQL =
            "SELECT category_id, category_name " +
                    "FROM category";

    private static final String FIND_BY_CATEGORY_ID_SQL =
            "SELECT category_id, category_name " +
                    "FROM category " +
                    "WHERE category_id = ?";

    private static final String FIND_BY_CATEGORY_NAME_SQL =
            "SELECT category_id, category_name " +
                    "FROM category " +
                    "WHERE category_name = ?";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category category = readCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered a problem finding all categories", e);
        }
        return categories;
    }

    @Override
    public Category findByCategoryId(long categoryId) {
        Category category = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = readCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered a problem finding category " + categoryId, e);
        }
        return category;
    }

    // Necessary because homepage search-box sends a category name as a parameter (instead of an id)
    @Override
    public Category findByCategoryName(String categoryName) {
        Category category = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_NAME_SQL)) {
            statement.setString(1, categoryName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = readCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered a problem finding category " + categoryName, e);
        }
        return category;
    }


    private Category readCategory(ResultSet resultSet) throws SQLException {
        Long categoryId = resultSet.getLong("category_id");
        String categoryName = resultSet.getString("category_name");
        return new Category(categoryId, categoryName);
    }

}
