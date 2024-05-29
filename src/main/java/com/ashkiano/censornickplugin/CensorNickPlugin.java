package com.ashkiano.censornickplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CensorNickPlugin extends JavaPlugin implements Listener {

    private List<String> bannedSubstrings;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        bannedSubstrings = config.getStringList("banned-substrings");
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this, 21952);
        this.getLogger().info("Thank you for using the CensorNickPlugin plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName().toLowerCase();
        for (String banned : bannedSubstrings) {
            if (playerName.contains(banned.toLowerCase())) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Your username contains a forbidden word!");
                return;
            }
        }
    }
}
