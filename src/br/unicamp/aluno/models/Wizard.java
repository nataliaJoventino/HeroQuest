package br.unicamp.aluno.models;

public class Wizard extends Hero {

	public Wizard(String name) {
		super(name);
		
		//Adicionando os pontos de ataque, defesa, vida e inteligencia
		this.setQuantityOfAttackDices(1);
		this.setQuantityOfDefenceDices(2);
		this.setLifePoints(4);
		this.setInteligencePoints(6);
		
		//Adicionar armas
		//Não adicionei pq ainda não as criei
	}
	
}
