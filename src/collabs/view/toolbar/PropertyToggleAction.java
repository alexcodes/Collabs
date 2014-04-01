package collabs.view.toolbar;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;

import javax.swing.*;

/**
 * Author: Aleksey A.
 * Date: 27.03.14
 * Time: 21:09
 */
public class PropertyToggleAction extends ToggleAction {

    public PropertyToggleAction(String actionName, String toolTip, Icon icon, Object target, String property)
    {
        super(actionName, toolTip, icon);
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return false;
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {

    }
}
