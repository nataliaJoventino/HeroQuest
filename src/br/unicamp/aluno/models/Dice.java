package br.unicamp.aluno.models;

import java.util.Random;

public class Dice {
    private final int BOUND_DICE = 6;
    private Random dice;

    public Dice(){
        dice = new Random();
    }

    public int redDice(){
        return (dice.nextInt(BOUND_DICE) + 1);
    }

    public String combatDice(){
        String diceResult = "";
        int diceNumber = dice.nextInt(BOUND_DICE) + 1;


        if (diceNumber >= 1 && diceNumber <= 3)
            diceResult = SideDice.SKULL.toString();

        else if (diceNumber >= 4 && diceNumber <= 5)
            diceResult = SideDice.HERO_SHIELD.toString();

        else
            diceResult = SideDice.MONSTER.toString();

        return diceResult;
    }

}
