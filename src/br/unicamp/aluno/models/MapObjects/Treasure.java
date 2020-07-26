package br.unicamp.aluno.models.MapObjects;

import java.util.ArrayList;

import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.EngineComponents.Traceable;

//Tesouro
public class Treasure extends Traceable {
	
	//Itens que ficam dentro do baú do tesouro
	private ArrayList<Item> items;

	private boolean visible;
	
	//Construtor da classe
	public Treasure(int x, int y) {
		super(x, y);
		//Guardaremos itens aleatórios dentro do baú -> Vou implementar depois
		this.items = new ArrayList<Item>();
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void turnVisible() {
		visible = true;
	}

	public void printTreasure(){
		for (int i = 0; i < items.size(); i++)
			System.out.println(""+ i +" "+ items.get(i).toString());
	}

	public Item removeTreasure(int index){
		Item item = items.get(index);
		items.remove(item);
		return item;
	}

	public void addTresure(Item item){
		items.add(item);
	}

	public int size(){
		return items.size();
	}


	//ToString
	@Override
	public String toString() {
		return "TS";
	}

}
