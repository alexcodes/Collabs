package com.collabs.plugin.view;

import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Aleksey A.
 */
public class ConsolePanel extends JScrollPane {
    public ConsolePanel() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setRows(15);
        setViewportView(textArea);
        setBorder(new TitledBorder(new LineBorder(JBColor.GRAY), "Console"));
        setAutoscrolls(true);
    }
}
