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
            if (Register.games.keySet().contains(sender.getName())) 
            {
                sender.sendMessage("bro thats kinda cringe you can't start a new one when you already have one.");
                return false;
            }
            Game game = new Game();
            Register.games.put(sender.getName(), game);
            game.Player = (Player) sender;
            game.StartGame();
        }
        return true;
    }
}