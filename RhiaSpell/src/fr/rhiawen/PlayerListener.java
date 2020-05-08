package fr.rhiawen;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class PlayerListener implements Listener {
	
	private Plugin plugin;
	private Spell spell;
	
	public PlayerListener(Plugin plugin, Spell spell) {
		this.plugin = plugin;
		this.spell = spell;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		spell.initPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		spell.removePlayer(e.getPlayer());
	}
	
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack stick = new ItemStack(Material.STICK);
		
		if ((action == Action.RIGHT_CLICK_AIR) && (player.getInventory().getItemInMainHand().equals(stick))) { //Player Choice Spell
			spell.changeSpell(player);
		}
		else if ((action == Action.LEFT_CLICK_AIR) && (player.getInventory().getItemInMainHand().equals(stick))) { //Player Left Click + Stick in hand = Spell
			for (Player p : player.getWorld().getPlayers()) {
				if (p.getLocation().distance(player.getLocation()) < 50) {
					p.sendMessage("<" + player.getName() + "> " + spell.getSpell(player).getName());
				}
			}
			new BukkitRunnable() { //Loop during distance != range AND isInAir
				double dist = 0;
				SpellList spellList = spell.getSpell(player);
				int range = spellList.getRange();
				int power = spellList.getPower();
				Location location = player.getLocation();
				boolean first = true;
				Vector direction = location.getDirection().normalize();
				public void run() { //Running Loop
					if (first) { //PlayerEyes Location
						location.setY(location.getY() + 2);
						first = false;
					}
					dist += 0.3;
					location.add(direction.getX() * dist, direction.getY() * dist, direction.getZ() * dist);
					for (Entity e : location.getWorld().getEntities()) { //Check if we have to damage entity
						if (e.getLocation().distance(location) < 2) {
							if (!e.isDead()) {
								LivingEntity entity = (LivingEntity) e;
								if (spellList.equals(SpellList.AVADA)) {
									entity.damage(entity.getHealth());
								}
								else {
									entity.damage(power);
								}			
							}
						}
					}
					
					location.getWorld().spawnParticle(spellList.getParticle(), location, 0);
				
					if (dist > range || !location.getWorld().getBlockAt(location).getBlockData().getMaterial().equals(Material.AIR)) { //Max spell range and isn't in block
						this.cancel();
					}
					
					location.subtract(direction.getX() * dist, direction.getY() * dist, direction.getZ() * dist);
					
				}
			}.runTaskTimer(plugin, 0, 1);
		}
	}
	
}
