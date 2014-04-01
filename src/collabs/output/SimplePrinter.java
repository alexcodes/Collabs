package collabs.output;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 18:06
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
