package com.ethanharv.plugins;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import net.md_5.bungee.api.ChatColor;

/**
 * ShowEndscreen
 */
public class Endscreen {

    public static void Show(Game game)
    {
        // TODO: look direction?
        ArmorStand as = (ArmorStand) game.player.getWorld().spawnEntity(game.player.getLocation().add(2, 0, 2), EntityType.ARMOR_STAND);
        as.setGravity(false);
        as.setCanPickupItems(false);
        as.setCustomName(ChatColor.RED + "Return To Lobby  (/lobby)");
        as.setCustomNameVisible(true);
        as.setVisible(false);
        as.setHealth(1);
        game.spawned.add((Entity) as);

        ArmorStand as2 = (ArmorStand) game.player.getWorld().spawnEntity(game.player.getLocation().add(-2, 0, 2), EntityType.ARMOR_STAND);
        as2.setGravity(false);
        as2.setCanPickupItems(false);
        as2.setCustomName(ChatColor.GREEN + "Replay Map  (/rs)");
        as2.setCustomNameVisible(true);
        as2.setVisible(false);
        as2.setHealth(1);
        game.spawned.add((Entity) as2);
    }
}