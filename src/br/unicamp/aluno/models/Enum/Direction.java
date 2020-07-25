package br.unicamp.aluno.models.Enum;

import br.unicamp.aluno.models.Point;

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
}
