package com.collabs.plugin.view;

import com.collabs.common.model.data.document.ServerDocument;
import com.collabs.common.model.data.issue.Issue;
import com.collabs.plugin.actions.ActionException;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collection;

public class CollabsPanel extends JPanel {

    public CollabsPanel() {
        buildGUI();
    }

    public void setToolWindow() {
    }

    private void buildGUI() {
        setLayout(new BorderLayout());
        add(getContent(), BorderLayout.CENTER);
    }

    private JPanel getContent() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(JBColor.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 0, 0));
        panel.add(getTabPane(), BorderLayout.CENTER);
        return panel;
    }

    private JTabbedPane getTabPane() {
        JTabbedPane tabbedPane = new JBTabbedPane();
        tabbedPane.add("Documents", getDocumentsPanel());
        tabbedPane.add("Issues", getIssuePanel());
        return tabbedPane;
    }

    @SuppressWarnings("unchecked")
    public static void setDocuments(final Collection<ServerDocument> documents) {
        DocumentPanel.setDocuments(documents);
    }

    public static void setIssues(final Collection<Issue> issues) {
        IssuePanel.setIssues(issues);
    }

    public static int getSelectedId() throws ActionException {
        return DocumentPanel.getSelectedId();
    }

    private Component getDocumentsPanel() {
        return new DocumentPanel();
    }

    private Component getIssuePanel() {
        return new IssuePanel();
    }
}