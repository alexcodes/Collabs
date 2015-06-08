package com.collabs.common.model.event.general;

import java.util.Map;

/**
 * @author Aleksey A.
 */
public class RefreshClientInfoEvent implements GeneralEvent {
    private Map<Integer, String> info;

    public RefreshClientInfoEvent(Map<Integer, String> info) {
        this.info = info;
    }

    public Map<Integer, String> getInfo() {
        return info;
    }
}
