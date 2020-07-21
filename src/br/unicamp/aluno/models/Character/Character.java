package br.unicamp.aluno.models.Character;

import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.SideDice;
import br.unicamp.aluno.models.Exceptions.YouAreDeadException;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Traceable;

import java.util.ArrayList;

public abstract class Character extends Traceable {
    private int quantityOfAttackDices;
    private int quantityOfDefenceDices;
    private int lifePoints;
    private int inteligencePoints;
    private ArrayList<Item> backpack;

    public Character(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) { //recebe via contrutor todas as informações
        super(x, y);
        this.quantityOfAttackDices = quantityOfAttackDices;
        this.quantityOfDefenceDices = quantityOfDefenceDices;
        this.lifePoints = lifePoints;
        this.inteligencePoints = inteligencePoints;
        this.backpack = new ArrayList();
    }


    protected void storeInBackpack(Item item){ //Colocar item na mochila
        backpack.add(item);
    }

    protected void removeFromBackpack(Item item){ //remove item da mochila
        backpack.remove(item);
    }

    protected boolean isInBackpack(Item item){  //verifica se item existe na mochila
        return backpack.contains(item);
    }

    public void addLifePoints(int value){ // adiciona quantidade vida ao personagem
        this.lifePoints += value; // value deve ser sempre positiva, fazer exceção?
    }

    public void removeLifePoints(int value) { //Remove uma certa quantidade de vida do personagem
        this.lifePoints -= value; // value deve ser sempre positiva, fazer exceção?
        if(lifePoints <= 0) {
            throw new YouAreDeadException();
        }
    }

    private void removeLifePoints(int value, Dice dice){ // remove pontos de vida descontando pontos de defesa
        int totalHit = value - hitDefence(dice); // remove hits de defesa
        if (totalHit > 0)
            removeLifePoints(totalHit);
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

    public int getQuantityOfAttackDices() { // retorna dados de ataque
        return quantityOfAttackDices;
    }

    public int getQuantityOfDefenceDices() { // retorna dados de defesa
        return quantityOfDefenceDices;
    }

    public int getLifePoints() { // retorna life points
        return lifePoints;
    }

    public int getInteligencePoints() { // retorna pontos de inteligencia
        return inteligencePoints;
    }

    public void printBackpack(){ // printa itens da mochila na tela com seu index
        for (int i = 0; i < backpack.size(); i++)
            System.out.println(""+ i +" "+backpack.get(i).toString()); // item deve ter nome?
    }

    public void move(Traceable traceable){ //recebe nova direção e altera (para o teleporte)
        super.updatePosition(traceable.getPositionX(), traceable.getPositionY());
    }

    public void move(Direction direction) { // anda uma posição dada a direção
        int x = super.getPositionX() + direction.getTraceable().getPositionX(); // coordenada x
        int y = super.getPositionY() + direction.getTraceable().getPositionY(); // cooredenada y

        super.updatePosition(x,y);
    }

    public void hit(Character character, Dice dice){ //quanto de lifepoints vai ser tirado do inimigo dado dados (1 caveira = 1 hit)
        int cont = 0;
        for (int i = 0; i < quantityOfAttackDices; i++) // looping para quantidade de dados para ataque
            if(dice.combatDice() == SideDice.SKULL){
                cont++;
            }
        character.removeLifePoints(cont, dice); // remove life points
    }

    protected abstract int hitDefence(Dice dice); //quanto de lifepoints vai ser defendido de ataque do inimigo dado dados

}
