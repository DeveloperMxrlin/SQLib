package mxrlin.sqlib.command.set;

import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.misc.MySQLStatement;

/**
 * Part of the SQLib API
 *
 * Deletes a table.
 *
 * 1. Create a new instance of {@link mxrlin.sqlib.SQLibConnection} and create a MySQL-Connection
 * 2. Create a new instance of this class.
 * 3. Use the {@link mxrlin.sqlib.SQLibConnection#executeUpdate(UpdatingCommand)} with this class as first parameter.
 *
 * @see mxrlin.sqlib.IConnection#executeUpdate(UpdatingCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public class DeleteTableCommand implements UpdatingCommand {

    // the name of the table
    private String tableName;

    /**
     * Delete a table with the name {@param tableName}
     * @param tableName The name of the table
     */
    public DeleteTableCommand(String tableName){
        this.tableName = tableName;
    }

    /**
     * @return Name of table
     */
    public String getTableName() {
        return tableName;
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
        return new MySQLStatement("DROP TABLE " + tableName);
    }
}
