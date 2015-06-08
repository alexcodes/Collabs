package com.collabs.common.model.event.general;

/**
 * @author Aleksey A.
 */
public class ClientInfoEvent implements GeneralEvent {
    private int idClient;
    private String name;

    public ClientInfoEvent(int idClient, String name) {
        this.idClient = idClient;
        this.name = name;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getName() {
        return name;
    }
}
