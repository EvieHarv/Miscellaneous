package com.ethanharv.plugins;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    public long BestTime = 0; // in millis
    public ZonedDateTime lastStartTime = ZonedDateTime.now();
    public GameState State = GameState.WAITING;

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
        Player plr = this.Player;
        ZonedDateTime LastStart = this.lastStartTime;
        GameState state = this.State;
        BukkitScheduler sch = Bukkit.getServer().getScheduler();
        sch.scheduleSyncDelayedTask(App.getPlugin(App.class), new Runnable()
        {
            @Override
            public void run() {
                plr.sendMessage(String.valueOf(((double)ChronoUnit.MILLIS.between(LastStart, ZonedDateTime.now()))/1000));
                if (state == GameState.PLAYING) 
                {
                    updateTime();
                }
                else
                {
                    LastTime = ChronoUnit.MILLIS.between(LastStart, ZonedDateTime.now());
                    if (LastTime < BestTime)
                    {
                        BestTime = LastTime;
                    } 
                    plr.sendMessage("Finished in " + ((double)LastTime)/1000 + " seconds!");
                }
            }
        }, 1L);
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