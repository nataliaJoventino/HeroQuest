package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.SideDice;
import br.unicamp.aluno.models.Traceable;

import java.util.ArrayList;
import java.util.Random;

public abstract class Monster extends Character {
	private ArrayList<Traceable> adjacent;

	public Monster(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		super(x, y, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
	}

	// gera uma direção aleatória
	public Direction randomDirection() {
		Random randomDirection = new Random();

		switch (randomDirection.nextInt(4)) {
		case 0:
			return Direction.UP;
		case 1:
			return Direction.DOWN;
		case 2:
			return Direction.RIGHT;
		case 3:
			return Direction.LEFT;

		}

		return null;
	}

	public int hitDefence(Dice dice){
		int cont = 0;
		for (int i = 0; i < getQuantityOfDefenceDices(); i++)
			if(dice.combatDice() == SideDice.MONSTER_SHIELD){
				cont++;
			}

		return cont;
	}

	private void listAdjacent(Character character){ // gera lista com todas as adjacencias
		setAdjacent(character, Direction.UP);
		setAdjacent(character, Direction.DOWN);
		setAdjacent(character, Direction.RIGHT);
		setAdjacent(character, Direction.LEFT);
	}

	private void setAdjacent(Character character, Direction direction){ // pega adjacencias de personagem
		int x = character.getPositionX() + direction.getTraceable().getPositionX(); // soma direção com posição atual da personagem para pegar adjacente
		int y = character.getPositionY() + direction.getTraceable().getPositionY();
		Traceable traceable = new Traceable(x,y);
		adjacent.add(traceable);
	}

	public boolean isHeroAround(Hero hero){ // verifica se heroi está nas adjacencias
		listAdjacent(this);

		for (Traceable traceable : adjacent){
			if (hero.getPositionX() == traceable.getPositionX() && hero.getPositionY() == traceable.getPositionY())
				return true;
		}
		return false;
	} // é necessario se tem isOnSight?


}
