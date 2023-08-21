package de.castmax1311.katzcraftpets;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import de.castmax1311.katzcraftpets.commands.*;

public final class Main extends JavaPlugin implements Listener {
    public static Main instance;
    @Override
    public void onLoad() {
        instance = this;
    }
    @Override
    public void onEnable() {
        getLogger().info("KatzcraftPets plugin has been enabled.");
        getCommand("givepet").setExecutor(new GivepetCommand());
        getCommand("ridepet").setExecutor(new RidepetCommand());
        getCommand("tame").setExecutor(new TameCommand());
        getCommand("untame").setExecutor(new UntameCommand());
        getCommand("saddle").setExecutor(new SaddleCommand());

        new UpdateChecker(this, 111864).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("No updates available");
            } else {
                getLogger().info("New update available!");
            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("KatzcraftPets plugin has been disabled.");
    }
    public static String formatMessage(String message) {
        return "[" + ChatColor.BLUE + "KatzcraftPets" + ChatColor.RESET + "] " + message;
    }

}
