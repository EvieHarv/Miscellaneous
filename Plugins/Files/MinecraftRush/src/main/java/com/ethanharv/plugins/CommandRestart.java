package com.ethanharv.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandRestart
 */
public class CommandRestart implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
        {
            try {
                Game game = Register.games.get(((Player)sender).getUniqueId());
                game.restartGame();
                PlayerUI.sendActionBar(game.player, " ");
            } catch (Exception e) {
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