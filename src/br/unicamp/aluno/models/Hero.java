package br.unicamp.aluno.models;

import java.util.ArrayList;

public class Hero extends Traceable{
	private String name;
	private int quantityOfDices;
	private int quantityOfAttackDices;
	private int quantityOfDefenceDices;
	private int lifePoints;
	private int inteligencePoints;
	private Item rightHand;
	private Item leftHand;
	private ArrayList<Item> backpack;
	
	//Construtor de Heroi
	public Hero(String name) {
		//O heroi sempre nasce no (0,0) por enquanto
		super(0, 0);
		this.name = name;
		this.backpack = new ArrayList<Item>();
	}

	//Obter nome
	public String getName() {
		return name;
	}
	
	//Obter quantidade de dados
	public int getQuantityOfDices() {
		return quantityOfDices;
	}

	//Altera quantidade de dados
	public void setQuantityOfDices(int quantityOfDices) {
		this.quantityOfDices = quantityOfDices;
	}
	
	//Obter quantidade de dados de ataque
	public int getQuantityOfAttackDices() {
		return quantityOfAttackDices;
	}

	//Altera quantidade de dados de ataque
	public void setQuantityOfAttackDices(int quantityOfAttackDices) {
		this.quantityOfAttackDices = quantityOfAttackDices;
	}

	//Obter quantidade de defesa
	public int getQuantityOfDefenceDices() {
		return quantityOfDefenceDices;
	}

	//Altera quantidade de dados de defesa
	public void setQuantityOfDefenceDices(int quantityOfDefenceDices) {
		this.quantityOfDefenceDices = quantityOfDefenceDices;
	}

	//Obter quantidade de pontos de vida
	public int getLifePoints() {
		return lifePoints;
	}
	//Altera quantidade de pontos de vida
	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	//Obter quantidade de pontos de inteligencia
	public int getInteligencePoints() {
		return inteligencePoints;
	}

	//Altera quantidade de pontos de inteligencia
	public void setInteligencePoints(int inteligencePoints) {
		this.inteligencePoints = inteligencePoints;
	}
	
	//Remove uma certa quantidade de vida do personagem
	public void removeLifePoints(int value) {
		this.lifePoints -= value;
	}
		
	//Verifica se o personagem está vivo
	public boolean isAlive() {
		if(lifePoints > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	//Colocar item na mão direita
	public void holdWithRightHand(Item item) {
		
		//Verifica se o heroi possui o item que está querendo usar
		if(!this.backpack.contains(item)) {
			System.out.println("Você não possui esse item!");
			return;
		}
		
		//Caso tenha pode retirar da mochila e usar normalmente
		this.backpack.remove(item);
		this.rightHand = item;
	}
	
	//Colocar item na mão esquerda
	public void holdWithLeftHand(Item item) {
		//Verifica se o heroi possui o item que está querendo usar
		if(!this.backpack.contains(item)) {
			System.out.println("Você não possui esse item!");
			return;
		}
		
		//Caso tenha pode retirar da mochila e usar normalmente
		this.backpack.remove(item);
		this.leftHand = item;
	}

	//Colocar item na mochila
	public void storeInBackpack(Item item) {
		this.backpack.add(item);
	}
	
	
}