package com.ethanharv.plugins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
            if (game.DistFromStart(event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getZ()) > 4.5)
            {
                event.getPlayer().sendMessage("too far smh");
            }
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }
    }
}