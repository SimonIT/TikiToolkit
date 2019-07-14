package com.minecarts.verrier.tikitoolkit.listener;

import com.minecarts.verrier.tikitoolkit.TikiToolkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {
	private TikiToolkit plugin;

	public EntityListener(TikiToolkit instance) {
		plugin = instance;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			//Check to see if their drops are off
			if (!plugin.getConfig().getBoolean("admins." + player.getUniqueId().toString() + ".drop_items", true)) {
				if (!player.isOp() && plugin.getConfig().getBoolean("admins." + player.getUniqueId().toString() + ".op_only", false)) {
					return;
				}
				event.getDrops().clear();
			}
		}
	}

}
