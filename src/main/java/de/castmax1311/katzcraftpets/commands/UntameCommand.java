package de.castmax1311.katzcraftpets.commands;

import de.castmax1311.katzcraftpets.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

public class UntameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                double maxDistanceSquared = 5 * 5;
                Entity closestTameable = null;
                double closestDistanceSquared = Double.MAX_VALUE;

                for (Entity entity : player.getNearbyEntities(1, 1, 1)) {
                    if (entity instanceof Tameable) {
                        Tameable tameable = (Tameable) entity;
                        if (tameable.isTamed() && tameable.getOwner() instanceof AnimalTamer) {
                            AnimalTamer owner = (AnimalTamer) tameable.getOwner();
                            if (owner.getUniqueId().equals(player.getUniqueId())) {
                                double distanceSquared = entity.getLocation().distanceSquared(player.getLocation());
                                if (distanceSquared <= maxDistanceSquared && distanceSquared < closestDistanceSquared) {
                                    closestTameable = entity;
                                    closestDistanceSquared = distanceSquared;
                                }
                            }
                        }
                    }
                }

                if (closestTameable != null) {
                    Tameable tameable = (Tameable) closestTameable;
                    tameable.setTamed(false);
                    tameable.setOwner(null);
                    player.sendMessage(Main.formatMessage(ChatColor.GREEN + "Tamed animal successfully untamed!"));
                } else {
                    player.sendMessage(Main.formatMessage(ChatColor.RED + "No tamed animal found nearby or you are not the owner"));
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
