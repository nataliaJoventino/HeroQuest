package br.unicamp.aluno.models.Character.Monster;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.aluno.models.EngineComponents.Point;
import br.unicamp.aluno.models.EngineComponents.Traceable;
import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.SideDice;

public abstract class Monster extends Character {
	private ArrayList<Point> adjacent;

	public Monster(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) {
		super(x, y, quantityOfAttackDices, quantityOfDefenceDices, lifePoints, inteligencePoints);
		adjacent = new ArrayList<>();
	}

	public int hitDefence(){
		int cont = 0;
		for (int i = 0; i < getQuantityOfDefenceDices(); i++)
			if(getDice().combatDice() == SideDice.MONSTER_SHIELD){
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
		int x = character.getPositionX() + direction.getPoint().getPositionX(); // soma direção com posição atual da personagem para pegar adjacente
		int y = character.getPositionY() + direction.getPoint().getPositionY();
		Point point = new Point(x,y);
		adjacent.add(point);
	}

	public boolean isHeroAround(Hero hero){ // verifica se heroi está nas adjacencias
		listAdjacent(this);

		for (Point point : adjacent){
			if (hero.getPositionX() == point.getPositionX() && hero.getPositionY() == point.getPositionY())
				return true;
		}
		return false;
	}
	
	// gera uma direção aleatória
    public Direction randomMonsterDirection() { // no mapa verifica se posição está livre
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


}
