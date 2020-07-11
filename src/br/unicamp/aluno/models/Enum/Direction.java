package br.unicamp.aluno.models.Enum;

import br.unicamp.aluno.models.Traceable;

public enum Direction {
    UP,DOWN,RIGHT,LEFT;

    public Traceable getTraceable(){
        Traceable coordinate = null;

        switch (this){
            case UP:
                coordinate = new Traceable(0, -1);
                break;

            case DOWN:
                coordinate = new Traceable(0, 1);
                break;

            case LEFT:
                coordinate = new Traceable(-1, 0);
                break;

            case RIGHT:
                coordinate = new Traceable(1, 0);
                break;
        }
        return coordinate;
    }
}
