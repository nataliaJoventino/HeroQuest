package br.unicamp.aluno.models.Item;

public class Armor extends Item {
	private int defenseBonus;

	public Armor(String name, int defenseBonus) {
		super(name);
		this.defenseBonus = defenseBonus;
	}

	public int getDefenseBonus() {
		return defenseBonus;
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
