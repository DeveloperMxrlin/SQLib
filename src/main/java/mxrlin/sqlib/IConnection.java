package mxrlin.sqlib;

import mxrlin.sqlib.command.get.GetEntryCommand;
import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.command.set.*;
import mxrlin.sqlib.exception.SQLibException;
import mxrlin.sqlib.misc.RowData;
import mxrlin.sqlib.misc.Table;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface IConnection extends AutoCloseable {

    /**
     * Executes an Update to the Database
     * @param command The Command such as {@link CreateTableCommand}, {@link UpdateEntryCommand}, {@link InsertColumnCommand} etc.
     * @return Returns true if the process was successfully done
     * @throws SQLibException Thrown when the update failed
     * @see UpdatingCommand
     */
    boolean executeUpdate(UpdatingCommand command) throws SQLibException;

    /**
     * Get something from the database with a Query Command
     * @param command The Command such as {@link GetEntryCommand}
     * @return Returns a {@link ResultSet}
     * @throws SQLibException Thrown when something went wrong while getting data from the database
     * @see QueryCommand
     */
    ResultSet executeQuery(QueryCommand command) throws SQLibException;

    /**
     * Get the ongoing database connection.
     *
     * Null if connection is closed
     *
     * @return Returns the database connection
     * @see Connection
     */
    Connection getConnection();

    /**
     * Open a new database connection, if you closed it earlier.
     * @throws SQLibException Thrown when the connection is still open, or it failed to connect to the database
     */
    void open() throws SQLibException;

    /**
     * Check if the database connection is still there
     * @return true = database connection still there
     */
    boolean isOpen();

    /**
     * Check if the class is committing automatically to the database
     * @return true = auto commit is on
     * @throws SQLibException Thrown when something went wrong while checking if auto commit is on/off.
     */
    boolean isAutoCommitting() throws SQLibException;

    /**
     * Change the Auto Commit Parameter
     * @param bool true = auto commit on / false = auto commit off
     * @throws SQLibException Thrown when something went wrong while changing the parameter
     */
    void setAutoCommit(boolean bool) throws SQLibException;

    /**
     * Commit manually to the database
     * @throws SQLibException Thrown when it couldn't commit to the database
     */
    void commit() throws SQLibException;

    /**
     * Rollback to the last commit of the database
     * @throws SQLibException Thrown when it couldn't roll back.
     */
    void rollback() throws SQLibException;

    /**
     * Create a new table in the selected database
     * @param table The new Table with all its needed information
     * @param createIfNotExists If true, the table only creates if it doesn't already exist.
     * @return Returns if the process was successful
     * @throws SQLibException Thrown when something failed while updating.
     * @see mxrlin.sqlib.misc.Table
     */
    default boolean createTable(Table table, boolean createIfNotExists) throws SQLibException{
        return executeUpdate(new CreateTableCommand(table, createIfNotExists));
    }

    /**
     * Delete a table with the name {@param tableName}
     * @param tableName The name of the table
     * @return Returns if the process was successful
     * @throws SQLibException Thrown when something failed while updating.
     */
    default boolean deleteTable(String tableName) throws SQLibException {
        return executeUpdate(new DeleteTableCommand(tableName));
    }

    /**
     * Insert a column into a table named {@param tableName}
     * @param tableName The name of the table
     * @param data The data that should be inserted into the column
     * @return Returns if the process was successful
     * @throws SQLibException Thrown when something failed while updating.
     */
    default boolean insertColumn(String tableName, List<RowData> data) throws SQLibException {
        return executeUpdate(new InsertColumnCommand(tableName, data));
    }

    /**
     * Delete a column out of a table
     * @param tableName The name of the table
     * @param data Data of some or all rows.
     * @return Returns if the process was successful
     * @throws SQLibException Thrown when something failed while updating.
     */
    default boolean deleteColumn(String tableName, List<RowData> data) throws SQLibException {
        return executeUpdate(new DeleteColumnCommand(tableName, data));
    }

    /**
     * Updates an Entry in a table
     * @param tableName The table name
     * @param updatingRows The Row(s) that is/are getting updated
     * @param updateAt The Row(s) to specify which column should be updated
     * @return Returns if the process was successful
     * @throws SQLibException Thrown when something failed while updating.
     */
    default boolean updateEntry(String tableName, List<RowData> updatingRows, List<RowData> updateAt) throws SQLibException {
        return executeUpdate(new UpdateEntryCommand(tableName, updatingRows, updateAt));
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as an Object
     * @throws SQLibException Thrown when it couldn't get the result set or get it as an Object.
     */
    default Object getObjectEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getObject(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as object.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a String
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a String.
     */
    default String getStringEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getString(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as String.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Integer
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Integer.
     */
    default Integer getIntegerEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getInt(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Integer.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as an Array
     * @throws SQLibException Thrown when it couldn't get the result set or get it as an Array.
     */
    default Array getArrayEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getArray(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Array.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as an Ascii Stream
     * @throws SQLibException Thrown when it couldn't get the result set or get it as an Ascii Stream
     */
    default InputStream getAsciiStreamEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getAsciiStream(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Ascii Stream.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Binary Stream
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Binary Stream
     */
    default InputStream getBinaryStreamEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getBinaryStream(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Binary Stream.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Character Stream
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Character Stream
     */
    default Reader getCharacterStreamEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getCharacterStream(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Character Stream.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a NCharaterStream
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a NCharacterStream.
     */
    default Reader getNCharacterStreamEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getNCharacterStream(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as NCharacter Stream.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a BigDecimal
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a BigDecimal.
     */
    default BigDecimal getBigDecimalEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getBigDecimal(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Big Decimal.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Blob
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Blob.
     */
    default Blob getBlobEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getBlob(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Blob.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Clob
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Clob.
     */
    default Clob getClobEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getClob(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Clob.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Byte
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Byte.
     */
    default Byte getByteEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getByte(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Byte.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Byte Array
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Byte Array.
     */
    default byte[] getByteArrayEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getBytes(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Byte Array.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Date
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Date.
     */
    default Date getDateEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getDate(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Date.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Double
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Double.
     */
    default Double getDouble(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getDouble(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Double.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Float
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Float.
     */
    default Float getFloatEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getFloat(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Float.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Long
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Long.
     */
    default Long getLongEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getLong(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Long.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Short
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Short.
     */
    default Short getShortEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getShort(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Short.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a SQLXML
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a SQLXML.
     */
    default SQLXML getXMLEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getSQLXML(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as SQLXML.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Time Object
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Time Object.
     */
    default Time getTimeEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getTime(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Time.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a Timestamp
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a Timestamp.
     */
    default Timestamp getTimestampEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getTimestamp(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as Timestamp.");
        }
    }

    /**
     * Get an Entry from the row {@param key} in the table {@param tableName}
     * @param tableName The name of the table
     * @param key The Row the entry is saved in
     * @param atRow The row as reference point
     * @return Returns the Entry as a URL
     * @throws SQLibException Thrown when it couldn't get the result set or get it as a URL.
     */
    default URL getURLEntry(String tableName, String key, RowData atRow) throws SQLibException {
        try (ResultSet set = executeQuery(new GetEntryCommand(tableName, key, atRow))){
            return set.getURL(key);
        } catch (SQLException e) {
            throw new SQLibException("Couldn't fetch the result set as URL.");
        }
    }

}
