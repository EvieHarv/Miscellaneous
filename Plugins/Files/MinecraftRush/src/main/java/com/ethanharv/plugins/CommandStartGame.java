package com.ethanharv.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandStart
 */
public class CommandStartGame implements CommandExecutor 
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
        {
            Game game = new Game();
            game.Player = (Player) sender;
            game.StartGame();
        }
        return true;
    }
}