package collabs.model.core;

import collabs.model.core.Manager;
import com.intellij.openapi.vfs.*;

/**
 * Author: Aleksey A.
 * Date: 26.03.14
 * Time: 20:26
 */
public class SimpleVirtualFileListener implements VirtualFileListener {
    private static VirtualFileEvent lastEvent;

    @Override
    public void propertyChanged(VirtualFilePropertyEvent event) {
        handleEvent(event);
    }

    @Override
    public void contentsChanged(VirtualFileEvent event) {
        handleEvent(event);
    }

    @Override
    public void fileCreated(VirtualFileEvent event) {
        handleEvent(event);
    }

    @Override
    public void fileDeleted(VirtualFileEvent event) {
        handleEvent(event);
    }

    @Override
    public void fileMoved(VirtualFileMoveEvent event) {
        handleEvent(event);
    }

    @Override
    public void fileCopied(VirtualFileCopyEvent event) {
        handleEvent(event);
    }

    @Override
    public void beforePropertyChange(VirtualFilePropertyEvent event) {
        handleEvent(event);
    }

    @Override
    public void beforeContentsChange(VirtualFileEvent event) {
        handleEvent(event);
    }

    @Override
    public void beforeFileDeletion(VirtualFileEvent event) {
        handleEvent(event);
    }

    @Override
    public void beforeFileMovement(VirtualFileMoveEvent event) {
        handleEvent(event);
    }

    private void handleEvent(VirtualFileEvent event) {
        if (event != null && event != lastEvent) {
            StringBuilder sb = new StringBuilder();
            sb.append(event.getFile()).append("\n")
                    .append(event).append("\n")
                    .append(event.getOldModificationStamp()).append("\n")
                    .append(event.getNewModificationStamp()).append("\n");
            if (event instanceof VirtualFilePropertyEvent) {
                sb.append(((VirtualFilePropertyEvent) event).getOldValue()).append("\n");
                sb.append(((VirtualFilePropertyEvent) event).getNewValue()).append("\n");
            }
            Manager.getManager().getConnection().transmit(sb.toString());
        }
        lastEvent = event;
    }
}
