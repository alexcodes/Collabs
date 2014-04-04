package collabs.connection;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 22:08
 */
public class Test extends JPanel {
    public Test() {
        setLayout(new FlowLayout());
        add(new JBLabel("label"));
        add(new JButton("button"));
        add(new JBRadioButton("radio"));
        add(new JBCheckBox());
        add(new JBTextField("text field"));
    }

    public static void main(String[] args) {
        //Exception in thread "main" java.lang.IllegalStateException: The DialogWrapper can be used only on event dispatch thread.
        //Messages.showInfoMessage("Message", "Title");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Collabs");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setSize(300, 700);
                frame.setLocation(1050, 20);

                frame.getContentPane().setLayout(new BorderLayout(10, 10));
                frame.getContentPane().add(new Test());

                frame.setVisible(true);
            }
        });
    }
}
