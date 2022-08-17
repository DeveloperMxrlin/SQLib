package mxrlin.sqlib.command;

import mxrlin.sqlib.misc.MySQLStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of the SQLib API
 *
 * CommandBuilder used for every command that is in the package <b>mxrlin.sqlib.command</b>
 *
 * Build a Command by appending default command lines or question marks that are later getting replaced by given values.
 * This prevents SQL Injection.
 */
public class CommandBuilder {

    // current command
    private StringBuilder command;

    // list with all objects that are later getting replaced with "?"
    private List<Object> replaces;

    /**
     * Create a new empty CommandBuilder
     */
    public CommandBuilder(){
        command = new StringBuilder();
        replaces = new ArrayList<>();
    }

    /**
     * Create a new CommandBuilder with {@param start} at the start
     * @param start The Starting Command
     */
    public CommandBuilder(String start){
        command = new StringBuilder(start);
        replaces = new ArrayList<>();
    }

    /**
     * Append a default command line to the command
     * @param str default command line
     */
    public CommandBuilder append(String str){
        command.append(str);
        return this;
    }

    /**
     * Append a QuestionMark to the command that is later getting replaced with {@param replace}
     * @param replace The Object that is replaced with "?"
     */
    public CommandBuilder appendQuestionMark(Object replace){
        command.append("?");
        replaces.add(replace);
        return this;
    }

    /**
     * @return Returns the current command
     */
    public String getCommand() {
        return command.toString();
    }

    /**
     * @return Returns all Objects that are later getting replaced with Question Marks
     */
    public List<Object> getReplaces(){
        return new ArrayList<>(replaces);
    }

    /**
     * Build the CommandBuilder.
     * @return Returns {@link MySQLStatement}
     */
    public MySQLStatement build(){
        return new MySQLStatement(command.toString(), replaces);
    }

}
