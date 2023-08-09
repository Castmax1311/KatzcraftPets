package de.castmax1311.katzcraftpets.commands;

import de.castmax1311.katzcraftpets.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

public class TameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                double maxDistanceSquared = 5 * 5;
                Entity closestTameable = null;
                double closestDistanceSquared = Double.MAX_VALUE;

                for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                    if (entity instanceof Tameable) {
                        Tameable tameable = (Tameable) entity;
                        if (!tameable.isTamed()) {
                            double distanceSquared = entity.getLocation().distanceSquared(player.getLocation());
                            if (distanceSquared <= maxDistanceSquared && distanceSquared < closestDistanceSquared) {
                                closestTameable = entity;
                                closestDistanceSquared = distanceSquared;
                            }
                        }
                    }
                }

                if (closestTameable != null) {
                    Tameable tameable = (Tameable) closestTameable;
                    tameable.setTamed(true);
                    tameable.setOwner(player);
                    player.sendMessage(Main.formatMessage(ChatColor.GREEN + "Untamed animal successfully tamed!"));
                } else {
                    player.sendMessage(Main.formatMessage(ChatColor.RED + "No untamed animal found nearby"));
                }
            } else {
                player.sendMessage(Main.formatMessage(ChatColor.RED + "You don't have permission to execute this command!"));
            }
        } else {
            sender.sendMessage(Main.formatMessage(ChatColor.RED + "This command can only be executed by players!"));
        }
        return true;
    }
}
