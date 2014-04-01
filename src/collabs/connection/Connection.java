package collabs.connection;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 17:15
 */
public interface Connection {
    public void connect();
    public void disconnect();
    public Object receive();
    public void transmit(Object object);
}
