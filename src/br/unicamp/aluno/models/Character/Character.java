package br.unicamp.aluno.models.Character;

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

    public Character(int x, int y, int quantityOfAttackDices, int quantityOfDefenceDices, int lifePoints, int inteligencePoints) { //recebe via contrutor todas a informações
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

    protected abstract void move(); // personagem deve implementar sua movimentação
    protected abstract void action(); // personagem deve implementar sua ação






}
