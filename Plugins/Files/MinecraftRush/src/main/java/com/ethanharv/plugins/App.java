package com.ethanharv.plugins;
import org.bukkit.plugin.java.JavaPlugin;
public class App extends JavaPlugin 
{
    @Override
    public void onEnable() 
    {
        getLogger().info("MinecraftRush initalizing.");
        getServer().getPluginManager().registerEvents(new Listen(), this);
        this.getCommand("rush").setExecutor(new CommandStartGame());
    }
    @Override
    public void onDisable() 
    {
        getLogger().info("See you again, SpigotMC!");
    }
}