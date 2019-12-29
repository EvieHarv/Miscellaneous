package com.ethanharv.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ListGames
 */
public class CommandListGames implements CommandExecutor 
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            player.sendMessage("Games:");
            for(Game game : Register.games.values().toArray(new Game[0]))
            {
                player.sendMessage(game.player.getName() + "'s game.");
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}