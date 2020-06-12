package br.unicamp.aluno.models;

public abstract class Item {
	
	//O equipTheHero devolve o item depois de ter realizado os procedimentos certos.
	//Assim, devolvendo poderemos escolher em qual mão colocar
	public abstract Item equipTheHero(Hero hero);
	
	public abstract void unequipTheHero(Hero hero);
	
}
