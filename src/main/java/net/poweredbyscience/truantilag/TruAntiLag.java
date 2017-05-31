package net.poweredbyscience.truantilag;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by Lax on 1/29/2017.
 */
public class TruAntiLag extends JavaPlugin {

    private ArrayList<String> a = new ArrayList<String>();

    public void onEnable() {
        loadBlacklist();
        getLogger().log(Level.WARNING, "Cracked by LaxWasHere@SpigotMC");
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().log(Level.WARNING, "Disabling Lag");
                disableLag();
            }
        }.runTaskLater(this, 600); //Make sure all plugins are loaded
    }

    public void loadBlacklist() {
        for (String s : getBlacklistconfig().getConfigurationSection("name").getKeys(false)) {
            a.add(s.toLowerCase());
        }
        for (String s : getBlacklistconfig().getConfigurationSection("authors").getKeys(false)) {
            a.add(s.toLowerCase());
        }
        for (String s : getBlacklistconfig().getConfigurationSection("mains").getKeys(false)) {
            a.add(s.replace("+",".").toLowerCase());
        }
    }

    public void disableLag() {
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            PluginDescriptionFile pds = p.getDescription();

            if (a.contains(pds.getName().toLowerCase()) && p.isEnabled()) {
                Bukkit.getPluginManager().disablePlugin(p);
            }

            if(a.contains(pds.getMain()) && p.isEnabled()) {
                Bukkit.getPluginManager().disablePlugin(p);
            }

            for (String s : pds.getAuthors()) {
                if (a.contains(s.toLowerCase()) && p.isEnabled()) {
                    Bukkit.getPluginManager().disablePlugin(p);
                }
            }
        }
    }

    public FileConfiguration getBlacklistconfig() {
        try {
            FileConfiguration blacklist = new YamlConfiguration();
            blacklist.load(getClassLoader().getResourceAsStream("laggers.yml"));
            return blacklist;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
