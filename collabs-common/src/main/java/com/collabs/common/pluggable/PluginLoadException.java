package com.collabs.common.pluggable;

/**
 * @author Aleksey A.
 */
public class PluginLoadException extends Exception {
    public PluginLoadException(String message) {
        super(message);
    }

    public PluginLoadException(Throwable cause) {
        super(cause);
    }
}
