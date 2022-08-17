package mxrlin.sqlib.misc;

/**
 * Part of the SQLib API
 *
 * Simple Class that contains the row of the Name and its set value
 */
public class RowData {

    // Name of the row
    private String rowName;

    // value of the row
    private Object value;

    /**
     * Creates a new RowData instance with 2 parameters.
     * @param rowName The Name of the Row
     * @param value The Value of the Row
     */
    public RowData(String rowName, Object value) {
        this.rowName = rowName;
        this.value = value;
    }

    /**
     * @return The Name of the Row
     */
    public String getRowName() {
        return rowName;
    }

    /**
     * @return The Value of the Row
     */
    public Object getValue() {
        return value;
    }

}
