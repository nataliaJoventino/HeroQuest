package br.unicamp.aluno.models;

public class Armor extends Item {

	//Equipando a armadura
	@Override
	public Item equipTheHero(Hero hero) {
		hero.wearArmor(this);
		return this;
	}

	//Desequipando a armadura
	@Override
	public void unequipTheHero(Hero hero) {
		hero.wearArmor(null);	
	}

}
