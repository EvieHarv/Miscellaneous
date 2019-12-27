package com.ethanharv.plugins;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.md_5.bungee.api.ChatColor;

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
            Game game = Register.games.get(event.getPlayer().getUniqueId());

            // Checks if player is in start area
            if (game.state == GameState.WAITING && game.inStartArea(event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getZ())) 
            {
                game.StartAttempt();
            }
            // Is player finished?
            else if (game.state == GameState.PLAYING && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.PRISMARINE)
            {
                game.finishGame();
            }
            // Player Has Fallen off of map.
            else if (game.state == GameState.PLAYING && event.getPlayer().getLocation().getY() < game.map.minY) 
            {
                PlayerUI.sendActionBar(event.getPlayer(), ChatColor.YELLOW + "Map failed, restarting...");
                game.restartGame();
            }
        } 
        catch (Exception e) {}
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event)
    {
        if (Register.games.keySet().contains(event.getPlayer().getUniqueId())){
            try 
            {
                Game game = Register.games.get(event.getPlayer().getUniqueId());
                if (game.state == GameState.WAITING)
                {
                    game.StartAttempt();
                    game.blocks.add(event.getBlock());
                    game.refillItems();
                }
                else if (game.state == GameState.PLAYING)
                {
                    game.blocks.add(event.getBlock());
                    game.refillItems();
                }
            } 
            catch (Exception e) {}
        }
        else // They don't have a game
        {
            // TODO: Check for perms for being allowed to place block. If not, cancel event.
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event)
    {
        event.setCancelled(true);
    }
}