package com.collabs.common.model.event.general;

import com.collabs.common.model.event.Event;

/**
 * @author Aleksey A.
 */
public class InitClientEvent implements GeneralEvent {
    private int idClient;

    public InitClientEvent(int idClient) {
        this.idClient = idClient;
    }

    public int getIdClient() {
        return idClient;
    }
}
