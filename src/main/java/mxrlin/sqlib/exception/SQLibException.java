package mxrlin.sqlib.exception;

import java.sql.SQLException;

/**
 * Part of the SQLib API
 *
 * Exception that is thrown everytime something went wrong while the library is working.
 */
public class SQLibException extends Exception {

    // the exception the reason that exception was thrown
    private Exception before;

    /**
     * Throw a new Exception that isn't thrown because of another exception
     * @param reason The Reason the Exception was thrown
     */
    public SQLibException(String reason){
        this(reason, null);
    }

    /**
     * Throw a SQLibException that is thrown because of another exception specified at {@param before}
     * @param reason The Reason the Exception was thrown
     * @param before The Exception that was the reason this Exception was thrown
     */
    public SQLibException(String reason, Exception before){
        super(reason + (before != null ? " (Exception thrown by other exception [" + before.getClass().getName() + "])" : "") + ": " + before.getMessage());
        this.before = before;
    }

    /**
     * @return The Exception that was the reason this Exception was thrown
     */
    public Exception getExceptionThrown() {
        return before;
    }
}
