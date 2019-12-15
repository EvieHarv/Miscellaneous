package com.ethanharv.plugins;
import org.bukkit.plugin.java.JavaPlugin;
public class App extends JavaPlugin 
{
    @Override
    public void onEnable() 
    {
        getLogger().info("MinecraftRush initalizing.");
        getServer().getPluginManager().registerEvents(new HandleEvent(), this);
        this.getCommand("rush").setExecutor(new CommandStartGame());
        this.getCommand("games").setExecutor(new CommandListGames());
        getLogger().info("MinecraftRush initalized.");
    }
    @Override
    public void onDisable() 
    {
        getLogger().info("Disabling MinecraftRush...");
    }
}