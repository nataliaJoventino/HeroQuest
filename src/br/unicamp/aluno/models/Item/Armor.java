package br.unicamp.aluno.models.Item;

public class Armor implements Item {
	private int defenseBonus;

	public Armor(int defenseBonus) {
		this.defenseBonus = defenseBonus;
	}

	public int getDefenseBonus() {
		return defenseBonus;
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
