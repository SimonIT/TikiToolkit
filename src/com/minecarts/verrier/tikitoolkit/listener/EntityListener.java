package com.minecarts.verrier.tikitoolkit.listener;

import com.minecarts.verrier.tikitoolkit.TikiToolkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener {
	private TikiToolkit plugin;

	public EntityListener(TikiToolkit instance) {
		plugin = instance;
	}

	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			//Check to see if their drops are off
			if (!(plugin.config.getBoolean("admins." + player.getName() + ".drop_items", true))) {
				if (!player.isOp() && plugin.config.getBoolean("admins." + player.getName() + ".op_only", false)) {
					return;
				}
				event.getDrops().clear();
			}
		}
	}

}
