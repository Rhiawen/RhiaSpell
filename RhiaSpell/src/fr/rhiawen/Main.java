package fr.rhiawen;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	Spell spell;
	
	public final void onLoad() {
		spell = new Spell();
	}
	
	
	@Override
	public final void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this, spell), this);
	}
	
	
	@Override
	public final void onDisable() {
		
	}
	
}
