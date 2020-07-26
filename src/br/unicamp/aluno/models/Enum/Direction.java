package br.unicamp.aluno.models.Enum;

import br.unicamp.aluno.models.EngineComponents.Point;

import java.util.Random;

public enum Direction {
    UP,DOWN,RIGHT,LEFT;

    public Point getPoint(){
        switch (this){
            case UP:
                return new Point(0, -1);

            case DOWN:
                return new Point(0, 1);

            case LEFT:
                return new Point(-1, 0);

            case RIGHT:
                return new Point(1, 0);
        }

        return null;
    }

    // gera uma direção aleatória
    public Direction randomDirection() { // no mapa verifica se posição está livre
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
