package com.minecarts.verrier.tikitoolkit;

import com.minecarts.verrier.tikitoolkit.helper.StringHelper;
import com.minecarts.verrier.tikitoolkit.listener.EntityListener;
import com.minecarts.verrier.tikitoolkit.listener.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class TikiToolkit extends JavaPlugin {

	public final Logger log = Logger.getLogger("Minecraft");
	private PlayerListener playerListener;
	private EntityListener entityListener;

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();

		//Set up our listeners
		playerListener = new PlayerListener(this);
		entityListener = new EntityListener(this);

		pm.registerEvents(this.playerListener, this);
		pm.registerEvents(this.entityListener, this);

		PluginDescriptionFile pdf = getDescription();
		this.log.info("[" + pdf.getName() + "] version " + pdf.getVersion() + " enabled.");
	}

	public void onDisable() {

	}


	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (cmdLabel.toLowerCase().equals("tiki")) {
			if (args.length > 0) {
				//Reload the config
				if (args[0].equalsIgnoreCase("reload")) {
					//If a player issued the command
					if (sender instanceof Player) {
						//Check to see if they're an op
						if (sender.isOp()) {
							this.reloadConfig();
						}
						sender.sendMessage("TikiToolkit config reloaded.");
					} else {
						//Console issued the command
						this.reloadConfig();
					}
					log.info("TikiToolkit config reloaded.");
					return true;
					//Identify what item you have in hand
				} else if (args[0].equalsIgnoreCase("identify")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage("Tiki: You are holding: " + ChatColor.GOLD + player.getInventory().getItemInMainHand().getType().name());
						return true;
					}
					//Give the player their tools
				} else if (args[0].equalsIgnoreCase("tools")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;

						if (!player.isOp() && getConfig().getBoolean("admins." + player.getUniqueId().toString() + ".op_only", false)) {
							return false;
						}

						Runnable setInventory = playerListener.new setInventory(player);
						getServer().getScheduler().scheduleSyncDelayedTask(this, setInventory, 1);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("bind")) {
					if (args.length > 2) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							getConfig().set("admins." + player.getUniqueId().toString() + ".slot_" + player.getInventory().getHeldItemSlot() + ".type", player.getInventory().getItemInMainHand().getType().toString());

							if (args[1].equalsIgnoreCase("click_left") || args[1].equalsIgnoreCase("click_right")) {
								//Check to see if there are multiple args after this, and if so, try to figure out how to break it apart
								//  for now, we're just going to assume it's a single command (eg no chaining (yet))

								String combinedArgs = StringHelper.join(args, 2);

								getConfig().set("admins." + player.getUniqueId().toString() + ".slot_" + player.getInventory().getHeldItemSlot() + "." + args[1].toLowerCase(), combinedArgs);
							} else {
								player.sendMessage("Please use click_left or click_right to define the click action.");
							}
							this.saveConfig();
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
