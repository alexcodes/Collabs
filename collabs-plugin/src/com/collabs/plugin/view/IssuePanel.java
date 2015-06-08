package com.collabs.plugin.view;

import com.collabs.common.model.data.issue.Issue;
import com.collabs.common.model.data.issue.IssueProgress;
import com.collabs.plugin.actions.issue.AssignIssue;
import com.collabs.plugin.actions.issue.CreateIssue;
import com.collabs.plugin.actions.issue.EditIssue;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.ui.JBColor;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * @author Aleksey A.
 */
public class IssuePanel extends JScrollPane implements CollabsConstants {
    private static final Tree issueTree = new Tree();
    private static final DefaultMutableTreeNode OPEN_ISSUES = new DefaultMutableTreeNode("Open");
    private static final DefaultMutableTreeNode IN_PROGRESS_ISSUES = new DefaultMutableTreeNode("In progress");
    private static final DefaultMutableTreeNode CLOSED_ISSUES = new DefaultMutableTreeNode("Closed");
    private static DefaultTreeModel treeModel;

    public IssuePanel() {
        super(issueTree);
        init();
    }

    private void init() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Issues");
        root.add(OPEN_ISSUES);
        root.add(IN_PROGRESS_ISSUES);
        root.add(CLOSED_ISSUES);
        treeModel = new DefaultTreeModel(root);
        issueTree.setModel(treeModel);
        final JPopupMenu menu = createPopupMenu();
        issueTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    TreePath path = issueTree.getSelectionPath();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node.getUserObject() instanceof Issue) {
                        menu.show(issueTree, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private JPopupMenu createPopupMenu() {
        DefaultActionGroup actionGroup = new DefaultActionGroup(ID_ISSUE_ACTION_GROUP, true);
        actionGroup.add(new CreateIssue("Create Issue", null, null));
        actionGroup.add(new EditIssue("Edit Issue", null, null));
        actionGroup.add(new AssignIssue("Assign Issue", null, null));
        ActionManager manager = ActionManager.getInstance();
        return manager.createActionPopupMenu(ID_ISSUE_POPUP, actionGroup).getComponent();
    }

    public static void setIssues(final Collection<Issue> issues) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                synchronized (issueTree) {
                    OPEN_ISSUES.removeAllChildren();
                    IN_PROGRESS_ISSUES.removeAllChildren();
                    CLOSED_ISSUES.removeAllChildren();
                    for (Issue issue : issues) {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(issue.toString());
                        node.setUserObject(issue);
                        if (issue.getProgress() == IssueProgress.OPEN) {
                            OPEN_ISSUES.add(node);
                        } else if (issue.getProgress() == IssueProgress.IN_PROGRESS) {
                            IN_PROGRESS_ISSUES.add(node);
                        } else if (issue.getProgress() == IssueProgress.CLOSED) {
                            CLOSED_ISSUES.add(node);
                        }
                    }
                    treeModel.reload();
                }
            }
        });
    }

    public static Issue getSelectedIssue() {
        TreePath path = issueTree.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node.getUserObject() instanceof Issue) {
            return (Issue) node.getUserObject();
        }
        return null;
    }
}
