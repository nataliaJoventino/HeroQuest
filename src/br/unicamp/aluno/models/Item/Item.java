package br.unicamp.aluno.models.Item;

public abstract class Item {
	private String name; // precisa de nome para apresentar os items da mochila

	public Item(String name) {
		this.name = name;
	}

	//	//O equipTheHero devolve o item depois de ter realizado os procedimentos certos.
//	//Assim, devolvendo poderemos escolher em qual mão colocar
//	public abstract Item equipTheHero(Hero hero);
//
//	public abstract void unequipTheHero(Hero hero);

	@Override
	public String toString(){
		return name;
	}
	
}
