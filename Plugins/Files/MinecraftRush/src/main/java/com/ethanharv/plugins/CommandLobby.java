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
            Player player = (Player) sender;
            player.teleport(new Location(player.getWorld(), .5, 81, .5));
            try {
                Game game = Register.games.get(player.getUniqueId());
                game.clearGame();
                Register.games.remove(game.player.getUniqueId());
            } catch (Exception e) {}
            return true;
        }
        else
        {
            return false;
        }
    }
}