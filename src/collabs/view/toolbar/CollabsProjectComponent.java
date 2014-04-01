package collabs.view.toolbar;

import collabs.view.CollabsConstants;
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

/**
 * Author: Aleksey A.
 * Date: 27.03.14
 * Time: 19:36
 */
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
        panel = new CollabsPanel(this);

        ActionManager actionManager = ActionManager.getInstance();
        DefaultActionGroup actionGroup = new DefaultActionGroup(ID_ACTION_GROUP, false);
        actionGroup.add(new PropertyToggleAction("Toggle", "2", null, this, ""));

        ActionToolbar toolBar = actionManager.createActionToolbar(ID_ACTION_TOOLBAR, actionGroup, true);
        panel.add(toolBar.getComponent(), BorderLayout.NORTH);

        ToolWindow toolWindow = getToolWindow();
        panel.setToolWindow(toolWindow);
    }

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
