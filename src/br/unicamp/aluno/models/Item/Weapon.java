package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Hero.Hero;

public abstract class Weapon extends Item {

	public abstract Item equipTheHero(Hero hero);
	public abstract void unequipTheHero(Hero hero);
}
