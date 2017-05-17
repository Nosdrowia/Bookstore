package business;

public class BookstoreDatabaseException extends RuntimeException {

    public BookstoreDatabaseException(String message) {
        super(message);
    }

    public BookstoreDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class BookstoreConnectionDatabaseException extends BookstoreDatabaseException {
        public BookstoreConnectionDatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class BookstoreQueryDatabaseException extends BookstoreDatabaseException {
        public BookstoreQueryDatabaseException(String message) {
            super(message);
        }

        public BookstoreQueryDatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class BookstoreUpdateDatabaseException extends BookstoreDatabaseException {
        public BookstoreUpdateDatabaseException(String message) {
            super(message);
        }

        public BookstoreUpdateDatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
