package com.ethanharv.plugins;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Game
 */
public class Game {
    public Player Player;
    public String MapId = "";
    public Shard Shard = new Shard();
    

    public int Attempts = 0;
    public long LastTime = 0; // in millis
    public long BestTime = 0; // in millis - TODO: Grab from db in #StartGame
    public ZonedDateTime lastStartTime = ZonedDateTime.now();
    public GameState State = GameState.WAITING;
    public List<Block> Blocks = new ArrayList<Block>();

    public void StartGame() {
        this.Shard.StartShard();
        this.Player.teleport(new Location(this.Player.getWorld(), Shard.x, Shard.y, Shard.z));
    }

    public void StartAttempt() {
        this.State = GameState.PLAYING;
        this.lastStartTime = ZonedDateTime.now();
        updateTime();
    }

    public void updateTime() {
        // Wrapped in try-catch so that if player leaves it can free resources by erroring out. (hopefully? haven't implemented fully yet so we'll see.)
        try { 
            BukkitScheduler sch = Bukkit.getServer().getScheduler();
            sch.scheduleSyncDelayedTask(App.getPlugin(App.class), new Runnable()
            {
                @Override
                public void run() {

                    if (State == GameState.PLAYING) 
                    {
                        Player.sendMessage(String.valueOf(((double)ChronoUnit.MILLIS.between(lastStartTime, ZonedDateTime.now()))/1000));
                        updateTime();
                    }
                    else if (State == GameState.FINISHED)
                    {
                        LastTime = ChronoUnit.MILLIS.between(lastStartTime, ZonedDateTime.now());
                        if (LastTime < BestTime)
                        {
                            BestTime = LastTime;
                        } 
                        Player.sendMessage("Finished in " + ((double)LastTime)/1000 + " seconds!");
                    }
                    else if (State == GameState.WAITING)
                    {
                        LastTime = 0;
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
        this.Player.teleport(new Location(this.Player.getWorld(), this.Shard.x, this.Shard.y, this.Shard.z));
        this.State = GameState.WAITING;
        this.resetBlocks();
    }

    public void finishGame()
    {
        this.State = GameState.FINISHED;
        this.resetBlocks();
        Register.games.remove(this.Player.getUniqueId());
    }

    public void resetBlocks()
    {
        if (this.Blocks.size() > 0)
        {
            for (Block block : this.Blocks)
            {
                block.setType(Material.AIR);
            }
        }
    }

    public boolean inStartArea(double x, double z)
    {
        return !((Math.abs(this.Shard.x-x) <= 4.5) && (Math.abs(this.Shard.z-z) <= 4.5));
    }
}

enum GameState
{
    WAITING, PLAYING, FINISHED;
}