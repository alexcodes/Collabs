package collabs.output;

/**
 * General interface for printing actions
 */
public interface Printer {
    /**
     * Prints string
     * @param s output
     */
    public void print(String s);

    /**
     * Pints integer
     * @param i output
     */
    public void print(int i);

    /**
     * Prints object
     * @param object output
     */
    public void print(Object object);
}
