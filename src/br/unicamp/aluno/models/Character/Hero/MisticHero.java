package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Item.Spell;

//Os herois misticos podem dominar os 4 elementos e lançar magias
public class MisticHero extends Hero {

	public MisticHero(String name, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		super(name, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
	}

	//Joga a magia
	//Para atirar a magia é necessário que o personagem a esteja segurando em uma de suas mãos
	public void throwSpell() {
		try{
			Spell spell = (Spell) this.getRightHand(); //Checando se está na mão direita
			spell.cast(this); // precisa receber o proprio heroi

			if(!spell.isDestroyed())
				this.holdWithRightHand(null);

		} catch (ClassCastException e){

			try {
				Spell spell = (Spell) this.getLeftHand(); //Checando se está na mão esquerda e lançando caso esteja
				spell.cast(this); //lançando o feitiço

				if(!spell.isDestroyed())
					this.holdWithLeftHand(null);

			} catch (ClassCastException m){
				System.out.println("Você não está segurando nenhum feitiço!"); //Caso ele não esteja segurando nenhum feitiço
			}

		}
	}

	public void throwSpell(Character character) { // versão de throws recebendo um objetco
		try{
			Spell spell = (Spell) this.getRightHand(); //Checando se está na mão direita
			spell.cast(character); // precisa receber o  objetivo

			if(!spell.isDestroyed())
				this.holdWithRightHand(null);

		} catch (ClassCastException e){

			try {
				Spell spell = (Spell) this.getLeftHand(); //Checando se está na mão esquerda e lançando caso esteja
				spell.cast(character); //lançando o feitiço

				if(!spell.isDestroyed())
					this.holdWithLeftHand(null);

			} catch (ClassCastException m){
				System.out.println("Você não está segurando nenhum feitiço!"); //Caso ele não esteja segurando nenhum feitiço
			}

		}
	}
	
}
