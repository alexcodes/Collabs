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
        add(getCenter(), BorderLayout.CENTER);
        add(getTop(), BorderLayout.NORTH);
    }

    private JPanel getTop() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JButton("Bind"));
        panel.add(new JButton("Unbind"));
        return panel;
    }

    private JPanel getCenter() {
        JPanel panel = new JPanel(new BorderLayout());
        JBList jbList = new JBList("1. test.txt(collabs/action/)",
                "2. Action.java(collabs/connection/server/)",
                "3. Action.java(collabs/connection/server/)",
                "4. Action.java(collabs/connection/server/)");
        panel.add(jbList);
        jbList.setListData(new String[]{"1. New value"});
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Collabs");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setSize(300, 700);
                frame.setLocation(1050, 20);

                frame.getContentPane().setLayout(new BorderLayout(10, 10));
                frame.getContentPane().add(new CollabsPanel(null));

                frame.setVisible(true);
            }
        });
    }
}
