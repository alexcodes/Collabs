package com.collabs.common.pluggable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Aleksey A.
 */
public class PluginInfo {
    private static final String PROPERTIES_NAME = "plugin.properties";
    private static final String MAIN_CLASS_PROPERTY = "main.class";
    private static final String PLUGIN_NAME_PROPERTY = "plugin.name";
    private static final String PLUGIN_VERSION_PROPERTY = "plugin.version";

    private Plugin plugin;
    private String name;
    private String version;

    public PluginInfo(File jarFile) throws PluginLoadException {
        try {
            init(jarFile);
        } catch (IOException e) {
            throw new PluginLoadException(e);
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    private void init(File jarFile) throws IOException, PluginLoadException {
        Properties properties = getProperties(jarFile);
        if (properties == null) {
            throw new PluginLoadException("No properties file found");
        }
        String mainClassName = getProperty(MAIN_CLASS_PROPERTY, properties, true, true);
        name = getProperty(PLUGIN_NAME_PROPERTY, properties);
        version = getProperty(PLUGIN_VERSION_PROPERTY, properties);
        URL jarURL = jarFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
        Class pluginClass;
        try {
            pluginClass = classLoader.loadClass(mainClassName);
            plugin = (Plugin) pluginClass.newInstance();
        } catch (Exception e) {
            throw new PluginLoadException(e);
        }
    }

    private String getProperty(String propertyName, Properties properties) throws PluginLoadException {
        return getProperty(propertyName, properties, false, false);
    }

    private String getProperty(String propertyName, Properties properties, boolean notNull, boolean notEmpty)
            throws PluginLoadException {
        String value = properties.getProperty(propertyName);
        if (notNull && value == null) {
            throw new PluginLoadException("Cannot find property \"" + propertyName + "\"");
        }
        if (notEmpty && value.isEmpty()) {
            throw new PluginLoadException("Property \"" + propertyName + "\" has no value");
        }
        return value;
    }

    private Properties getProperties(File file) throws IOException {
        Properties result = null;
        JarFile jar = new JarFile(file);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().equals(PROPERTIES_NAME)) {
                InputStream is = null;
                try {
                    is = jar.getInputStream(entry);
                    result = new Properties();
                    result.load(is);
                } finally {
                    if (is != null)
                        is.close();
                }
            }
        }
        return result;
    }
}
