package br.unicamp.aluno;

import br.unicamp.aluno.models.Character.Hero.Barbarian;
import br.unicamp.aluno.models.Character.Hero.Dwarf;
import br.unicamp.aluno.models.Character.Hero.Elf;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Character.Hero.Wizard;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Escolha um nome para o seu heroi:");
		
		/**
		 * Ler o nome do heroi
		 */
		
		System.out.printf("Escolha um heroi -----\n1 - Barbaro\n2 - Anão\n3 - Elfo\n4 - Mago");

		/**
		 * Ler a escolha do usuário
		 */
		
		int heroNumber = 0;
		Hero hero = null;
		
		//Escolhendo o heroi
		switch (heroNumber) {

		//Barbaro
		case 1:
			hero = new Barbarian("Natália");
			break;
		//Anão
		case 2:
			hero = new Dwarf("Professor");
			break;
		//Elfo
		case 3:
			hero = new Elf("Irmão do Andrey");
			break;
		//Mago
		case 4:
			hero = new Wizard("Andrey");
			break;
		default:
			break;
		}
		
		Game game = new Game(hero, 21, 23);

		game.start();
		
	}

}
