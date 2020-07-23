package br.unicamp.aluno;

import br.unicamp.aluno.models.Character.Hero.Wizard;

public class Main {

	public static void main(String[] args) {
		
		Wizard hero = new Wizard("Andrey");
		
		Game game = new Game(hero, 21, 23);
		
		TextEngine keyboard = new TextEngine(game);

//		game.printAllMap();
		System.out.println("");
//		game.searchForTreasure();
//		game.searchForTrap();
		game.printMap();

		keyboard.readCommandFromKeyboard();
		
		
	}

}
