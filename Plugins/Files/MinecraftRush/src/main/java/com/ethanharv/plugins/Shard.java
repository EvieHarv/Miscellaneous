package com.ethanharv.plugins;

/**
 * Shard
 */
public class Shard 
{
    public int id;
    public double x;
    public double y;
    public double z;
    
    /**
     * Placeholder method. Need to actually update with dynamic shard creation. The xyz represents the shard's midpoint at the starting y-level.
     */
    public void StartShard() 
    {
        this.x = 0.5;
        this.y = 80;
        this.z = 36.5;
    }
}