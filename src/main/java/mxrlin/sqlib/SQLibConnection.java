package mxrlin.sqlib;

import mxrlin.sqlib.command.get.QueryCommand;
import mxrlin.sqlib.command.set.UpdatingCommand;
import mxrlin.sqlib.exception.SQLibException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLibConnection implements IConnection {

    private Connection connection;
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    /**
     * Create a new MySQL Database Connection
     * @throws SQLibException Thrown when it couldn't connect to the database
     */
    public SQLibConnection(String host, String port, String database, String username, String password) throws SQLibException {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        open();
    }

    @Override
    public boolean executeUpdate(UpdatingCommand command) throws SQLibException{
        checkIfConnectionIsClosed();
        try{
            command.getCommandStatement().asPreparedStatement(connection).executeUpdate();
            return true;
        }catch (SQLException e){
            throw new SQLibException("Failed to update PreparedStatement with the command \"" + command.getCommandStatement().getCommand() + "\"" + e.getMessage(), e);
        }
    }

    @Override
    public ResultSet executeQuery(QueryCommand command) throws SQLibException{
        checkIfConnectionIsClosed();
        try{
            return command.getCommandStatement().asPreparedStatement(connection).executeQuery();
        }catch (SQLException e){
            throw new SQLibException("Failed to execute Query with the command \"" + command.getCommandStatement().getCommand() + "\"", e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void open() throws SQLibException {
        if(isOpen()) throw new SQLibException("Can't open connection while connection is open.");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            throw new SQLibException("Failed to connect to database", e);
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
        connection = null;
    }

    @Override
    public boolean isOpen() {
        try {
            return !connection.isClosed();
        } catch (SQLException ignored) {}
        return connection != null;
    }

    @Override
    public boolean isAutoCommitting() throws SQLibException {
        try {
            return connection.getAutoCommit();
        } catch (SQLException e) {
            throw new SQLibException("Failed to get auto commit", e);
        }
    }

    @Override
    public void setAutoCommit(boolean bool) throws SQLibException {
        try {
            connection.setAutoCommit(bool);
        } catch (SQLException e) {
            throw new SQLibException("Failed to change auto commit to " + bool, e);
        }
    }

    @Override
    public void commit() throws SQLibException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLibException("Failed to commit", e);
        }
    }

    @Override
    public void rollback() throws SQLibException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SQLibException("Failed to rollback", e);
        }
    }

    private void checkIfConnectionIsClosed() throws SQLibException {
        try{
            if(connection.isClosed()) throw new SQLibException("Connection is closed");
        }catch (SQLException e){
            throw new SQLibException("Failed to check if connection is closed", e);
        }
    }

}
