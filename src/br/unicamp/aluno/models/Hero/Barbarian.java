package br.unicamp.aluno.models.Hero;

import br.unicamp.aluno.models.Item.LongSword;

public class Barbarian extends Hero {

	//É melhor alterar os atributos do pai através de protected ou chamando o metodo publico?
	public Barbarian(String name) {
		//Chamando o construtor da super classe
		super(name);
		//Pontos de vida, inteligencia e defesa padrões
		this.setQuantityOfAttackDices(3);
		this.setQuantityOfDefenceDices(2);
		this.setLifePoints(8);
		this.setInteligencePoints(2);
		
		//Adicionar arma segundo as regras do jogo
		this.holdWithRightHand(new LongSword());
		
	}
	
	@Override
	public String toString() {
		return "BA";
	}
	
}
