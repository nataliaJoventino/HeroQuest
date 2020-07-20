package br.unicamp.aluno.models;

import java.util.ArrayList;

import br.unicamp.aluno.models.Item.Item;

//Tesouro
public class Treasure extends Traceable {
	
	//Itens que ficam dentro do baú do tesouro
	private ArrayList<Item> items;

	//Construtor da classe
	public Treasure(int x, int y) {
		super(x, y);

		//Guardaremos itens aleatórios dentro do baú -> Vou implementar depois
		this.items = new ArrayList<Item>();
	}
	
	//ToString
	@Override
	public String toString() {
		return "TS";
	}

}
