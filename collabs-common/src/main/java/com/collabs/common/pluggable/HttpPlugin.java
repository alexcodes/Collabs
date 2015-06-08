package com.collabs.common.pluggable;

/**
 * @author Aleksey A.
 */
public interface HttpPlugin extends Plugin {
    void setRequest(String request);
    String getResponse();
}
