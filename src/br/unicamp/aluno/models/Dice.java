package br.unicamp.aluno.models;

import br.unicamp.aluno.models.Enum.SideDice;

import java.util.Random;

public class Dice {
    private final int BOUND_DICE = 6;
    private final int MAX_REDDICE = 2;
    private Random dice;

    public Dice(){
        dice = new Random();
    }

    public int normalDice(){
        return (dice.nextInt(BOUND_DICE) + 1);
    }

    public int redDice(){ // lança 2 dados de 6 faces
        int sum = 0;

        for (int i = 0; i < MAX_REDDICE; i++)
            sum += normalDice();

        return sum;
    }

    public SideDice combatDice(){ //retornar uma lista com os resultados a a partir da quantidade de estatistica recebido por parametro ou deixar para a classe que vai usar fazer isso?
        SideDice diceResult;
        int diceNumber = dice.nextInt(BOUND_DICE) + 1;


        if (diceNumber >= 1 && diceNumber <= 3)
            diceResult = SideDice.SKULL;

        else if (diceNumber >= 4 && diceNumber <= 5)
            diceResult = SideDice.HERO_SHIELD;

        else
            diceResult = SideDice.MONSTER_SHIELD;

        return diceResult;
    }

}
