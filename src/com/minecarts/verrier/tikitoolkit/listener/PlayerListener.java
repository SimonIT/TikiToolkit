package com.minecarts.verrier.tikitoolkit.listener;

import com.minecarts.verrier.tikitoolkit.TikiToolkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class PlayerListener implements Listener {

	private final TikiToolkit plugin;

	public PlayerListener(TikiToolkit instance) {
		plugin = instance;
	}


	@EventHandler
	public void onPlayerJoin(PlayerEvent event) {
		//Do we care?
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
			this.doToolCmd(event.getPlayer(), "click_left");
		} else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if (this.doToolCmd(event.getPlayer(), "click_right")) {
				//If we performed a command, cancel it so we don't
				// eat a fish, for example
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onItemHeldChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		int slot = event.getNewSlot();
		String name = plugin.getConfig().getString("admins." + player.getUniqueId().toString() + ".slot_" + slot + ".name");
		String type = getToolTypeAtSlot(player, slot);
		if (name != null) {
			//Only display the selected tool message if they have the have the correct item in hand
			ItemStack currentItems = player.getInventory().getItem(slot);
			if (currentItems != null && currentItems.getType().equals(Material.getMaterial(type))) {
				if (plugin.getConfig().getBoolean("admins." + player.getUniqueId().toString() + ".selected_msg", true)) {
					player.sendMessage(String.format("Tiki: %s%s%s selected", ChatColor.GOLD, name, ChatColor.WHITE));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Runnable setInventory = new setInventory(player);
		//Since we don't have the actual player object that's going to respawn
		//	lets fire off a task to do later? Is this the best way to do it?
		if (!player.isOp() && plugin.getConfig().getBoolean("admins." + player.getUniqueId().toString() + ".op_only", false)) {
			return;
		}
		if (plugin.getConfig().getBoolean("admins." + player.getUniqueId().toString() + ".respawn_wands", false)) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, setInventory, 1);
		}
	}

	private boolean doToolCmd(Player player, String clickType) {
		int slot = player.getInventory().getHeldItemSlot();
		String type = getToolTypeAtSlot(player, slot);

		if (type != null) {
			//Check to see if the item in the hand is their configured wand
			if (player.getInventory().getItemInMainHand().getType() == Material.getMaterial(type)) {
				//Try to load the commands as a list
				List<String> cmds = plugin.getConfig().getStringList("admins." + player.getUniqueId().toString() + ".slot_" + slot + "." + clickType);
				if (cmds.size() > 0) {
					Iterator<String> itr = cmds.iterator();
					while (itr.hasNext()) {
						String cmd = itr.next();//Try to parse it as an integer, if it is, treat it as a delay
						try {
							int delay = Integer.parseInt(cmd);
							ExecuteCommandLater commandExec = new ExecuteCommandLater(player, itr.next());
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, commandExec, delay);
						} catch (Exception e) {
							//Wasn't an int, so it's a string command
							player.chat(cmd);
						}
					}
				} else {
					//Try fetching it as a string
					String cmd = plugin.getConfig().getString("admins." + player.getUniqueId().toString() + ".slot_" + slot + "." + clickType);
					if (cmd != null) {
						player.chat(cmd);
					}
				}
				return true;
			}
		}
		return false;
	}

	private String getToolTypeAtSlot(Player player, int slot) {
		return plugin.getConfig().getString("admins." + player.getUniqueId().toString() + ".slot_" + slot + ".type");
	}

	public class setInventory implements Runnable {
		private final Player player;

		public setInventory(Player player) {
			this.player = player;
		}

		public void run() {
			//Give the players their admin kit on respawn
			player.sendMessage("The tiki gods have restored your admin tools.");
			for (int i = 0; i < 9; i++) {
				String type = getToolTypeAtSlot(player, i);
				if (type != null) {
					//Assign the item
					player.getInventory().setItem(i, new ItemStack(Material.valueOf(type), 1));
				}
			}
		}
	}

	private static class ExecuteCommandLater implements Runnable {
		private final String command;
		private final Player player;

		public ExecuteCommandLater(Player player, String command) {
			this.command = command;
			this.player = player;
		}

		public void run() {
			this.player.chat(command);
		}
	}
}
