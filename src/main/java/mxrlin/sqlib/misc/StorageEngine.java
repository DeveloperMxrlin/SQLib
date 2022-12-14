package mxrlin.sqlib.misc;

/**
 * Part of SQLib API
 *
 * StorageEngines of MySQL 8.0
 * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/storage-engines.html">Learn more about StorageEngines</a>
 */
public enum StorageEngine {

    /**
     *  The default storage engine in MySQL 8.0. InnoDB is a transaction-safe (ACID compliant) storage engine for MySQL
     *  that has commit, rollback, and crash-recovery capabilities to protect user data. InnoDB row-level locking
     *  (without escalation to coarser granularity locks) and Oracle-style consistent nonlocking reads increase multi-user
     *  concurrency and performance. InnoDB stores user data in clustered indexes to reduce I/O for common queries based on
     *  primary keys. To maintain data integrity, InnoDB also supports FOREIGN KEY referential-integrity constraints.
     */
    InnoDB,

    /**
     * These tables have a small footprint. Table-level locking limits the performance in read/write workloads,
     * so it is often used in read-only or read-mostly workloads in Web and data warehousing configurations.
     */
    MyISAM,

    /**
     * Stores all data in RAM, for fast access in environments that require quick lookups of non-critical data.
     * This engine was formerly known as the HEAP engine. Its use cases are decreasing; InnoDB with its buffer pool
     * memory area provides a general-purpose and durable way to keep most or all data in memory, and NDBCLUSTER
     * provides fast key-value lookups for huge distributed data sets.
     */
    Memory,

    /**
     * Its tables are really text files with comma-separated values. CSV tables let you import or dump data in CSV format,
     * to exchange data with scripts and applications that read and write that same format. Because CSV tables are not indexed,
     * you typically keep the data in InnoDB tables during normal operation, and only use CSV tables during the import
     * or export stage.
     */
    CSV,

    /**
     * These compact, unindexed tables are intended for storing and retrieving large amounts of
     * seldom-referenced historical, archived, or security audit information.
     */
    Archive,

    /**
     * The Blackhole storage engine accepts but does not store data, similar to the Unix /dev/null device. Queries always return
     * an empty set. These tables can be used in replication configurations where DML statements are sent to replica servers, but the
     * source server does not keep its own copy of the data.
     */
    Blackhole,

    /**
     * This clustered database engine is particularly suited for applications that require the highest possible degree of
     * uptime and availability.
     */
    NDB,

    /**
     * Enables a MySQL DBA or developer to logically group a series of identical MyISAM tables and reference them as one object.
     * Good for VLDB environments such as data warehousing.
     */
    Merge,

    /**
     * Offers the ability to link separate MySQL servers to create one logical database from many physical servers.
     * Very good for distributed or data mart environments.
     */
    Federated,

    /**
     * This engine serves as an example in the MySQL source code that illustrates how to begin writing new storage engines.
     * It is primarily of interest to developers. The storage engine is a ,stub, that does nothing. You can create tables with this engine,
     * but no data can be stored in them or retrieved from them.
     */
    Example;

}
