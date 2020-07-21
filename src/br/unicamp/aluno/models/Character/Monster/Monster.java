package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.SideDice;
import br.unicamp.aluno.models.Traceable;

import java.util.Random;

public class Monster extends Character {

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

//	//Movimenta aleatoriamente; Mapa não consegue verificar se tem obstaculos a frente do monstro
//	public void move() {
//		Direction dir = randomDirection();
//		move(dir);
//	}

	public int hitDefence(Dice dice){
		int cont = 0;
		for (int i = 0; i < getQuantityOfDefenceDices(); i++)
			if(dice.combatDice() == SideDice.MONSTER_SHIELD){
				cont++;
			}

		return cont;
	}

}
