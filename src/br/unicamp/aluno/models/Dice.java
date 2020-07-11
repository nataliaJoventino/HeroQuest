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

    public int redDice(){ // fazer o uso de dois dados direto aqui na clase ou na de quem vai usar?
        int sum = 0;

        for (int i = 0; i < MAX_REDDICE; i++)
            sum += (dice.nextInt(BOUND_DICE) + 1);

        return sum;
    }

    public String combatDice(){ //retornar uma lista com os resultados a a partir da quantidade de estatistica recebido por parametro ou deixar para a classe que vai usar fazer isso?
        String diceResult = "";
        int diceNumber = dice.nextInt(BOUND_DICE) + 1;


        if (diceNumber >= 1 && diceNumber <= 3)
            diceResult = SideDice.SKULL.toString();

        else if (diceNumber >= 4 && diceNumber <= 5)
            diceResult = SideDice.HERO_SHIELD.toString();

        else
            diceResult = SideDice.MONSTER_SHIELD.toString();

        return diceResult;
    }

}
