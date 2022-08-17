package mxrlin.sqlib.misc;

/**
 * Part of the SQLib API
 *
 * All MySQL-DataTypes that are supported by SQLib.
 */
public enum DataType {

    TINYINT, SMALLINT, MEDIUMINT, INT, // https://dev.mysql.com/doc/refman/8.0/en/integer-types.html

    FLOAT, DOUBLE, // https://dev.mysql.com/doc/refman/8.0/en/floating-point-types.html

    CHAR, VARCHAR, // https://dev.mysql.com/doc/refman/8.0/en/char.html

    TINYBLOB, BLOB, MEDIUMBLOB, LONGBLOB, TINYTEXT, TEXT, MEDIUMTEXT, LONGTEXT; // https://dev.mysql.com/doc/refman/8.0/en/blob.html

}
