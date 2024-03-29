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
import org.bukkit.entity.Entity;
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
    public List<Entity> spawned = new ArrayList<Entity>();
    public ItemStack userBlock = new ItemStack(Material.WOOL, 1); // TODO: Grab from db
    public int userSlot = 4; // TODO: grab from db

    public void StartGame(Player player) {
        this.player = player;
        this.map.intializeMap();
        this.shard.StartShard();
        this.player.teleport(new Location(this.player.getWorld(), shard.x, shard.y, shard.z));
        this.player.getInventory().clear();
        ItemStack block64 = userBlock.clone(); block64.setAmount(64); // Duplicate userBlock exactly, but as *64
        this.player.getInventory().setItem(this.userSlot, block64);
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
                    if (!Register.games.containsKey(player.getUniqueId()))
                    {
                        PlayerUI.sendActionBar(player, " ");
                        return;
                    }
                    if (state == GameState.PLAYING) 
                    {
                        String sentTime = normalizeTime(ChronoUnit.MILLIS.between(lastStartTime, ZonedDateTime.now()));
                        PlayerUI.sendActionBar(player, ChatColor.DARK_AQUA + "Current Time | " + ChatColor.AQUA + sentTime + ChatColor.DARK_AQUA + " | Current Time");
                        updateTime();
                    }   
                    else if (state == GameState.FINISHED)
                    {
                        lastTime = ChronoUnit.MILLIS.between(lastStartTime, ZonedDateTime.now());
                        if (bestTime > lastTime || bestTime == 0)
                        {
                            bestTime = lastTime;
                        }
                        String sentTime = normalizeTime(lastTime);
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
        for (Entity e : spawned){ e.remove(); }
        this.resetBlocks();
        this.player.getInventory().clear();
        ItemStack block64 = userBlock.clone(); block64.setAmount(64); // Duplicate userBlock exactly, but as *64
        this.player.getInventory().setItem(this.userSlot, block64);
        this.state = GameState.WAITING;
    }

    public void finishGame()
    {
        this.state = GameState.FINISHED;
        this.resetBlocks();
        this.player.getInventory().clear();
        Endscreen.Show(this);
    }

    public void clearGame()
    {
        // TODO: reset shard
        this.resetBlocks();
        this.player.getInventory().clear();
        for (Entity e : spawned)
        {
            e.remove();
        }
        Register.games.remove(player.getUniqueId());
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


    public static String normalizeTime(Long time) // TODO: Handle Min/Sec
    {
        return String.format("%.3f", ((double)time)/1000);
    }
}

enum GameState
{
    WAITING, PLAYING, FINISHED;
}