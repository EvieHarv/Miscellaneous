package com.ethanharv.plugins;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * listen
 */
public class HandleEvent implements Listener
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
            else if (game.State == GameState.PLAYING && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.PRISMARINE)
            {
                game.State = GameState.FINISHED;
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