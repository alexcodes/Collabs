package collabs.view;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Aleksey A.
 * Date: 03.04.14
 * Time: 18:02
 */
public class Loader {
    public static Icon getIcon(String path) {
        URL url = Loader.class.getResource(path);
        if (url == null) {
            try {
                url = new URL(path);
            }
            catch (MalformedURLException ignored) {}
        }
        return new ImageIcon(url);
    }
}
