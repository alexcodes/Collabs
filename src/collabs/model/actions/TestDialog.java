package collabs.model.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 23:15
 */
public class TestDialog extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        String[] values = new String[]{"one", "two", "three", "four", "five"};
        Project project = e.getProject();

        Messages.showEditableChooseDialog("Message", "Title", Messages.getInformationIcon(), values, "initialValue", null);
    }
}
