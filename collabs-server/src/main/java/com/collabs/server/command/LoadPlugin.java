package com.collabs.server.command;

import com.collabs.common.pluggable.PluginInfo;
import com.collabs.common.pluggable.PluginLoadException;

import java.io.File;

/**
 * @author Aleksey A.
 */
public class LoadPlugin implements Command {
    private File file;
    private PluginInfo pluginInfo;

    public LoadPlugin(File file) {
        this.file = file;
    }

    @Override
    public void execute() throws PluginLoadException {
        pluginInfo = new PluginInfo(file);
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }
}
