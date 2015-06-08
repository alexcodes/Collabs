package com.collabs.plugin.actions.document;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.data.document.ServerDocument;
import com.collabs.common.model.event.document.RegisterDocumentEvent;
import com.collabs.plugin.actions.ActionException;
import com.collabs.plugin.core.Client;
import com.collabs.plugin.data.storage.Documents;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiPackageStatement;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class RegisterAndBind extends AnAction {
    public RegisterAndBind() {
        super();
    }

    public RegisterAndBind(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @SuppressWarnings("ConstantConditions")
    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            Document document = e.getData(PlatformDataKeys.EDITOR).getDocument();
            if (document == null) {
                Messages.showErrorDialog("No document is selected", "Error");
                return;
            }
            int idDocument = generateIdDocument(document);
            try {
                registerDocument(idDocument, document, e);
                bindDocument(document, idDocument);
                Messages.showInfoMessage("Document registered and bind", "Success");
            } catch (ActionException exception) {
                Messages.showErrorDialog(exception.getMessage(), "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }

    private void registerDocument(int idDocument, Document document, AnActionEvent e) throws ActionException {
        if (Documents.get().contains(document)) {
            throw new ActionException("Document is already bind");
        }
        String name = getName(e);
        String path = getPackage(e);
        String text = document.getText();
        ServerDocument sd = new ServerDocument(idDocument, name, path, text);
        RegisterDocumentEvent eventRegister = new RegisterDocumentEvent(sd);
        Packet packetRegister = new Packet(Client.get().getIdSession(), Client.get().getIdClient(), eventRegister);
        Client.get().send(packetRegister);
    }

    private void bindDocument(Document document, int idDocument) throws ActionException {
        BindDocument action = new BindDocument();
        action.bindDocument(document, idDocument);
    }

    private int generateIdDocument(Document document) {
        String s = Client.get().getIdClient() + ":" + System.currentTimeMillis();
        return s.hashCode();
    }

    private String getName(AnActionEvent e) throws ActionException {
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (virtualFile == null) {
            throw new ActionException("No virtual file");
        }
        return virtualFile.getNameWithoutExtension();
    }

    private String getPackage(AnActionEvent e) throws ActionException {
        PsiFile psiFile = e.getData(PlatformDataKeys.PSI_FILE);
        if (psiFile == null) {
            throw new ActionException("No PSI file");
        }
        for (PsiElement element : psiFile.getChildren()) {
            if (element instanceof PsiPackageStatement) {
                for (PsiElement psiPackageStatement : element.getChildren()) {
                    if (psiPackageStatement instanceof PsiJavaCodeReferenceElement) {
                        return psiPackageStatement.getText();
                    }
                }
            }
        }
        return "";
    }
}
