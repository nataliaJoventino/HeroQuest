package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Item.ShortSword;

public class Dwarf extends Hero {
	
	public Dwarf(String name) {
		//Chamando o construtor da super classe
		super(name,2,2,7,3); //Pontos de vida, inteligencia e defesa padrões
		storeInBackpack(new ShortSword());
	}
	
	@Override
	public String toString() {
		return "DW";
	}

}
