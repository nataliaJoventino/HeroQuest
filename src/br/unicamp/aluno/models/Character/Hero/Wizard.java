package br.unicamp.aluno.models.Character.Hero;

public class Wizard extends MisticHero {

	public Wizard(String name) {
		super(name,1,2,4,6); //Pontos de vida, inteligencia e defesa padrões
		
		//Adicionar armas
		//Não adicionei pq ainda não as criei
	}
	
	@Override
	public String toString() {
		return "W";
	}
}
