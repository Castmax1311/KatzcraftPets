package de.castmax1311.katzcraftpets.commands;

import de.castmax1311.katzcraftpets.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GivepetCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.formatMessage(ChatColor.RED + "This command can only be executed by players!"));
            return true;
        }

        if (args.len
            sender.sendMessage(Main.formatMessage("Use: /givepet <petname> <player>"));
            return true;
        }

        String petName = args[0];
        String playerName = args[1];

        Player targetPlayer = Main.instance.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(Main.formatMessage(ChatColor.RED + "Player not found or not online"));
            return true;
        }

        Entity petEntity = null;
        for (Entity entity : targetPlayer.getWorld().getEntities()) {
            if (entity instanceof Tameable) {
                Tameable tameable = (Tameable) entity;
                if (tameable.getOwner() instanceof Player) {
                    Player owner = (Player) tameable.getOwner();
                    if (owner.equals((Player) sender) && tameable.getCustomName() != null
                            && tameable.getCustomName().equalsIgnoreCase(petName)) {
                        petEntity = tameable;
                        break;
                    }
                }
            }
        }

        if (petEntity == null) {
            sender.sendMessage(Main.formatMessage(ChatColor.RED + "You don't own a pet named " + petName));
            return true;
        }

        Tameable tameable = (Tameable) petEntity;
        tameable.setOwner(targetPlayer);

        sender.sendMessage(Main.formatMessage(ChatColor.GREEN + "Pet " + petName + " was given to " + targetPlayer.getName()));
        targetPlayer.sendMessage(Main.formatMessage(ChatColor.GREEN + "You have received the pet " + petName));

        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            Player player = (Player) sender;
            for (Entity entity : player.getWorld().getEntities()) {
                if (entity instanceof Tameable) {
                    Tameable tameable = (Tameable) entity;
                    if (tameable.getOwner() instanceof Player) {
                        Player owner = (Player) tameable.getOwner();
                        if (owner.equals(player) && tameable.getCustomName() != null) {
                            completions.add(tameable.getCustomName());
                        }
                    }
                }
            }

            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        } else if (args.length == 2) {
            List<String> completions = new ArrayList<>();

            for (Player onlinePlayer : Main.instance.getServer().getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }

            return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}