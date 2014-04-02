package collabs.view.toolbar;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Aleksey A.
 * Date: 27.03.14
 * Time: 19:53
 */
public class CollabsPanel extends JPanel {
    private CollabsProjectComponent projectComponent;
    private ToolWindow toolWindow;

    public CollabsPanel(CollabsProjectComponent cpc) {
        projectComponent = cpc;
        buildGUI();
    }

    public void setToolWindow(ToolWindow tw) {
        toolWindow = tw;
    }

    private void buildGUI() {
        setLayout(new BorderLayout());
        add(new JLabel("EXAMPLE ПРИМЕР"));
        JBList jbList = new JBList("one", "two", "three", "four");
        add(jbList);
    }
}
