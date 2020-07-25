package br.unicamp.aluno.models.Enum;

import br.unicamp.aluno.models.Point;

public enum Direction {
    UP,DOWN,RIGHT,LEFT;

    public Point getPoint(){
    	//Por que precisa instanciar?
        Point coordinate = null;

        switch (this){
            case UP:
            	//Esse new traceble não seria na verdade um updatePosition?
                coordinate = new Point(0, -1);
                break;

            case DOWN:
                coordinate = new Point(0, 1);
                break;

            case LEFT:
                coordinate = new Point(-1, 0);
                break;

            case RIGHT:
                coordinate = new Point(1, 0);
                break;
        }
        return coordinate;
    }
}
