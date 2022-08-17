package mxrlin.sqlib.misc;

import java.util.List;

/**
 * Part of SQLib API
 *
 * Used for the {@link mxrlin.sqlib.command.set.CreateTableCommand} command, to set the defaults as the row, tableName, engine etc.
 */
public class Table {

    // name of table
    private String tableName;

    // rows of table
    private List<TableRow> rows;

    private StorageEngine engine;
    private String charset;

    /**
     * Create a default table with the name {@param tableName} and the rows defined at {@param rows}
     * @param tableName The name of the Table
     * @param rows A list with all rows of the Table
     */
    public Table(String tableName, List<TableRow> rows) {
        this.tableName = tableName;
        this.rows = rows;
    }

    /**
     * Creates a table with the name {@param tableName} and rows defined at {@param rows} and a set charset at {@param charset} and
     * defined {@param engine}
     * @param tableName The name of the Table
     * @param rows A list with all rows of the table
     * @param engine The engine the table is running on
     * @param charset The Charset the table is using
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/charset-charsets.html">Charsets</a>
     * @see StorageEngine
     */
    public Table(String tableName, List<TableRow> rows, StorageEngine engine, String charset) {
        this.tableName = tableName;
        this.rows = rows;
        this.engine = engine;
        this.charset = charset;
    }

    /**
     * @return The name of the Table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return A list with all rows of the table
     */
    public List<TableRow> getRows() {
        return rows;
    }

    /**
     * @return A list with all rows of the table
     */
    public StorageEngine getEngine() {
        return engine;
    }

    /**
     * @return The Charset the table is using
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Used for table creation to set defaults of a table row such as the rowName, the datatype or the maximum length.
     */
    public static class TableRow {

        private String rowName;
        private int maxLength;
        private DataType dataType;
        private boolean canContainNull;
        private boolean autoIncrement;

        /**
         * Creates a tableRow with all defaults
         * @param rowName The Name of the row
         * @param maxLength Maximum length of chars in the row
         * @param dataType The DataType that is saved in that row
         * @param canContainNull true = row can contain null
         * @param autoIncrement true = value automatically increases on inserting new column
         */
        public TableRow(String rowName, int maxLength, DataType dataType, boolean canContainNull, boolean autoIncrement) {
            this.rowName = rowName;
            this.maxLength = maxLength;
            this.dataType = dataType;
            this.canContainNull = canContainNull;
            this.autoIncrement = autoIncrement;
        }

        /**
         * @return The Name of the row
         */
        public String getRowName() {
            return rowName;
        }

        /**
         * @return Maximum length of chars in the row
         */
        public int getMaxLength() {
            return maxLength;
        }

        /**
         * @return The DataType that is saved in that row
         */
        public DataType getDataType() {
            return dataType;
        }

        /**
         * true = row can contain null
         * @return Boolean that determines if the row is allowed to contain null
         */
        public boolean isAllowedToContainNull() {
            return canContainNull;
        }

        /**
         * @return true = value automatically increases on inserting new column
         */
        public boolean isAutoIncrement() {
            return autoIncrement;
        }

    }

}
