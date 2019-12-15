package com.ethanharv.plugins;

import java.util.UUID;

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
            UUID senderUUID = sender.getServer().getPlayer(sender.getName()).getUniqueId(); // idk why I can't do this directly but ok

            if (Register.games.keySet().contains(senderUUID)) 
            {
                sender.sendMessage("Cannot start new match, already in one.");
                return true;
            }
            Game game = new Game();
            Register.games.put(senderUUID, game);
            game.Player = (Player) sender;
            game.StartGame();
        }
        return true;
    }
}