package com.collabs.common.model.event.document;

/**
 * @author Aleksey A.
 */
public class DocumentChangedEvent implements DocumentEvent {
    private String oldFragment;
    private String newFragment;
    private int offset;
    private int idDocument;

    public DocumentChangedEvent(String oldFragment, String newFragment, int offset, int idDocument) {
        this.oldFragment = oldFragment;
        this.newFragment = newFragment;
        this.offset = offset;
        this.idDocument = idDocument;
    }

    public String getOldFragment() {
        return oldFragment;
    }

    public String getNewFragment() {
        return newFragment;
    }

    public int getOffset() {
        return offset;
    }

    public int getIdDocument() {
        return idDocument;
    }

    @Override
    public String toString() {
        return "id_document#" + idDocument
                + " [offset=" + offset + "; old=\"" + oldFragment + "\"; new=\"" + newFragment + "\"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DocumentChangedEvent) {
            DocumentChangedEvent event = (DocumentChangedEvent) obj;
            return oldFragment.equals(event.getOldFragment())
                    && newFragment.equals(event.getNewFragment())
                    && offset == event.getOffset()
                    && idDocument == event.getIdDocument();
        }
        return false;
    }
}
