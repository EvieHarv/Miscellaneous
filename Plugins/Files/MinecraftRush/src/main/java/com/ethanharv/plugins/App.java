package com.ethanharv.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
public class App extends JavaPlugin 
{
    @Override
    public void onEnable() 
    {
        getLogger().info("MinecraftRush initalizing.");
        getServer().getPluginManager().registerEvents(new HandleEvent(), this);
        this.getCommand("rush").setExecutor(new CommandStartGame());
        this.getCommand("game").setExecutor(new CommandListGames());
        this.getCommand("lobby").setExecutor(new CommandLobby());
        this.getCommand("rs").setExecutor(new CommandRestart());
        getLogger().info("MinecraftRush initalized.");
    }
    @Override
    public void onDisable() 
    {
        for (Game game : Register.games.values())
        {
            for (Entity e : game.spawned)
            {
                e.remove();
            }
            game.player.getInventory().clear();
            game.player.teleport(new Location(game.player.getWorld(), .5, 81, .5));
        }
        getLogger().info("Disabling MinecraftRush...");
    }
}