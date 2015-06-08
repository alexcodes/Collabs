package com.collabs.plugin.view;

import com.collabs.plugin.actions.document.RefreshDocuments;
import com.collabs.plugin.actions.general.Connect;
import com.collabs.plugin.actions.general.Disconnect;
import com.collabs.plugin.actions.general.StartServer;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


public class CollabsProjectComponent implements ProjectComponent, CollabsConstants {
    private Project project;
    private CollabsPanel panel;

    public CollabsProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        initToolWindow();
    }

    @Override
    public void projectClosed() {
        unregisterToolWindow();
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return PLUGIN_NAME + '.' + PROJECT_COMPONENT_NAME;
    }

    public Project getProject() {
        return project;
    }

    private void initToolWindow() {
        panel = new CollabsPanel();

        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup(ID_TOOLBAR_ACTION_GROUP, false);
        actionGroup.add(new Connect("Connect", "Connect to server",
                Loader.getIcon(ICON_CONNECT)));
        actionGroup.add(new Disconnect("Disconnect", "Disconnect from server",
                Loader.getIcon(ICON_DISCONNECT)));
        actionGroup.add(new RefreshDocuments("Refresh", "Refresh list of registered documents",
                Loader.getIcon(ICON_REFRESH)));
        actionGroup.add(new StartServer("Start Server", "Start Server",
                Loader.getIcon(ICON_SERVER_START)));

        ActionToolbar toolBar = actionManager.createActionToolbar(ID_ACTION_TOOLBAR, actionGroup, true);
        panel.add(toolBar.getComponent(), BorderLayout.NORTH);

        ToolWindow toolWindow = getToolWindow();
        toolWindow.setIcon(Loader.getIcon(ICON_TOOLBAR));
        panel.setToolWindow();
    }

    @SuppressWarnings("deprecation")
    private ToolWindow getToolWindow() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        if (isToolWindowRegistered()) {
            return toolWindowManager.getToolWindow(ID_TOOL_WINDOW);
        } else {
            return toolWindowManager.registerToolWindow(ID_TOOL_WINDOW,
                    panel,
                    ToolWindowAnchor.RIGHT);
        }
    }

    private boolean isToolWindowRegistered() {
        return ToolWindowManager.getInstance(project).getToolWindow(ID_TOOL_WINDOW) != null;
    }

    public void unregisterToolWindow() {
        if (isToolWindowRegistered())
            ToolWindowManager.getInstance(project).unregisterToolWindow(ID_TOOL_WINDOW);
    }
}