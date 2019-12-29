package com.ethanharv.plugins;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandLobby
 */
public class CommandLobby implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
        {
            try {
                Game game = Register.games.get(((Player)sender).getUniqueId());
                game.player.teleport(new Location(game.player.getWorld(), .5, 81, .5));
                game.clearGame();
                Register.games.remove(game.player.getUniqueId());
            } catch (Exception e) 
            {
                sender.sendMessage("You don't currently have a game.");
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}