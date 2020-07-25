package br.unicamp.aluno.models.Character.Hero;

import java.util.ArrayList;

import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Point;
import br.unicamp.aluno.models.SquareVision;
import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Enum.SideDice;
import br.unicamp.aluno.models.Exceptions.YouAreDeadException;
import br.unicamp.aluno.models.Item.Armor;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Item.Weapon;

public abstract class Hero extends Character {
	private String name;
	private Item rightHand;
	private Item leftHand;
	private Item armor;
	private ArrayList<Item> backpack;
	private SquareVision firstSquareVision;
	private SquareVision secondSquareVision;

	//Construtor de Heroi
	public Hero(String name, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		//O heroi sempre nasce no (18,2)
		super(8, 18, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
		this.name = name;
		this.backpack = new ArrayList();
		firstSquareVision = new SquareVision();
		secondSquareVision = new SquareVision();
	}

	@Override
	public void removeLifePoints(int value) { //Remove uma certa quantidade de vida do personagem
		super.removeLifePoints(value);

		if (getLifePoints() == 0)
			throw new YouAreDeadException();

	}

	public Hand isBothHandsUsed(){ // retorna qual mão está com item, se as duas estiverem ocupadas ou vazias retorna nulo
		if (leftHand != null && rightHand == null)
			return Hand.LEFT;
		else if (rightHand != null && leftHand == null)
			return Hand.RIGHT;
		else
			return null; // se duas mãos ocupadas ou duas mãos vazias

	}

	public boolean emptyHands(){
		if (rightHand == null && leftHand == null)
			return true;
		return false;
	}

	public boolean isBothHandItem(){
		if (!emptyHands() && rightHand == leftHand)
			return true;
		return false;
	}

	//Atualizando o campo de visão
	public void updateVision(SquareVision vision1, SquareVision vision2) {
		firstSquareVision = vision1;
		secondSquareVision = vision2;
	}
	public SquareVision getFirstSquareVision() {
		return firstSquareVision;
	}
	public SquareVision getSecondSquareVision() {
		return secondSquareVision;
	}

	public void storeInBackpack(Item item){ //Colocar item na mochila
		backpack.add(item);
	}

	protected void removeFromBackpack(Item item){ //remove item da mochila
		backpack.remove(item);
	}

	protected boolean isInBackpack(Item item){  //verifica se item existe na mochila
		return backpack.contains(item);
	}

	public Item getInBackpack(int index){ //Colocar item na mochila
		return backpack.get(index);
	}

	public void printBackpack(){ // printa itens da mochila na tela com seu index
		for (int i = 0; i < backpack.size(); i++)
			System.out.println(""+ i +" "+backpack.get(i).toString()); // item deve ter nome?
	}

	//Obter nome
	public String getName() {
		return name;
	}

	public Item getRightHand() {
		return rightHand;
	}

	public Item getLeftHand() {
		return leftHand;
	}

	//Obtem a armadura usada
	public Item getArmor() {
		return armor;
	}

	public void equip(Item item, Hand hand){ // quem chama verifica se o item deve ser segurado com as duas mão quando equipado
		if (hand == Hand.LEFT)
			holdWithLeftHand(item);
		else if (hand == Hand.RIGHT)
			holdWithRightHand(item);
	}

	public void equip(Item item){ // equipa o item escolhido
		if (isArmor(item) != null)
			wearArmor(item);
		else
			holdItem(item);
	}

	//Veste a armadura
	private void wearArmor(Item newArmor) {
		//Armaduras antigas serão destruidas quando trocadas
		if (armor != null && armor.isDestroyed())
			removeBonus(armor);
		else if (armor != null){
			removeBonus(armor);
			storeInBackpack(armor);
		}

		armor = newArmor;
		addBonus(armor);
	}

	private Armor isArmor(Item item){
		try{
			Armor armor = (Armor) item;
			return armor;
		} catch (ClassCastException e){
			return null;
		}
	}

	private void holdItem(Item item){ // item deve ser segurado com as dua mãos
		if (leftHand != null) // verifica se mão esquerda está ocupada
			unequipTheItemFromLeftHand();

		if (rightHand != null) // verifica se mão direita está ocupada
			unequipTheItemFromRightHand();

		holdWithLeftHand(item);
		holdWithRightHand(item);
	}

	//Colocar item na mão direita
	protected void holdWithRightHand(Item item) {

		//Verifica se o heroi possui o item que está querendo usar
		if(!isInBackpack(item)) {
			System.out.println("Você não possui esse item!");
			return;
		}

		if (rightHand == leftHand){ // se item da mão direita for igual a da mão esquerda, é item que usa duas mãos
			unequipTheItemFromLeftHand();
			unequipTheItemFromRightHand();
		} else if (rightHand != null) //Tirando o item atual da maneira certa primeiro
			unequipTheItemFromRightHand();

		//Caso tenha pode retirar da mochila e usar normalmente
		removeFromBackpack(item);
		this.rightHand = item;
	}

	//Colocar item na mão esquerda
	protected void holdWithLeftHand(Item item) {
		//Verifica se o heroi possui o item que está querendo usar
		if(!isInBackpack(item)) {
			System.out.println("Você não possui esse item!");
			return;
		}

		if (rightHand == leftHand){ // se item da mão direita for igual a da mão esquerda, é item que usa duas mãos
			unequipTheItemFromLeftHand();
			unequipTheItemFromRightHand();
		} else if (leftHand != null) //Tirando o item atual da maneira certa primeiro
			unequipTheItemFromLeftHand();

		//Agora ele pode retirar o item desejado da mochila e usar normalmente
		removeFromBackpack(item);
		this.leftHand = item;
	}

	//Desequipando e guardando na mochila o item da mão direita
	protected void unequipTheItemFromRightHand() {
		Item item = rightHand;
		if (item != null) {
			rightHand = null;
			storeInBackpack(item);
		}
	}

	//Desequipando e guardando na mochila o item da mão esquerda
	protected void unequipTheItemFromLeftHand() {
		Item item = leftHand;
		if (item != null) {
			leftHand = null; // esvazia mão
			storeInBackpack(item);
		}
	}

	private void addBonus(Item item){
		try{
			Weapon weapon = (Weapon) item; // converte para tipo weapon e pega bonus de ataque
			super.addAttackDice(weapon.getAttackBonus());
		}catch (ClassCastException e){
			try{
				Armor armor = (Armor) item; // converte para tipo armor e pega bonus de defesa
				super.addDefenceDice(armor.getDefenseBonus());
			}catch (ClassCastException m){
				System.out.println("Item não possui bonus");
			}
		}
	}

	private void removeBonus(Item item){
		try{
			Weapon weapon = (Weapon) item; // converte para tipo weapon e pega bonus de ataque
			super.removeAttackDice(weapon.getAttackBonus());
		}catch (ClassCastException e){
			try{
				Armor armor = (Armor) item; // converte para tipo armor e pega bonus de defesa
				super.removeDefenceDice(armor.getDefenseBonus());
			}catch (ClassCastException m){
				System.out.println("Item não possui bonus");
			}
		}
	}


	public void hit(Character character){ //quanto de lifepoints vai ser tirado do inimigo dado dados (1 caveira = 1 hit)
		int cont = 0;
		addBonus(leftHand); // para quando item ocupa duas mãos
		super.hit(character);
		removeBonus(leftHand);

		if (leftHand.isDestroyed()){ // se item se destroi após o uso
			leftHand = null;
			rightHand = null;
		}
	}

	public void hit(Character character, Hand hand){ //quanto de lifepoints vai ser tirado do inimigo dado dados (1 caveira = 1 hit)
		Item item = null;
		int cont = 0;

		if (hand == Hand.LEFT)
			item = leftHand;
		else if (hand == Hand.RIGHT)
			item =  rightHand;

		addBonus(item);
		super.hit(character);
		removeBonus(item);

		if (item.isDestroyed()){ // verifica se item se destroi após o uso
			if (hand == Hand.LEFT)
				leftHand = null;
			else if (hand == Hand.RIGHT)
				rightHand = null;
		}
	}

	public int hitDefence(){ //quanto vai dfender de ataque do inimigo
		int cont = 0;
		for (int i = 0; i < getQuantityOfDefenceDices(); i++)
			if(getDice().combatDice() == SideDice.HERO_SHIELD){
				cont++;
			}

		return cont;
	}

	private Weapon isWeapon(Item item){
		try{
			Weapon weapon = (Weapon) item; // converte para tipo weapon e pega bonus de ataque
			return weapon;
		}catch (ClassCastException e){
			return  null;
		}
	}

	@Override
	public boolean isOnSight(Character character) { // para arma que ocupa duas mãos
		Weapon weapon = isWeapon(leftHand);
		return onSight(character, weapon);
	}

	public boolean isOnSight(Character character, Hand hand) { //verifica arma da mão direita ou esquerda
		Weapon weapon;
		int x, y;

		if (hand == Hand.LEFT)
			weapon = isWeapon(leftHand);
		else
			weapon = isWeapon(rightHand);

		return onSight(character, weapon);
	}

}