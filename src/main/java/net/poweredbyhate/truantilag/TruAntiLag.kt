package net.poweredbyhate.truantilag

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import khttp.get
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.json.JSONObject
import java.net.ProxySelector
import java.util.*
import java.util.logging.Level

/**
 * Created by Lax on 1/29/2017.
 * Fixed by Redrield on 2017-10-09
 */
class TruAntiLag : JavaPlugin() {

    private val a = ArrayList<String>()

    companion object {
        lateinit var instance: TruAntiLag
    }

    private val blacklistConfig by lazy {
        get("https://raw.githubusercontent.com/LaxWasHere/TruAntiLag/master/laggers.json").jsonObject.toGson()
    }

    override fun onEnable() {
        instance = this;
        loadBlacklist()
        logger.log(Level.WARNING, "Cracked by LaxWasHere@SpigotMC")
        logger.log(Level.WARNING, "Mega cracked by Redrield@SpigotMC")
        object : BukkitRunnable() {
            override fun run() {
                logger.log(Level.WARNING, "Disabling Lag")
                disableLag()
            }
        }.runTaskLater(this, 600) //Make sure all plugins are loaded
        ProxySelector.setDefault(BadDetector(ProxySelector.getDefault()))
        if (!Bukkit.getVersion().contains("git-Paper")) {
            logger.log(Level.WARNING, "For a TruAntiLag xPerience , use Paper (https://paper.emc.gs)")
        }
    }


    private fun loadBlacklist() {
        blacklistConfig["name"].asJsonObject.entrySet().mapTo(a) { (key, _) -> key.toLowerCase() }
        blacklistConfig["authors"].asJsonObject.entrySet().mapTo(a) { (key, _) -> key.toLowerCase() }
        blacklistConfig["mains"].asJsonObject.entrySet().mapTo(a) { (key, _) -> key.toLowerCase() }
    }

    private fun disableLag() {
        for (p in Bukkit.getPluginManager().plugins) {
            val pds = p.description

            if (a.contains(pds.name.toLowerCase()) && p.isEnabled) {
                Bukkit.getPluginManager().disablePlugin(p)
            }

            if (a.contains(pds.main) && p.isEnabled) {
                Bukkit.getPluginManager().disablePlugin(p)
            }

            pds.authors
                    .filter { a.contains(it.toLowerCase()) && p.isEnabled }
                    .forEach { Bukkit.getPluginManager().disablePlugin(p) }
        }
    }
}

fun JSONObject.toGson(): JsonObject {
    return JsonParser().parse(toString()).asJsonObject

}
