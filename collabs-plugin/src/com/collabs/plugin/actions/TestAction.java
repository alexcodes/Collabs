package com.collabs.plugin.actions;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.BenchmarkEvent;
import com.collabs.plugin.core.Client;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackageStatement;

/**
 * @author Aleksey A.
 */
public class TestAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        System.out.println(e.getData(PlatformDataKeys.VIRTUAL_FILE).getNameWithoutExtension());
        for (PsiElement element : e.getData(PlatformDataKeys.PSI_FILE).getChildren()) {
            if (element instanceof PsiPackageStatement) {
                for (PsiElement psiElement : element.getChildren()) {
                    System.out.println(psiElement);
                    System.out.println(psiElement.getClass());
                }
            }
        }
    }
}
