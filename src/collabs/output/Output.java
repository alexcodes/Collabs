package collabs.output;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 18:04
 */
public class Output {
    private static final Printer printer = new SimplePrinter();

    public static synchronized void print(String x) {
        printer.print(x);
    }

    public static synchronized void print(int x) {
        printer.print(x);
    }

    public static synchronized void print(Object x) {
        printer.print(x);
    }
}
