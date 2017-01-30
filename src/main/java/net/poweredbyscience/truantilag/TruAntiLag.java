package net.poweredbyscience.truantilag;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Lax on 1/29/2017.
 */
public class TruAntiLag extends JavaPlugin {

    public ArrayList<String> a = new ArrayList<String>();

    public void onEnable() {
        loadBlacklist();
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().log(Level.WARNING, "Disabling Lag");
                disableLag();
            }
        }.runTaskLater(this, 600); //Make sure all plugins are loaded
    }

    public void loadBlacklist() {
        for (String s : getBlacklistconfig().getKeys(false)) {
            a.add(s.toLowerCase());
        }
    }

    public void disableLag() {
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            if (a.contains(p.getName().toLowerCase())) {
                Bukkit.getPluginManager().disablePlugin(p);
                getLogger().log(Level.WARNING, "Disabled " + p.getName() + " for causing lag");
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
