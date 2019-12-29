package com.ethanharv.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        if (!(sender instanceof Player))
        {
            return false;
        }

        if (args.length == 0 && Register.games.containsKey(((Player) sender).getUniqueId())) // Send sender info about own game
        {
            sendGameMessage((Player) sender, Register.games.get(((Player) sender).getUniqueId()));
            return true;
        }
        else if (args.length != 1)
        {
            return false;
        }

        if (args[0].toLowerCase().equals("list"))
        {
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.GREEN + "Games:");
            sender.sendMessage(ChatColor.YELLOW + "=====================================");
            sender.sendMessage(" ");
            for(Game game : Register.games.values().toArray(new Game[0]))
            {
                sender.sendMessage(ChatColor.LIGHT_PURPLE + game.player.getName() + "'s game.");
            }
            return true;
        }

        try {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            Game game = Register.games.get(player.getUniqueId());
            if (game == null) 
            {
                sender.sendMessage(ChatColor.RED + "No game found for player \"" + args[0] + "\"");
                return false;
            }
            // TODO: Check for permissions
            sendGameMessage((Player) sender, game);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "No game found for player \"" + args[0] + "\"");
            return false;
        }

        return true;
    }

    public static void sendGameMessage(Player sender, Game game)
    {
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GREEN + "Game Info:");
        sender.sendMessage(ChatColor.YELLOW + "=====================================");
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.DARK_GREEN + "Player:           " + ChatColor.GRAY + game.player.getName());
        sender.sendMessage(ChatColor.DARK_GREEN + "Map:                " + ChatColor.GRAY + game.map.name);
        sender.sendMessage(ChatColor.DARK_GREEN + "Attempts:        " + ChatColor.GRAY + game.attempts);
        sender.sendMessage(ChatColor.DARK_GREEN + "Best Time:       " + ChatColor.GRAY + (game.bestTime == 0 ? " " : Game.normalizeTime(game.bestTime) + "s")); // TODO: normalize
        sender.sendMessage(ChatColor.DARK_GREEN + "Last Time:       " + ChatColor.GRAY + (game.lastTime == 0 ? " " : Game.normalizeTime(game.lastTime) + "s")); // TODO: normalize
    }

}