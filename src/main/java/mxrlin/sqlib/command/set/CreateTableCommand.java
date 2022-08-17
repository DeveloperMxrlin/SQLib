package mxrlin.sqlib.command.set;

import mxrlin.sqlib.command.CommandBuilder;
import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.misc.MySQLStatement;
import mxrlin.sqlib.misc.StorageEngine;
import mxrlin.sqlib.misc.Table;

/**
 * Part of the SQLib API
 *
 * Creates a Table.
 *
 * 1. Create a new instance of {@link mxrlin.sqlib.SQLibConnection} and create a MySQL-Connection
 * 2. Create a new instance of this class.
 * 3. Use the {@link mxrlin.sqlib.SQLibConnection#executeUpdate(UpdatingCommand)} with this class as first parameter.
 *
 * @see mxrlin.sqlib.IConnection#executeUpdate(UpdatingCommand)
 * @see mxrlin.sqlib.SQLibConnection
 * @see mxrlin.sqlib.misc.Table
 */
public class CreateTableCommand implements UpdatingCommand {

    // the table with all its information
    private Table table;

    // true if "CREATE IF NOT EXISTS" should be added to cmd string
    private boolean createIfNotExists;

    /**
     * Create a new table in the selected database
     * @param table The new Table with all its needed information
     * @param createIfNotExists If true, the table only creates if it doesn't already exist.
     * @see mxrlin.sqlib.misc.Table
     */
    public CreateTableCommand(Table table, boolean createIfNotExists) {
        this.table = table;
        this.createIfNotExists = createIfNotExists;
    }

    /**
     * @return Returns a {@link Table}
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return If true, the table is only getting created if it doesn't already exist
     */
    public boolean isCreateIfNotExists() {
        return createIfNotExists;
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

        CommandBuilder commandBuilder = new CommandBuilder("CREATE TABLE ");
        if(createIfNotExists)
            commandBuilder.append("IF NOT EXISTS ");
        commandBuilder.append(table.getTableName());

        commandBuilder.append(" (");
        for (int i = 0; i < table.getRows().size(); i++) {
            Table.TableRow row = table.getRows().get(i);

            commandBuilder.append(row.getRowName());
            commandBuilder.append(" ").append(row.getDataType().name()).append("(").append("" + row.getMaxLength()).append(")");

            if(!row.isAllowedToContainNull()) commandBuilder.append(" NOT NULL");
            if(row.isAutoIncrement()) commandBuilder.append(" AUTO_INCREMENT");

            if(i != table.getRows().size()) commandBuilder.append(", ");
        }

        commandBuilder.append(")");

        // name (rowname ROWTYPE(length), ...) ENGINE=engine DEFAULT CHARACTER SET;
        if(table.getEngine() != null && table.getEngine() != StorageEngine.InnoDB) commandBuilder.append("ENGINE=" + table.getEngine().name());
        if(table.getCharset() != null && !table.getCharset().isEmpty()) commandBuilder.append("DEFAULT CHARACTER SET " + table.getCharset());

        return commandBuilder.build();
    }

}
