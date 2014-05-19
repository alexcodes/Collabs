package collabs.connection;

/**
 * A {@code Connection} defines some object that can
 * either transmit or receive data. Also it can
 * establish connection and break it.
 *
 * Author: Aleksey A.
 */
public interface Connection {
    /**
     * Connect to some source of receiving data.
     * Also transmitting data to the source is also possible.
     */
    public void connect();

    /**
     * Disconnect from source of data.
     */
    public void disconnect();

    /**
     * Receive data from source.
     * @return received object
     */
    public Object receive();

    /**
     * Transmit data to the source.
     * @param object - transmitted object
     */
    public void transmit(Object object);
}
