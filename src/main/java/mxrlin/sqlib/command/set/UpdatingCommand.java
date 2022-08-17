package mxrlin.sqlib.command.set;

import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.misc.MySQLStatement;

/**
 * Part of the SQLib API
 *
 * Interface for all commands that can be executed with the method {@link mxrlin.sqlib.SQLibConnection#executeUpdate(UpdatingCommand)} 
 * Every Command implementing this will return a boolean that tells if the process of updating was successful.
 *
 * @see mxrlin.sqlib.command.set.UpdatingCommand
 * @see mxrlin.sqlib.IConnection#executeQuery(QueryCommand)
 * @see mxrlin.sqlib.SQLibConnection
 */
public interface UpdatingCommand {

    /**
     * Returns the MySQL Statement, that is used for executing the 
     * command in {@link mxrlin.sqlib.IConnection#executeUpdate(UpdatingCommand)} 
     *
     * Example:     "SELECT row FROM table WHERE row1=?"
     *              "DELETE FROM table WHERE row1=? AND row2=?"
     *
     * @return Returns an {@link MySQLStatement}
     */
    MySQLStatement getCommandStatement();

}
