package br.unicamp.aluno.models.Character;

import br.unicamp.aluno.models.EngineComponents.Dice;
import br.unicamp.aluno.models.Item.Weapon;
import br.unicamp.aluno.models.EngineComponents.Point;
import br.unicamp.aluno.models.EngineComponents.Traceable;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.SideDice;
import br.unicamp.aluno.models.Exceptions.CantMoveException;

public abstract class Character extends Traceable {
    private int quantityOfAttackDices;
    private int quantityOfDefenceDices;
    private int lifePoints;
    private int intelligencePoints;
    private Direction currentDirection;
    private Dice dice;
    private int moveAllowed;
    private int hitAttack;
    private int hitDefence;

    public Character(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int intelligencePoints) { //recebe via contrutor todas as informações
        super(x, y);
        this.quantityOfAttackDices = quantityOfAttackDices;
        this.quantityOfDefenceDices = quantityOfDefenceDices;
        this.lifePoints = lifePoints;
        this.intelligencePoints = intelligencePoints;
        dice = new Dice();
        moveAllowed = 0;

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

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    protected void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void addLifePoints(int value){ // adiciona quantidade vida ao personagem
        this.lifePoints += value; // value deve ser sempre positiva, fazer exceção?
    }

    public void removeLifePoints(int value) { //Remove uma certa quantidade de vida do personagem
        this.lifePoints -= value;
        if(lifePoints < 0)
            lifePoints = 0;
    }

    public void removeLifePointsWithDefense(int value){ // remove pontos de vida descontando pontos de defesa
        int totalHit;
        this.hitDefence = hitDefence();
        totalHit = value - this.hitDefence; // remove hits de defesa
        if (totalHit > 0)
            removeLifePoints(totalHit);

    }

    public int getHitAttack() {
        return hitAttack;
    }

    public int getHitDefence() {
        return hitDefence;
    }

    public int getLifePoints() { // retorna life points
        return lifePoints;
    }

    public int getIntelligencePoints() { // retorna pontos de inteligencia
        return intelligencePoints;
    }

    public int getMoveAllowed(){
        return moveAllowed;
    }

    public void generateMoveAllowed(){ //gera o quanto personagem pode andar via dado
        moveAllowed = dice.redDice();
    }

    public void move(Point point){ //recebe nova direção e altera (para o teleporte)
        super.updatePosition(point.getPositionX(), point.getPositionY());
    }

    public void move(Direction direction) { // anda uma posição dada a direção
        int x, y;
        currentDirection = direction;
        if (moveAllowed > 0) {
            x = super.getPositionX() + direction.getPoint().getPositionX(); // coordenada x
            y = super.getPositionY() + direction.getPoint().getPositionY(); // cooredenada y
            currentDirection = direction;
            super.updatePosition(x, y);
            moveAllowed--;
        } else
            throw new CantMoveException("Moves allowed are over. Wave must be ended.");
    }

    public void hit(Character character){ //quanto de lifepoints vai ser tirado do inimigo dado dados (1 caveira = 1 hit)
        int cont = 0;
        for (int i = 0; i < quantityOfAttackDices; i++) // looping para quantidade de dados para ataque
            if(dice.combatDice() == SideDice.SKULL){
                cont++;
            }
        hitAttack = cont;
        character.removeLifePointsWithDefense(cont); // remove life points
    }

    protected abstract int hitDefence(); //quanto de lifepoints vai ser defendido de ataque do inimigo dado dados

    public abstract boolean isOnSight(Character character);// verifica se personagem está em alcance da arma

    protected boolean onSight(Character character, Weapon weapon) { // retorna se personagem está na mira da arma em alguma das adjacencias
        try {
            Point left = reach(Direction.LEFT, weapon);
            Point right = reach(Direction.RIGHT, weapon);
            Point down = reach(Direction.DOWN, weapon);
            Point up = reach(Direction.UP, weapon);

            if ((character.getPositionX() >= left.getPositionX() && character.getPositionX() < this.getPositionX())&& (left.getPositionY() == character.getPositionY())) // verifica se character está entre hero e alcance da arma em x. Posição y é a mesma neste caso.
                return true;

            else if ((character.getPositionX() > this.getPositionX() && character.getPositionX() <= right.getPositionX()) && (right.getPositionY() == character.getPositionY())) //verifica se character está entre hero e alcance da arma em x. Posição y é a mesma neste caso.
                return true;

            if ((character.getPositionY() > this.getPositionY() && character.getPositionY() <= down.getPositionY()) && (down.getPositionX() == character.getPositionX())) //verifica se character está entre hero e alcance da arma em y. Posição x é a mesma neste caso.
                return true;

            if ((character.getPositionY() >= up.getPositionY() && character.getPositionY() < this.getPositionY()) && (up.getPositionX() == character.getPositionX())) //verifica se character está entre hero e alcance da arma em y. Posição x é a mesma neste caso.
                return true;

            return false;

        }catch (NullPointerException e) {
            return false; // item não pode ser usado para ataque
        }

    }

    private Point reach(Direction direction, Weapon weapon){
        try {
            int x = this.getPositionX() + (direction.getPoint().getPositionX() * weapon.getWeaponReach()); // pega direção atual e multiplica pelo alcance da arma somando com a coordenada atual para projetar ataque
            int y = this.getPositionY() + (direction.getPoint().getPositionY() * weapon.getWeaponReach());

            return new Point(x, y);

        } catch (NullPointerException e) {
            System.out.println("Item equipado não é arma com alcance");
            return null;
        }
    }

}
