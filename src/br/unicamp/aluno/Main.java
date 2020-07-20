package br.unicamp.aluno;

import br.unicamp.aluno.models.Hero.Wizard;

public class Main {

	public static void main(String[] args) {
		
		Wizard hero = new Wizard("Andrey");
		
		Game game = new Game(hero, 21, 23);
		
		game.printMap();

	}

}
