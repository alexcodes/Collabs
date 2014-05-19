package collabs.output;

/**
 * Printer whose output is system console
 */
public class SimplePrinter implements Printer {
    @Override
    public void print(String x) {
        System.out.println(x);
    }

    @Override
    public void print(int x) {
        System.out.println(x);
    }

    @Override
    public void print(Object x) {
        System.out.println(x);
    }
}
