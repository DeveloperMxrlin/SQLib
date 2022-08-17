package mxrlin.sqlib.command.set;

import mxrlin.sqlib.command.CommandBuilder;
import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.misc.MySQLStatement;
import mxrlin.sqlib.misc.RowData;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of the SQLib API
 *
 * Updates an Entry of a Table
 *
 * 1. Create a new instance of {@link mxrlin.sqlib.SQLibConnection} and create a MySQL-Connection
 * 2. Create a new instance of this class.
 * 3. Use the {@link mxrlin.sqlib.SQLibConnection#executeUpdate(UpdatingCommand)} with this class as first parameter.
 *
 * @see mxrlin.sqlib.IConnection#executeUpdate(UpdatingCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public class UpdateEntryCommand implements UpdatingCommand {

    // table name
    private String tableName;

    // rows that are getting updated
    private List<RowData> updatingRows;

    // rows to specify which column is meant
    private List<RowData> updateAt;

    /**
     * Updates an Entry in a table
     * @param tableName The table name
     * @param updatingRow The Row that is getting updated
     * @param updateAt The Row to specify which column should be updated
     */
    public UpdateEntryCommand(String tableName, RowData updatingRow, RowData updateAt) {
        this.tableName = tableName;
        this.updatingRows = new ArrayList<>();
        this.updatingRows.add(updatingRow);
        this.updateAt = new ArrayList<>();
        this.updateAt.add(updateAt);
    }

    /**
     * Updates an Entry in a table
     * @param tableName The table name
     * @param updatingRows The Row(s) that is/are getting updated
     * @param updateAt The Row(s) to specify which column should be updated
     */
    public UpdateEntryCommand(String tableName, List<RowData> updatingRows, List<RowData> updateAt) {
        this.tableName = tableName;
        this.updatingRows = updatingRows;
        this.updateAt = updateAt;
    }

    /**
     * @return The table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return The Row(s) that is/are getting updated
     */
    public List<RowData> getUpdatingRows() {
        return updatingRows;
    }

    /**
     * @return The Row(s) to specify which column should be updated
     */
    public List<RowData> getUpdateAt() {
        return updateAt;
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
        CommandBuilder commandBuilder = new CommandBuilder("UPDATE ");

        commandBuilder.append(tableName);
        commandBuilder.append(" SET ");

        for (int i = 0; i < updatingRows.size(); i++) {
            RowData rowData = updatingRows.get(i);
            commandBuilder.append(rowData.getRowName() + "=");
            commandBuilder.appendQuestionMark(rowData.getValue());
            if(i != updatingRows.size()) commandBuilder.append(", ");
        }

        commandBuilder.append(" WHERE ");

        for (int i = 0; i < updateAt.size(); i++) {

            RowData rowData = updateAt.get(i);
            commandBuilder.append(rowData.getRowName() + "=").appendQuestionMark(rowData.getValue());

            if(i != updateAt.size()) commandBuilder.append(" AND ");
        }

        return commandBuilder.build();
    }
}
