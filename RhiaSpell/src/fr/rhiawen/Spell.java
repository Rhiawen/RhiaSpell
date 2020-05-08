package fr.rhiawen;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

/*
 * Class is use for spells
 * Spell can be use with left click and change with right click
 */
public class Spell {

	//Map of players spell
	private final Map<String, SpellList> playerSpell = Maps.newHashMap();
	
	/*
	 * Constructor
	 */
	public Spell() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			initPlayer(player);
		}
	}
	
	/*
	 * Add player to the map
	 */
	public void initPlayer(Player player) {
		String uuid = player.getUniqueId().toString();
		if (playerSpell.containsKey(uuid)) return;
		playerSpell.put(uuid, SpellList.AVADA);
	}
	
	/*
	 * Remove player from the Map
	 */
	public void removePlayer(Player player) {
		if (!playerSpell.containsKey(player.getUniqueId().toString())) return;
		playerSpell.remove(player.getUniqueId().toString());
	}
	
	/*
	 * Use to change player spell (Right Click with stick)
	 */
	public void changeSpell(Player player) {
		String uuid = player.getUniqueId().toString();
		if (!playerSpell.containsKey(uuid)) return;
		SpellList spellList = playerSpell.get(uuid);
		int id = spellList.getId() + 1;
		boolean ok = false;
		for (SpellList sl : SpellList.values()) {
			if (id == sl.getId()) {
				ok = true;
				spellList = sl;
				break;
			}
		}
		if (!ok) {
			spellList = SpellList.AVADA;
		}
		player.sendMessage("Votre sort a ete modifie en " + spellList.getName());
		playerSpell.remove(uuid);
		playerSpell.put(uuid, spellList);
	}
	
	public SpellList getSpell(Player player) {
		return playerSpell.get(player.getUniqueId().toString());
	}
	
}
