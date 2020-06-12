package br.unicamp.aluno.models;

public abstract class Weapon extends Item {

	public abstract Item equipTheHero(Hero hero);
	public abstract void unequipTheHero(Hero hero);
}
