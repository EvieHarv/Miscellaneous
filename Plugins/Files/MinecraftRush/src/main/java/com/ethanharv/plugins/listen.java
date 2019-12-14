package com.ethanharv.plugins;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * listen
 */
public class listen implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("welcome " + event.getPlayer().getName().toLowerCase() + " my dude");
    }
}