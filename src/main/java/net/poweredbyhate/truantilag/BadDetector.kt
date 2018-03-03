package net.poweredbyhate.truantilag

import org.bukkit.Bukkit
import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI
import java.util.logging.Level

class BadDetector(var proxySelector: ProxySelector) : ProxySelector() {

    override fun select(uri: URI?): MutableList<Proxy> {
        if (Bukkit.isPrimaryThread()) {
            TruAntiLag.instance.logger.log(Level.WARNING, "Main thread call, Bad!: " + uri.toString());
            TruAntiLag.instance.logger.log(Level.WARNING, "Please notify the developer! https://git.io/vA9m2")
            throw RuntimeException("No touchy touchy my main thready");
        }
        return proxySelector.select(uri);
    }

    override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}