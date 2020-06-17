package br.unicamp.aluno.models;

import java.util.Random;

public class Monster extends Traceable {
    private Item weapon;
    private int[] movePossibility;

    public Monster(int x, int y) {
        super(x, y); // por enquanto inicia em posição via construtor, mas intenção é fazer com que inicie em posição aleatória do mapa(seja via construtor ou internamente)
        movePossibility = new int[2];

        movePossibility[0] = -1;
        movePossibility[1] = 1;
    }

    private int newMove(int xy){
        Random randomXY = new Random();
        Dice dice = new Dice();
        int newDir = 0;
        int  dir = dice.redDice() * movePossibility[randomXY.nextInt(2)];


        if (Direcao.X.valueOf() == xy)
            newDir = super.getPositionX() + dir;
        else
            newDir = super.getPositionY() + dir;

        return newDir;
    }

    public void move(){
        Random randomXY = new Random();
        int dirXY = randomXY.nextInt(2);

        if (dirXY == Direcao.X.valueOf()) {
            super.updatePosition(newMove(dirXY),0);
        } else
            super.updatePosition(0,newMove(dirXY));
    }

    public void action(){ //atacar ou feitiço
        //* Implementação retorna pontos de ataque que o heroi irá perder*//
    }
}
