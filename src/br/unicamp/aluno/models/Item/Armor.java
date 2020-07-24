package br.unicamp.aluno.models.Item;

public class Armor implements Item {
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


	//	//Equipando a armadura
//	@Override
//	public Item equipTheHero(Hero hero) {
//		hero.wearArmor(this);
//		return this;
//	}
//
//	//Desequipando a armadura
//	@Override
//	public void unequipTheHero(Hero hero) {
//		hero.wearArmor(null);
//	}

}
