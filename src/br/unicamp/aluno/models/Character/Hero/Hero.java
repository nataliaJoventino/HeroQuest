package br.unicamp.aluno.models.Character.Hero;

import java.util.ArrayList;

import br.unicamp.aluno.models.SquareVision;
import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Enum.SideDice;
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
		//O heroi sempre nasce no (18,2) por enquanto
		super(8, 18, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
		this.name = name;
		this.backpack = new ArrayList();
		firstSquareVision = new SquareVision();
		secondSquareVision = new SquareVision();
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

	protected void storeInBackpack(Item item){ //Colocar item na mochila
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

	//Veste a armadura
	public void wearArmor(Armor newArmor) {
		//Armaduras antigas serão destruidas quando trocadas
		removeBonus(armor);
		armor = newArmor;
		addBonus(armor);
	}

	public void equip(Item item, Hand hand){ // quem chama verifica se o item deve ser segurado com as duas mão quando equipado
		if (hand == Hand.LEFT)
			holdWithLeftHand(item);
		else if (hand == Hand.RIGHT)
			holdWithRightHand(item);
	}

	public void equip(Item item){ // equipa o item escolhido
		holdItem(item);
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
		rightHand = null;
		storeInBackpack(item);
	}

	//Desequipando e guardando na mochila o item da mão esquerda
	protected void unequipTheItemFromLeftHand() {
		Item item = leftHand;
		leftHand = null; // esvazia mão
		storeInBackpack(item);
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
		try {
			int x = this.getPositionX() + (getCurrentDirection().getTraceable().getPositionX() * isWeapon(leftHand).getWeaponReach()); // pega direção atual e multiplica pelo alcance da arma somando com a coordenada atual para projetar ataque
			int y = this.getPositionY() + (getCurrentDirection().getTraceable().getPositionY() * isWeapon(leftHand).getWeaponReach());

			if (character.getPositionX() > this.getPositionX() && character.getPositionX() <= x) //verifica se personagem esta a frente de monstro ou no alcance da arma em x
				if (character.getPositionY() > this.getPositionY() && character.getPositionY() <= y)
					return  true;

			return false;

		}catch (NullPointerException e) {
			System.out.println("Item equipado não é arma com alcance");
			return false;
		}

	}

	public boolean isOnSight(Character character, Hand hand) { //verivica arda da mão direita ou esquerda
		Weapon weapon;
		int x, y;

		if (hand == Hand.LEFT)
			weapon = isWeapon(leftHand);
		else
			weapon = isWeapon(rightHand);

		try {
			x = this.getPositionX() + (getCurrentDirection().getTraceable().getPositionX() * weapon.getWeaponReach()); // pega direção atual e multiplica pelo alcance da arma somando com a coordenada atual para projetar ataque
			y = this.getPositionY() + (getCurrentDirection().getTraceable().getPositionY() * weapon.getWeaponReach());

			if (character.getPositionX() > this.getPositionX() && character.getPositionX() <= x) //verifica se personagem esta a frente de monstro ou no alcance da arma em x
				if (character.getPositionY() > this.getPositionY() && character.getPositionY() <= y)
					return  true;

			return false;

		}catch (NullPointerException e) {
			System.out.println("Item equipado não é arma com alcance");
			return false;
		}
	}



}