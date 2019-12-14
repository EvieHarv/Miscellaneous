package com.ethanharv.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Game
 */
public class Game 
{
    public Player Player;
    public String mapId;
    public Shard Shard;
    public int Attempts; 
    
    public int CurrTime;
    public int BestTime;

    public void StartGame() 
    {
        this.Shard.NewShard();
        this.Player.teleport(new Location(this.Player.getWorld(), Shard.x, Shard.y, Shard.z));
    }
}