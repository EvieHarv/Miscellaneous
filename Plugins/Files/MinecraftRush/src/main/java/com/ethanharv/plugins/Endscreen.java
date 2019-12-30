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
        game.spawned.add((Entity) as);
        ArmorStand bas = (ArmorStand) game.player.getWorld().spawnEntity(game.player.getLocation().add(2, 1, 2), EntityType.ARMOR_STAND);
        bas.setGravity(false);
        bas.setCanPickupItems(false);
        bas.setCustomName(ChatColor.RED + "Return To Lobby  (/lobby)");
        bas.setCustomNameVisible(false);
        bas.setVisible(false);
        game.spawned.add((Entity) bas);



        ArmorStand as2 = (ArmorStand) game.player.getWorld().spawnEntity(game.player.getLocation().add(-2, 0, 2), EntityType.ARMOR_STAND);
        as2.setGravity(false);
        as2.setCanPickupItems(false);
        as2.setCustomName(ChatColor.GREEN + "Replay Map  (/rs)");
        as2.setCustomNameVisible(true);
        as2.setVisible(false);
        game.spawned.add((Entity) as2);
        ArmorStand bas2 = (ArmorStand) game.player.getWorld().spawnEntity(game.player.getLocation().add(-2, 1, 2), EntityType.ARMOR_STAND);
        bas2.setGravity(false);
        bas2.setCanPickupItems(false);
        bas2.setCustomName(ChatColor.GREEN + "Replay Map  (/rs)");
        bas2.setCustomNameVisible(false);
        bas2.setVisible(false);
        game.spawned.add((Entity) bas2);

    }
}