package com.collabs.server.core;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.BenchmarkEvent;
import com.collabs.common.model.event.document.*;
import com.collabs.common.model.event.issue.IssueEvent;

/**
 * @author Aleksey A.
 */
public class SimpleHandler extends AbstractHandler {
    @Override
    protected void handlePlugins(Packet packet) {
        //empty
    }
}
