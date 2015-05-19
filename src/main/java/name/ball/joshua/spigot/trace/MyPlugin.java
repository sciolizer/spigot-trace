package name.ball.joshua.spigot.trace;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {

    public void onDisable() {
    }

    public void onEnable() {
        Bukkit.getServer().broadcastMessage("spigot-trace loaded");
    }

}
