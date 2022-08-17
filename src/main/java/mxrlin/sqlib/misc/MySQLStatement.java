package mxrlin.sqlib.misc;

import mxrlin.sqlib.exception.SQLibException;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of the SQLib API
 *
 * This class is used for generating string commands into {@link PreparedStatement} that are later used for
 * executing Updates ({@link mxrlin.sqlib.command.set.UpdatingCommand}) or for getting data out
 * of the MySQL Database ({@link mxrlin.sqlib.command.get.QueryCommand}).
 *
 * @see mxrlin.sqlib.command.get.QueryCommand
 * @see mxrlin.sqlib.command.set.UpdatingCommand
 * @see mxrlin.sqlib.IConnection
 */
public class MySQLStatement {

    // the simple command
    private String command;

    // list with args that are later replaced with the question marks
    private List<Object> replaceArguments;

    /**
     * Generate a Simple MySQL Statement with just a normal String command
     */
    public MySQLStatement(String command) {
        this.command = command;
    }

    /**
     * Generate a SQL-Injection safe MySQL Statement with a String command and arguments that are later replacing the "?"
     * @param command The String Command
     * @param replaceArguments Objects that are later replacing Question marks
     */
    public MySQLStatement(String command, List<Object> replaceArguments) {
        this.command = command;
        this.replaceArguments = replaceArguments;
    }

    /**
     * @return The String Command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return Objects that are later replacing Question marks
     */
    public List<Object> getReplaceArguments() {
        return new ArrayList<>(replaceArguments);
    }

    /**
     * Generate a PreparedStatement with the current MySQL {@link Connection} {@param connection}.
     * @param connection The ongoing MySQL Connection
     * @return Returns an {@link PreparedStatement}
     * @throws SQLibException Thrown when something went wrong while creating the statement.
     */
    public PreparedStatement asPreparedStatement(Connection connection) throws SQLibException {
        try (PreparedStatement statement = connection.prepareStatement(command)){

            if(getReplaceArguments() != null && getReplaceArguments().size() != 0){

                for(int i = 0; i < getReplaceArguments().size(); i++){

                    Object obj = getReplaceArguments().get(i);
                    if(obj instanceof BigDecimal){
                        statement.setBigDecimal(i+1,(BigDecimal)obj);
                    }else if(obj instanceof Boolean){
                        statement.setBoolean(i+1, (boolean) obj);
                    }else if(obj instanceof Integer){
                        statement.setInt(i+1, (int) obj);
                    }else if(obj instanceof Byte){
                        statement.setByte(i+1, (byte) obj);
                    }else if(obj instanceof URL){
                        statement.setURL(i+1, (URL) obj);
                    }else if(obj instanceof Long){
                        statement.setLong(i+1, (long) obj);
                    }else if(obj instanceof Double){
                        statement.setDouble(i+1, (double) obj);
                    }else if(obj instanceof Short){
                        statement.setShort(i+1, (short) obj);
                    }else if(obj instanceof String){
                        statement.setString(i+1, (String) obj);
                    }else statement.setObject(i+1, obj);

                }

            }

            return statement;
        } catch (SQLException e) {
            throw new SQLibException("Failed to create an prepared Statement with the command \"" + command + "\"", e);
        }
    }

}
