package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Hero.Hero;

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
