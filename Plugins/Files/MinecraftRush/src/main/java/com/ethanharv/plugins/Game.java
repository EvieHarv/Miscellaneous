package com.ethanharv.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Game
 */
public class Game 
{
    public Player Player;
    public String MapId = "";
    public Shard Shard = new Shard();
    public int Attempts = 0; 
    
    public int CurrTime = 0;
    public int BestTime = 0;
    public GameState State = GameState.WAITING;

    public void StartGame()
    {
        this.Shard.StartShard();
        this.Player.teleport(new Location(this.Player.getWorld(), Shard.x, Shard.y, Shard.z));
    }

    public double DistFromStart(double x, double z)
    {
        return Math.sqrt(Math.pow(this.Shard.x-x, 2) + Math.pow(this.Shard.z-z, 2));
    }
}

enum GameState
{
    WAITING, PLAYING, FINISHED;
}