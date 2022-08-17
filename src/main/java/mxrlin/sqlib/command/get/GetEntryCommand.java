package mxrlin.sqlib.command.get;

import mxrlin.sqlib.command.CommandBuilder;
import mxrlin.sqlib.misc.MySQLStatement;
import mxrlin.sqlib.misc.RowData;

/**
 * Part of the SQLib API
 *
 * Get an Entry out of a Table.
 *
 * 1. Create a new instance of {@link mxrlin.sqlib.SQLibConnection} and create a MySQL-Connection
 * 2. Create a new instance of this class.
 * 3. Use the {@link mxrlin.sqlib.SQLibConnection#executeQuery(QueryCommand)} method to get an {@link java.sql.ResultSet}.
 * 4. Use the {@link java.sql.ResultSet} methods to get a string, an integer, a short etc.
 *
 * @see mxrlin.sqlib.IConnection#executeQuery(QueryCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public class GetEntryCommand implements QueryCommand {

    // table
    private String tableName;

    // the row the entry is coming from
    private String key;

    // the row with value as reference point
    private RowData atRow;

    /**
     * Get an Entry of a table.
     * @param tableName The Table Name the entry is in
     * @param rowName The Row the Entry is saved in
     * @param atRow The Row and its Value to get the entry in the row {@param rowName}
     */
    public GetEntryCommand(String tableName, String rowName, RowData atRow) {
        this.tableName = tableName;
        this.key = rowName;
        this.atRow = atRow;
    }

    /**
     * @return The Table Name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return Returns the row the entry (value) is coming from
     */
    public String getRowName() {
        return key;
    }

    /**
     * @return A row with a value as referencing point for getting the entry.
     */
    public RowData getAtRow() {
        return atRow;
    }

    /**
     * Returns the MySQL Statement, that is used for executing the
     * command in {@link mxrlin.sqlib.IConnection#executeQuery(QueryCommand)}
     *
     * Example:     "SELECT row FROM table WHERE row1=?"
     *              "DELETE FROM table WHERE row1=? AND row2=?"
     *
     * @return Returns an {@link MySQLStatement}
     */
    @Override
    public MySQLStatement getCommandStatement() {

        CommandBuilder builder = new CommandBuilder("SELECT ");
        builder.append(key).append(" FROM ").append(tableName);
        builder.append(" WHERE ").append(getAtRow().getRowName() + "=").appendQuestionMark(getAtRow().getValue());

        return builder.build();
    }
}
