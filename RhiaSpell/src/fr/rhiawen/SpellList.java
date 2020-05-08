package fr.rhiawen;

import org.bukkit.Particle;

public enum SpellList {

	AVADA(1, -1, 40, "§2Avada-Kedavra", Particle.SPELL),
	STUPEFIX(2, 10, 40, "§cStupefix", Particle.FLAME),
	PETRIFICUS(3, 0, 30, "§6Pretrificus Totalus", Particle.SPELL_WITCH);
	
	private final int id;
	private final int power;
	private final int range;
	private final String name;
	private final Particle particle;
	
	private SpellList(int id, int power, int range, String name, Particle particle) {
		this.id = id;
		this.power = power;
		this.range = range;
		this.name = name;
		this.particle = particle;
	}

	public int getId() {
		return id;
	}
	
	public int getPower() {
		return power;
	}

	public int getRange() {
		return range;
	}

	public String getName() {
		return name;
	}
	
	public Particle getParticle() {
		return particle;
	}
}
