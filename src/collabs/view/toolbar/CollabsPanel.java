package collabs.view.toolbar;

import collabs.model.core.ToolbarModel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
        add(getContent(), BorderLayout.CENTER);
    }

    private JPanel getContent() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(JBColor.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 0, 0));
        JBScrollPane scrollPane;

        JBList jbList = new JBList("1. test.txt(collabs/action/)",
                "2. Action.java(collabs/connection/server/)",
                "3. Action.java(collabs/connection/server/)",
                "4. Action.java(collabs/connection/server/)");

        scrollPane = new JBScrollPane(jbList);
        scrollPane.setBorder(new TitledBorder(new LineBorder(JBColor.GRAY), "Registered documents"));
        panel.add(scrollPane, BorderLayout.CENTER);
        //jbList.setListData(new String[]{"1. New value"});

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setRows(15);
        ToolbarModel.getToolbarModel().setConsoleView(textArea);
        scrollPane = new JBScrollPane(textArea);
        scrollPane.setBorder(new TitledBorder(new LineBorder(JBColor.GRAY), "Console"));
        panel.add(scrollPane, BorderLayout.SOUTH);

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
