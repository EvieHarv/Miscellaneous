package com.ethanharv.plugins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * listen
 */
public class handleEvent implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        // get game instance
        try 
        {
            Game game = Register.games.get(event.getPlayer().getName());
            if (game.State == GameState.WAITING && game.inStartArea(event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getZ()))
            {
                event.getPlayer().sendMessage("Starting");
                game.StartAttempt();
            }
        } 
        catch (Exception e) {}
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event)
    {
        try 
        {
            Game game = Register.games.get(event.getPlayer().getName());
            if (game.State == GameState.WAITING)
            {
                event.getPlayer().sendMessage("Starting");
                game.StartAttempt();
            }
        } 
        catch (Exception e) {}
    }
}