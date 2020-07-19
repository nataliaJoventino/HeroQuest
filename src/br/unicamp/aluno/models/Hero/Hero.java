package br.unicamp.aluno.models.Hero;

import br.unicamp.aluno.models.Item.Armor;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Exceptions.YouAreDeadException;

import java.util.ArrayList;

public abstract class Hero extends Traceable {
	private String name;
	private int quantityOfDices;
	private int quantityOfAttackDices;
	private int quantityOfDefenceDices;
	private int lifePoints;
	private int inteligencePoints;
	private Item rightHand;
	private Item leftHand;
	private Item armor;
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
		if(lifePoints <= 0) {
			throw new YouAreDeadException();
		}
	}

	//Colocar item na mão direita
	public void holdWithRightHand(Item item) {
		
		//Verifica se o heroi possui o item que está querendo usar
		if(!this.backpack.contains(item)) {
			System.out.println("Você não possui esse item!");
			return;
		}
		//Tirando o item atual da maneira certa primeiro
		unequipTheItemFromRightHand();
		
		//Caso tenha pode retirar da mochila e usar normalmente
		this.backpack.remove(item);
		this.rightHand = item.equipTheHero(this);
	}
	
	//Colocar item na mão esquerda
	public void holdWithLeftHand(Item item) {
		//Verifica se o heroi possui o item que está querendo usar
		if(!this.backpack.contains(item)) {
			System.out.println("Você não possui esse item!");
			return;
		}
		
		//Tirando o item atual da maneira certa primeiro
		unequipTheItemFromLeftHand();
		
		//Agora ele pode retirar o item desejado da mochila e usar normalmente
		this.backpack.remove(item);
		this.leftHand = item;
	}
	
	//Desequipando e guardando na mochila o item da mão direita
	private void unequipTheItemFromRightHand() {
		Item item = this.getRightHand();
		storeInBackpack(item);
		item.unequipTheHero(this);
	}
	
	//Desequipando e guardando na mochila o item da mão esquerda
	private void unequipTheItemFromLeftHand() {
		
		//Talvez tenha que colocar uns instanceof aqui nath, eu nn lembro e to cansado kkkkkkkk (Mesma coisa no equipar item)
		Item item = this.getLeftHand();
		item.unequipTheHero(this);
		storeInBackpack(item);
	}

	//Colocar item na mochila
	public void storeInBackpack(Item item) {
		this.backpack.add(item);
	}
	
	//Obtem qual é o item que o heroi está segurando na mão direita
	public Item getRightHand() {
		return rightHand;
	}
	
	//Obtem a armadura usada
	public Item getArmor() {
		return armor;
	}
	
	//Obtem qual é o item que o heroi está segurando na mão esquerda
	public Item getLeftHand() {
		return leftHand;
	}
	
	//Veste a armadura
	public void wearArmor(Armor newArmor) {
		//Armaduras antigas serão destruidas quando trocadas
		armor = newArmor;
	}
	
}