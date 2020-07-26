package br.unicamp.aluno.models.Item;

import java.util.Random;

public abstract class Armor implements Item {
	private int defenseBonus;
	private boolean isDestroyed;

	public Armor(int defenseBonus, boolean isDestroyed) {
		this.defenseBonus = defenseBonus;
		this.isDestroyed = isDestroyed;
	}

	public int getDefenseBonus() {
		return defenseBonus;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	@Override
	public String toString() {
		return "Armor";
	}


}
