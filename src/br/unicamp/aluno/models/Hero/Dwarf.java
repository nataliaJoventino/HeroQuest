package br.unicamp.aluno.models.Hero;

public class Dwarf extends Hero {
	
	public Dwarf(String name) {
		//Chamando o construtor da super classe
		super(name);
		//Pontos de vida, inteligencia e defesa padrões
		this.setQuantityOfAttackDices(2);
		this.setQuantityOfDefenceDices(2);
		this.setLifePoints(7);
		this.setInteligencePoints(3);
		
		//Adicionar arma segundo as regras do jogo
		//Ainda não adicionei pq precisa criar lá nos itens
		
	}
	
	@Override
	public String toString() {
		return "DW";
	}

}
