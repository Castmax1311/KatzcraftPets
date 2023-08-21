package de.castmax1311.katzcraftpets.commands;

import de.castmax1311.katzcraftpets.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SaddleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.isOp()) {
                player.sendMessage(Main.formatMessage(ChatColor.RED + "You don't have permission to execute this command!"));
                return true;
            }

            double maxDistanceSquared = 2 * 2;

            for (Entity entity : player.getNearbyEntities(maxDistanceSquared, maxDistanceSquared, maxDistanceSquared)) {
                if (entity instanceof Horse) {
                    Horse horse = (Horse) entity;

                    if (horse.isTamed() && horse.getOwner() != null && horse.getOwner().equals(player)) {
                        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                        player.sendMessage(Main.formatMessage(ChatColor.GREEN + "You put a saddle on the animal"));
                        return true;
                    }
                }
            }

            player.sendMessage(Main.formatMessage(ChatColor.RED + "No suitable animal found nearby"));
        }

        return true;
    }
}