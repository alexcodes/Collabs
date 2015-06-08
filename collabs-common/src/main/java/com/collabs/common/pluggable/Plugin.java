package com.collabs.common.pluggable;

import com.collabs.common.model.data.Packet;

/**
 * @author Aleksey A.
 */
public interface Plugin {
    int getId();
    String getName();
    void init();
    void invoke(Packet packet);
}
