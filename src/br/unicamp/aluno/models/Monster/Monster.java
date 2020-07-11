package br.unicamp.aluno.models.Monster;

import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Traceable;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends Traceable {
    private int quantityOfAttackDices;
    private int quantityOfDefenceDices;
    private int lifePoints;
    private ArrayList<Item> weapon; // contei que goblim possui diversos punhais

    public Monster(int x, int y) {
        super(x, y);
    }

    public int getQuantityOfAttackDices() {
        return quantityOfAttackDices;
    }

    public int getQuantityOfDefenceDices() {
        return quantityOfDefenceDices;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    protected ArrayList<Item> getWeapon() {
        return weapon;
    }

    protected void setQuantityOfAttackDices(int quantityOfAttackDices) {
        this.quantityOfAttackDices = quantityOfAttackDices;
    }

    protected void setQuantityOfDefenceDices(int quantityOfDefenceDices) {
        this.quantityOfDefenceDices = quantityOfDefenceDices;
    }

    protected void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    protected void setWeapon(Item weapon) {
        this.weapon.add(weapon);
    }

    private Direction randomDirection(){ // gera uma direção aleatória
        Random randomDirection = new Random();

        switch (randomDirection.nextInt(4)){
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

    public void move(Direction dir){ // dada uma direção lança os dados e movimenta
        Traceable dirCoordinate = dir.getTraceable();

        Dice dice = new Dice();
        int diceNum = dice.redDice();

        int x = getPositionX() + (diceNum * dirCoordinate.getPositionX());
        int y = getPositionY() + (diceNum * dirCoordinate.getPositionY());

        updatePosition(x, y);
    }

    public void move(){ // movimenta aleatoriamente
        Direction dir = randomDirection();
        move(dir);
    }


    protected void attack(){
//        Implementação de attack
        // não entendi muito bem como vai funcionar o ataque e os dados de combate K
    }
}
