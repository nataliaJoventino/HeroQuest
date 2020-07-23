package br.unicamp.aluno.models.Item;

public abstract class Item {
	private String name; // precisa de nome para apresentar os items da mochila

	public Item(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}
	
}
