package com.ethanharv.plugins;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Game
 */
public class Game {
    public Player player;
    public Map map = new Map();
    public Shard shard = new Shard();
    

    public int attempts = 0;
    public long lastTime = 0; // in millis
    public long bestTime = 0; // in millis - TODO: Grab from db in #StartGame
    public ZonedDateTime lastStartTime = ZonedDateTime.now();
    public GameState state = GameState.WAITING;
    public List<Block> blocks = new ArrayList<Block>();
    public ItemStack userBlock = new ItemStack(Material.WOOL, 1); // TODO: Grab from db, give to player in #StartGame

    public void StartGame(Player player) {
        this.player = player;
        this.map.intializeMap();
        this.shard.StartShard();
        this.player.teleport(new Location(this.player.getWorld(), shard.x, shard.y, shard.z));
    }

    public void StartAttempt() {
        this.state = GameState.PLAYING;
        this.lastStartTime = ZonedDateTime.now();
        this.attempts += 1;
        updateTime();
    }

    public void updateTime() {
        // Wrapped in try-catch so that if game errors occur it can free resources by erroring out. (hopefully? haven't implemented fully yet so we'll see.)
        try { 
            BukkitScheduler sch = Bukkit.getServer().getScheduler();
            sch.scheduleSyncDelayedTask(App.getPlugin(App.class), new Runnable()
            {
                @Override
                public void run() 
                {
                    if (state == GameState.PLAYING) 
                    {
                        String sentTime = String.format("%.3f", ((double)ChronoUnit.MILLIS.between(lastStartTime, ZonedDateTime.now()))/1000);
                        PlayerUI.sendActionBar(player, ChatColor.DARK_AQUA + "Current Time | " + ChatColor.AQUA + sentTime + ChatColor.DARK_AQUA + " | Current Time");
                        updateTime();
                    }   
                    else if (state == GameState.FINISHED)
                    {
                        lastTime = ChronoUnit.MILLIS.between(lastStartTime, ZonedDateTime.now());
                        if (lastTime < bestTime)
                        {
                            bestTime = lastTime;
                        }
                        String sentTime = String.format("%.3f", ((double)lastTime)/1000);
                        PlayerUI.sendActionBar(player, ChatColor.DARK_AQUA + "---===---Finished---===---");
                        player.sendMessage(ChatColor.YELLOW + "=====================================");
                        player.sendMessage(" ");
                        player.sendMessage( ChatColor.DARK_AQUA + "Finished in " + ChatColor.AQUA + sentTime + ChatColor.DARK_AQUA + " seconds!");
                        player.sendMessage(" ");
                        player.sendMessage(ChatColor.YELLOW + "=====================================");
                    }
                    else if (state == GameState.WAITING)
                    {
                        lastTime = 0;
                        // Do nothing else ig.
                    }

                }
            }, 1L);
        } 
        catch (Exception e) 
        {
            // Do things
            System.out.println("Something is erroring out in Game#updateTime:");
            System.out.println(e.toString());
        }
    }

    public void restartGame()
    {
        this.player.teleport(new Location(this.player.getWorld(), this.shard.x, this.shard.y, this.shard.z));
        this.resetBlocks();
        this.state = GameState.WAITING;
    }

    public void finishGame()
    {
        this.state = GameState.FINISHED;
        this.resetBlocks();
        Register.games.remove(this.player.getUniqueId());
    }

    public void resetBlocks()
    {
        if (this.blocks.size() > 0)
        {
            for (Block block : this.blocks)
            {
                block.setType(Material.AIR);
            }
        }
    }

    public boolean inStartArea(double x, double z)
    {
        return !((Math.abs(this.shard.x-x) <= 4.5) && (Math.abs(this.shard.z-z) <= 4.5));
    }

    public void refillItems()
    {
        try 
        { 
            BukkitScheduler sch = Bukkit.getServer().getScheduler();
            sch.scheduleSyncDelayedTask(App.getPlugin(App.class), new Runnable()
            {
                @Override
                public void run() {
                    player.getInventory().addItem(userBlock);
                }
            }, 1L);    
        } catch (Exception e) { System.out.println("Error in BlockPlaceEvent - Waiting."); System.out.println(e.toString()); }
    }

}

enum GameState
{
    WAITING, PLAYING, FINISHED;
}