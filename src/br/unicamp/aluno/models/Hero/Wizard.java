package br.unicamp.aluno.models.Hero;

public class Wizard extends MisticHero {

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
	
	@Override
	public String toString() {
		return "WZ";
	}
}
