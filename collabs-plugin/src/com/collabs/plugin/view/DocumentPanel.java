package com.collabs.plugin.view;

import com.collabs.common.model.data.document.ServerDocument;
import com.collabs.plugin.actions.ActionException;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Collection;

/**
 * @author Aleksey A.
 */
public class DocumentPanel extends JScrollPane {
    private static final JBList documentList = new JBList();

    public DocumentPanel() {
        super(documentList);
    }

    @SuppressWarnings("unchecked")
    public static void setDocuments(final Collection<ServerDocument> documents) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                synchronized (documentList) {
                    documentList.setListData(documents.toArray());
                }
            }
        });
    }

    /**
     * @return {@code id} of document which is selected or {@code -1} otherwise
     */
    public static int getSelectedId() throws ActionException {
        ServerDocument document = (ServerDocument) documentList.getSelectedValue();
        if (document == null) {
            throw new ActionException("No document is selected");
        }
        return document.getIdDocument();
    }
}
