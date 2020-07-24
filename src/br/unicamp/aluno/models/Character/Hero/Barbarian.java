package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Item.LongSword;

public class Barbarian extends Hero {

	//É melhor alterar os atributos do pai através de protected ou chamando o metodo publico?
	public Barbarian(String name) {
		//Chamando o construtor da super classe
		super(name,3,2,8,2); //Pontos de vida, inteligencia e defesa padrões
		//Adicionar arma segundo as regras do jogo
		storeInBackpack(new LongSword());
	}
	
	@Override
	public String toString() {
		return "BA";
	}
	
}
