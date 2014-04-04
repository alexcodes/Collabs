package collabs.output;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 18:04
 */
public class Output {
    private static final Printer printer = new SimplePrinter();
    private static final Printer consoleView = new ConsoleViewPrinter();

    public static synchronized void print(String x) {
        printer.print(x);
    }

    public static synchronized void console(String x) {
        consoleView.print(x);
    }
}
