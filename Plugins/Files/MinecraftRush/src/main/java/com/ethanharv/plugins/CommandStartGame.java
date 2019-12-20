package com.ethanharv.plugins;

import java.util.UUID;

import org.bukkit.Bukkit;
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
            try {
                UUID senderUUID = ((Player) sender).getUniqueId();

                if (Register.games.keySet().contains(senderUUID)) 
                {
                    sender.sendMessage("Cannot start new match, already in one.");
                    return true;
                }
                Game game = new Game();
                Register.games.put(senderUUID, game);
                game.StartGame((Player) sender);
    
            } catch (Exception e) { Bukkit.getServer().getLogger().info("ERROR: Couldn't find player in CommandStartGame : " + e.getMessage());}

        }
        return true;
    }
}