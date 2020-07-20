package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Traceable;

import java.util.Random;

public class Monster extends Character {

	public Monster(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		super(x, y, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
	}

	// gera uma direção aleatória
	private Direction randomDirection() {
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

	//Dada uma direção lança os dados e movimenta
	private void move(Direction dir) {
		Traceable dirCoordinate = dir.getTraceable();

		Dice dice = new Dice();
		int diceNum = dice.redDice();

		int x = getPositionX() + (diceNum * dirCoordinate.getPositionX());
		int y = getPositionY() + (diceNum * dirCoordinate.getPositionY());

		updatePosition(x, y);
	}

	//Movimenta aleatoriamente
	public void move() {
		Direction dir = randomDirection();
		move(dir);
	}

	protected void action() {
		// Implementação de attack
		// não entendi muito bem como vai funcionar o ataque e os dados de combate K
	}
}
