package de.castmax1311.katzcraftpets.commands;

import de.castmax1311.katzcraftpets.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Wolf;

public class RidepetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.formatMessage(ChatColor.RED + "This command can only be executed by players!"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0) {
            player.sendMessage(Main.formatMessage("Use: /ridepet"));
            return true;
        }

        for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
            if (entity instanceof Tameable) {
                Tameable tameable = (Tameable) entity;
                if (tameable.isTamed() && tameable.getOwner() instanceof Player && tameable.getOwner().equals(player)) {
                    if (tameable instanceof Wolf || tameable instanceof Cat) {
                        tameable.addPassenger(player);
                        return true;
                    }
                }
            }
        }

        player.sendMessage(Main.formatMessage(ChatColor.RED + "No tamed pet found nearby, or you don't own a rideable pet."));
        return true;
    }
}
