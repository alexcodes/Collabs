package collabs.output;

import collabs.model.core.ToolbarModel;

/**
 * Printer whose output is plugin console
 */
public class ConsoleViewPrinter implements Printer {
    @Override
    public synchronized void print(String s) {
        ToolbarModel.write(s);
    }

    @Override
    public synchronized void print(int i) {
        print(Integer.toString(i));
    }

    @Override
    public synchronized void print(Object object) {
        print(object.toString());
    }
}
