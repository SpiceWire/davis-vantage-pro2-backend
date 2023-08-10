package spicewire.davisinterface.Model;

/**
 * Defines minimum requirements to create a record of sensor reading(s).
 */
public interface DataRecord {

    /**
     * Gets the type of data being sent. 0 = Loop1   1 = LPS  3 = other.
     * Used by JDBC to determine which SQL statements to use.
     * @return integer
     */
     int getPacketType();

    /**
     * Gets the source of data. E.g. name or location of sensor or
     * sensor package.
     * Used in database for labels.
     * @return String
     */
     String getDataSource();
}
