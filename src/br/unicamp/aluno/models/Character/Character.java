package br.unicamp.aluno.models.Character;

import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.SideDice;
import br.unicamp.aluno.models.Exceptions.YouAreDeadException;
import br.unicamp.aluno.models.Traceable;

import java.util.ArrayList;

public abstract class Character extends Traceable {
    private int quantityOfAttackDices;
    private int quantityOfDefenceDices;
    private int lifePoints;
    private int inteligencePoints;
    private Direction currentDirection;
    private Dice dice;

    public Character(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) { //recebe via contrutor todas as informações
        super(x, y);
        this.quantityOfAttackDices = quantityOfAttackDices;
        this.quantityOfDefenceDices = quantityOfDefenceDices;
        this.lifePoints = lifePoints;
        this.inteligencePoints = inteligencePoints;
        dice = new Dice();

    }

    protected void addAttackDice(int value){ // adiciona x dados de ataque
        this.quantityOfAttackDices += value;
    }

    protected void removeAttackDice(int value){ // remove x dados de ataque
        this.quantityOfAttackDices -= value;
    }

    protected void addDefenceDice(int value){ // radiciona x dados de defesa
        this.quantityOfDefenceDices += value;
    }

    protected void removeDefenceDice(int value){ // remove x dados de defesa
        this.quantityOfDefenceDices -= value;
    }

    protected Dice getDice() {
        return dice;
    }

    protected int getQuantityOfAttackDices() { // retorna dados de ataque
        return quantityOfAttackDices;
    }

    protected int getQuantityOfDefenceDices() { // retorna dados de defesa
        return quantityOfDefenceDices;
    }

    protected Direction getCurrentDirection() {
        return currentDirection;
    }

    protected void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void addLifePoints(int value){ // adiciona quantidade vida ao personagem
        this.lifePoints += value; // value deve ser sempre positiva, fazer exceção?
    }

    public void removeLifePoints(int value) { //Remove uma certa quantidade de vida do personagem
        this.lifePoints -= value; // value deve ser sempre positiva, fazer exceção?
//        if(lifePoints <= 0) {
//            throw new YouAreDeadException(); essa verificação tem que ser fora daqui 
//        }
    }

    public void removeLifePointsWithDefense(int value){ // remove pontos de vida descontando pontos de defesa
        int totalHit = value - hitDefence(); // remove hits de defesa
        if (totalHit > 0)
            removeLifePoints(totalHit);
    }

    public int getLifePoints() { // retorna life points
        return lifePoints;
    }

    public int getInteligencePoints() { // retorna pontos de inteligencia
        return inteligencePoints;
    }

    public void move(Traceable traceable){ //recebe nova direção e altera (para o teleporte)
        super.updatePosition(traceable.getPositionX(), traceable.getPositionY());
    }

    public void move(Direction direction) { // anda uma posição dada a direção
        int x = super.getPositionX() + direction.getTraceable().getPositionX(); // coordenada x
        int y = super.getPositionY() + direction.getTraceable().getPositionY(); // cooredenada y
        currentDirection = direction;

        super.updatePosition(x,y);
    }

    public void hit(Character character){ //quanto de lifepoints vai ser tirado do inimigo dado dados (1 caveira = 1 hit)
        int cont = 0;
        for (int i = 0; i < quantityOfAttackDices; i++) // looping para quantidade de dados para ataque
            if(dice.combatDice() == SideDice.SKULL){
                cont++;
            }
        character.removeLifePointsWithDefense(cont); // remove life points
    }

    protected abstract int hitDefence(); //quanto de lifepoints vai ser defendido de ataque do inimigo dado dados

    public abstract boolean isOnSight(Character character);// verifica se personagem está em alcance da arma

}
