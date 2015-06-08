package com.collabs.server.command;

import com.collabs.common.pluggable.PluginInfo;
import com.collabs.server.data.PluginStorage;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

/**
 * @author Aleksey A.
 */
public class LoadPlugins implements Command {
    private String dir;

    public LoadPlugins(String dir) {
        this.dir = dir;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute() throws Exception {
        File pluginDir = new File(dir);
        if (! pluginDir.exists()) {
            throw new CommandException("Path: " + dir + "\nCannot load plugins: No such directory");
        }
        if (! pluginDir.isDirectory()) {
            throw new Exception("Path: " + dir + "\nError! Path must be a directory");
        }
        File[] jars = pluginDir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".jar");
            }
        });
        for (File file : jars) {
            LoadPlugin command = new LoadPlugin(file);
            command.execute();
            PluginStorage.get().addItem(command.getPluginInfo());
            System.out.println("Plugin loaded: " + command.getPluginInfo().getName());
        }
        System.out.println("Total: " + jars.length + " plugins were loaded");
    }
}
