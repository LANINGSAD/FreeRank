package koala.dev.freerank.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileConfig {
    private final File file;

    private FileConfiguration configuration;

    public FileConfig(JavaPlugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            if (plugin.getResource(fileName) == null) {
                try {
                    this.file.createNewFile();
                } catch (IOException var4) {
                    plugin.getLogger().severe("Failed to create new file " + fileName);
                }
            } else {
                plugin.saveResource(fileName, false);
            }
        }
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }

    public double getDouble(String path) {
        return this.configuration.contains(path) ? this.configuration.getDouble(path) : 0.0D;
    }

    public int getInt(String path) {
        return this.configuration.contains(path) ? this.configuration.getInt(path) : 0;
    }

    public boolean getBoolean(String path) {
        return this.configuration.contains(path) ? this.configuration.getBoolean(path) : false;
    }

    public long getLong(String path) {
        return this.configuration.contains(path) ? this.configuration.getLong(path) : 0L;
    }

    public String getString(String path) {
        return this.configuration.contains(path) ? ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path)) : null;
    }

    public String getString(String path, String callback, boolean colorize) {
        if (this.configuration.contains(path))
            return colorize ? ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path)) : this.configuration.getString(path);
        return callback;
    }

    public List<String> getReversedStringList(String path) {
        List<String> list = getStringList(path);
        if (list == null)
            return Arrays.asList(new String[] { "ERROR: STRING LIST NOT FOUND!" });
        int size = list.size();
        List<String> toReturn = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--)
            toReturn.add(list.get(i));
        return toReturn;
    }

    public List<String> getStringList(String path) {
        if (!this.configuration.contains(path))
            return Arrays.asList(new String[] { "ERROR: STRING LIST NOT FOUND!" });
        ArrayList<String> strings = new ArrayList<>();
        Iterator<String> var3 = this.configuration.getStringList(path).iterator();
        while (var3.hasNext()) {
            String string = var3.next();
            strings.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return strings;
    }

    public List<String> getStringListOrDefault(String path, List<String> toReturn) {
        if (!this.configuration.contains(path))
            return toReturn;
        ArrayList<String> strings = new ArrayList<>();
        Iterator<String> var4 = this.configuration.getStringList(path).iterator();
        while (var4.hasNext()) {
            String string = var4.next();
            strings.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return strings;
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException var2) {
            Bukkit.getLogger().severe("Could not save config file " + this.file.toString());
            var2.printStackTrace();
        }
    }

    public void reload() {
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }
}