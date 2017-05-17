package business.book;

import business.BookstoreDatabaseException;
import business.JdbcUtils;
import business.category.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.BookstoreDatabaseException.BookstoreQueryDatabaseException;

public class BookDaoJdbc implements BookDao {

    private static final String FIND_BY_BOOK_ID_SQL =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "WHERE book_id = ?";

    private static final String FIND_BY_CATEGORY_ID_SQL =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "WHERE category_id = ?";

    private static final String GET_RANDOM_BOOKS =
            "SELECT book_id, title, author, price, is_public, category_id " +
                    "FROM book " +
                    "ORDER BY RAND() LIMIT 8";

    @Override
    public Book findByBookId(long bookId) {
        Book book = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_BOOK_ID_SQL)) {
            statement.setLong(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    book = readBook(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered a problem finding book " + bookId, e);
        }
        return book;
    }

    @Override
    public List<Book> findByCategoryId(long categoryId) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = readBook(resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered a problem finding category " + categoryId, e);
        }
        return books;
	}

	@Override
    public List<Book> getRandomBooks(){
        List<Book> books = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_RANDOM_BOOKS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = readBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDatabaseException("Encountered a problem finding all categories", e);
        }
        return books;
    }

    private Book readBook(ResultSet resultSet) throws SQLException {
        Long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        Integer price = resultSet.getInt("price");
        Boolean isPublic = resultSet.getBoolean("is_public");
        Long categoryId = resultSet.getLong("category_id");
        return new Book(bookId, title, author, price, isPublic, categoryId);
    }

}
