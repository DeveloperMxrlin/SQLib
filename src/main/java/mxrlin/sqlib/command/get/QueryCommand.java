package mxrlin.sqlib.command.get;

import mxrlin.sqlib.misc.MySQLStatement;

/**
 * Part of the SQLib API
 *
 * Interface for all commands that can be executed with the method {@link mxrlin.sqlib.SQLibConnection#executeQuery(QueryCommand)}
 * Every Command implementing this will return a {@link java.sql.ResultSet} as this command-type is the "Getter-Part"
 *
 * @see mxrlin.sqlib.command.set.UpdatingCommand
 * @see mxrlin.sqlib.IConnection#executeQuery(QueryCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public interface QueryCommand {

    /**
     * Returns the MySQL Statement, that is used for executing the
     * command in {@link mxrlin.sqlib.IConnection#executeQuery(QueryCommand)}
     *
     * Example:     "SELECT row FROM table WHERE row1=?"
     *              "DELETE FROM table WHERE row1=? AND row2=?"
     *
     * @return Returns an {@link MySQLStatement}
     */
    MySQLStatement getCommandStatement();

}
