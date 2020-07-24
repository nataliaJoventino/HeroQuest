package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Item.Fireball;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Item.Spell;

//Os herois misticos podem dominar os 4 elementos e lançar magias
public class MysticHero extends Hero {
	protected Spell spell; // botar em private
	public MysticHero(String name, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		super(name, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
	}

	public Spell getSpell() {
		return spell;
	}

	private void unableSpell(){
		if (spell != null)
			spell = null;
	}

	private boolean isSpell(Item item){
		try{
			Spell spell = (Spell) item;
			return true;
		} catch (ClassCastException e){
			return false;
		}
	}

	@Override
	public void equip(Item item, Hand hand) { // feitiço só pode ser equipado sozinho, personagem não pode carregar armas e feitiço
		if (!isSpell(item)) {
			super.equip(item, hand);
			unableSpell();
		}else
			equip(item);
	}

	@Override
	public void equip(Item item) {
		if (!isSpell(item)) {
			super.equip(item);
			unableSpell();
		}else {
			this.spell = (Spell) item;
			removeFromBackpack(item);
			unequipTheItemFromLeftHand(); // se equipado com feitiço não pode estar com armas
			unequipTheItemFromRightHand();
		}
	}

	public void throwSpell(Character character) { // lança feitiço recbido o alvo
		try {
			spell.cast(character);
			if (spell.isDestroyed()) {
				unableSpell();
				removeFromBackpack(spell);
			}
		} catch (NullPointerException e){
			System.out.println("Não há feitiço equipado");
		}
	}

//	public void throwSpell(Character character) { // versão de throws recebendo um objetco
//		try{
//			Spell spell = (Spell) this.getRightHand(); //Checando se está na mão direita
//			spell.cast(character); // precisa receber o  objetivo
//
//			if(!spell.isDestroyed())
//				this.holdWithRightHand(null);
//
//		} catch (ClassCastException e){
//
//			try {
//				Spell spell = (Spell) this.getLeftHand(); //Checando se está na mão esquerda e lançando caso esteja
//				spell.cast(character); //lançando o feitiço
//
//				if(!spell.isDestroyed())
//					this.holdWithLeftHand(null);
//
//			} catch (ClassCastException m){
//				System.out.println("Você não está segurando nenhum feitiço!"); //Caso ele não esteja segurando nenhum feitiço
//			}
//
//		}
//	}
	
}
