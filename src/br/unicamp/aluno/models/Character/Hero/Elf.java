package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Item.ShortSword;
import br.unicamp.aluno.models.Item.SimpleHeal;

public class Elf extends MysticHero {
	
	public Elf(String name) {
		//Chamando o construtor da super classe
		super(name,2,2,4,6); //Pontos de vida, inteligencia e defesa padrões
		storeInBackpack(new ShortSword());
		storeInBackpack(new SimpleHeal());
	}
	
	@Override
	public String toString() {
		return "EL";
	}
	
}
