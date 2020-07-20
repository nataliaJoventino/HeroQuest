package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Item.Armor;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Item.Weapon;

public abstract class Hero extends Character {
	private String name;
	private Item rightHand;
	private Item leftHand;
	private Item armor;
	
	//Construtor de Heroi
	public Hero(String name, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		//O heroi sempre nasce no (18,2) por enquanto
		super(18, 2, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
		this.name = name;
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
		armor = newArmor;
	}

	public void equip(Item item, Hand hand){ // quem cham verifica se o item deve ser segurado com as duas mão quando equipado
		if (hand == Hand.LEFT)
			holdWithLeftHand(item);
		else if (hand == Hand.RIGHT)
			holdWithRightHand(item);

		addBonus(item);
	}

	public void equip(Item item){ // equipa o item escolhido
		holdItem(item);
		addBonus(item);
	}

	protected void addBonus(Item item){
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

	private void removeAttackBonus(Item item){
		try{
			Weapon weapon = (Weapon) item; // converte para tipo weapon e pega bonus de ataque
			super.removeAttackDice(weapon.getAttackBonus());
		}catch (ClassCastException e){
			System.out.println("Item não é uma arma");
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

		if (rightHand == leftHand){ // se item da mão direita for igua a da mão esquerda, é item que usa duas mãos
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
		if (rightHand == leftHand){ // se item da mão direita for igua a da mão esquerda, é item que usa duas mãos
			unequipTheItemFromLeftHand();
			unequipTheItemFromRightHand();
		} else if (leftHand != null) //Tirando o item atual da maneira certa primeiro
			unequipTheItemFromLeftHand();
		
		//Agora ele pode retirar o item desejado da mochila e usar normalmente
		removeFromBackpack(item);
		this.leftHand = item;
	}
	
	//Desequipando e guardando na mochila o item da mão direita
	private void unequipTheItemFromRightHand() {
		Item item = rightHand;
		rightHand = null;
		removeAttackBonus(item);
		storeInBackpack(item);
	}
	
	//Desequipando e guardando na mochila o item da mão esquerda
	private void unequipTheItemFromLeftHand() {
		
		//Talvez tenha que colocar uns instanceof aqui nath, eu nn lembro e to cansado kkkkkkkk (Mesma coisa no equipar item)
		Item item = leftHand;
		leftHand = null; // esvazia mão
		removeAttackBonus(item);
		storeInBackpack(item);
	}

	@Override
	protected void move() {

	}

	@Override
	protected void action() {

	}
}