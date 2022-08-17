package mxrlin.sqlib.command.set;

import mxrlin.sqlib.command.CommandBuilder;
import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.misc.MySQLStatement;
import mxrlin.sqlib.misc.RowData;

import java.util.List;

/**
 * Part of the SQLib API
 *
 * Inserts a new column into a table.
 *
 * 1. Create a new instance of {@link mxrlin.sqlib.SQLibConnection} and create a MySQL-Connection
 * 2. Create a new instance of this class.
 * 3. Use the {@link mxrlin.sqlib.SQLibConnection#executeUpdate(UpdatingCommand)} with this class as first parameter.
 *
 * @see mxrlin.sqlib.IConnection#executeUpdate(UpdatingCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public class InsertColumnCommand implements UpdatingCommand {

    // Table Name
    private String tableName;

    // All the data that should be inserted into the column
    private List<RowData> rowData;

    /**
     * Insert a column into a table named {@param tableName}
     * @param tableName The name of the table
     * @param rowData The data that should be inserted into the column
     */
    public InsertColumnCommand(String tableName, List<RowData> rowData) {
        this.tableName = tableName;
        this.rowData = rowData;
    }

    /**
     * @return The name of the table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return The data that should be inserted into the column
     */
    public List<RowData> getRowData() {
        return rowData;
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

        CommandBuilder builder = new CommandBuilder("INSERT INTO "); // INSERT INTO
        builder.append(tableName + " ("); // INSERT INTO %TABLENAME% (

        for (int i = 0; i < getRowData().size(); i++) {
            RowData row = getRowData().get(i);
            builder.append(row.getRowName()); // INSERT INTO %TABLENAME% (rowName
            if(i != getRowData().size()) builder.append(", "); // INSERT INTO %TABLENAME% (rowName, ...
        }

        builder.append(") VALUES ("); // INSERT INTO %TABLENAME% (rowName, ..., ...) VALUES (

        for (int i = 0; i < getRowData().size(); i++) {
            RowData row  = getRowData().get(i);
            builder.appendQuestionMark(row.getValue()); // INSERT INTO %TABLENAME% (rowName, ..., ...) VALUES (?
            if(i != getRowData().size()) builder.append(", "); // INSERT INTO %TABLENAME% (rowName, ..., ...) VALUES (?, ...
        }

        builder.append(")"); // INSERT INTO %TABLENAME% (rowName, ..., ...) VALUES (?, ?, ?)

        return builder.build();
    }
}
