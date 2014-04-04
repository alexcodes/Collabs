package collabs.output;

import collabs.model.core.ToolbarModel;

/**
 * Author: Aleksey A.
 * Date: 04.04.14
 * Time: 22:50
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
