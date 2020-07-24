package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Item.MagicMissile;

public class Wizard extends MisticHero {

	public Wizard(String name) {
		super(name,1,2,4,6); //Pontos de vida, inteligencia e defesa padrões
		MagicMissile m = new MagicMissile();
		spell = m; // teste
		//Adicionar armas
		//Não adicionei pq ainda não as criei
	}
	
	@Override
	public String toString() {
		return "WZ";
	}
}
