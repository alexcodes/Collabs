package collabs.output;

/**
 * Access point to printing actions in whole application
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

    public static synchronized void printBoth(String x) {
        print(x);
        console(x);
    }
}
