package br.unicamp.aluno.models.Character.Hero;

public class Elf extends MysticHero {
	
	public Elf(String name) {
		//Chamando o construtor da super classe
		super(name,2,2,4,6); //Pontos de vida, inteligencia e defesa padrões

		//Adicionar arma segundo as regras do jogo
		//Ainda não adicionei pq precisa criar lá nos itens			
	}
	
	@Override
	public String toString() {
		return "EL";
	}
	
}
