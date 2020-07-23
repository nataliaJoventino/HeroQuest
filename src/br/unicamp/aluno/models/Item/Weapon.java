package br.unicamp.aluno.models.Item;

public abstract class Weapon implements Item {
	private boolean isDestroyed;
	private boolean bothHands;
	private int weaponReach;
	private int attackBonus;

	public Weapon(boolean isDestroyed, boolean bothHands, int weaponReach, int attackBonus) {
		this.isDestroyed = isDestroyed;
		this.bothHands = bothHands;
		this.weaponReach = weaponReach;
		this.attackBonus = attackBonus;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public boolean isBothHands() {
		return bothHands;
	}

	public int getWeaponReach() {
		return weaponReach;
	}

	public int getAttackBonus() {
		return attackBonus;
	}
}
