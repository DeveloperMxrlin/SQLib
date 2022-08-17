package mxrlin.sqlib.command.set;

import mxrlin.sqlib.command.CommandBuilder;
import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.misc.MySQLStatement;
import mxrlin.sqlib.misc.RowData;

import java.util.List;

/**
 * Part of the SQLib API
 *
 * Deletes a column of a table.
 *
 * 1. Create a new instance of {@link mxrlin.sqlib.SQLibConnection} and create a MySQL-Connection
 * 2. Create a new instance of this class.
 * 3. Use the {@link mxrlin.sqlib.SQLibConnection#executeUpdate(UpdatingCommand)} with this class as first parameter.
 *
 * @see mxrlin.sqlib.IConnection#executeUpdate(UpdatingCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public class DeleteColumnCommand implements UpdatingCommand {

    // The table name
    private String tableName;

    // row data, the more is in list, the more its specified which column should be deleted
    private List<RowData> rowData;

    /**
     * Delete a column out of a table
     * @param tableName The name of the table
     * @param rowData Data of some or all rows.
     */
    public DeleteColumnCommand(String tableName, List<RowData> rowData) {
        this.tableName = tableName;
        this.rowData = rowData;
    }

    /**
     * @return Returns the name of the table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return Returns a List with data of rows
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
        CommandBuilder builder = new CommandBuilder("DELETE FROM ");

        builder.append(tableName).append(" WHERE ");

        for (int i = 0; i < getRowData().size(); i++) {

            RowData rowData = getRowData().get(i);
            builder.append(rowData.getRowName() + "=").appendQuestionMark(rowData.getValue());

            if(i != getRowData().size()) builder.append(" AND ");
        }

        return builder.build();
    }

}
