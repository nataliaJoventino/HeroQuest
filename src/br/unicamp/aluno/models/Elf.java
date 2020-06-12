package br.unicamp.aluno.models;

public class Elf extends MisticHero {
	
	public Elf(String name) {
		//Chamando o construtor da super classe
		super(name);
		//Pontos de vida, inteligencia e defesa padrões
		this.setQuantityOfAttackDices(2);
		this.setQuantityOfDefenceDices(2);
		this.setLifePoints(4);
		this.setInteligencePoints(6);
		
		//Adicionar arma segundo as regras do jogo
		//Ainda não adicionei pq precisa criar lá nos itens
				
	}
	
}
