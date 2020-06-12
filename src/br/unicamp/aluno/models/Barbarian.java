package br.unicamp.aluno.models;

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
		//Ainda não adicionei pq precisa criar lá nos itens
		
		
	}
	
}
