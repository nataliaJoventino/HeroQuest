package br.unicamp.aluno.models;

public enum Direcao {
    X, Y;

    public int valueOf(){
        int number = -1;
        switch (this){
            case X:
                number = 0;
                break;

            case Y:
                number = 1;
                break;
        }
        return number;
    }

    public String toString(){
        String str = "";
        switch (this){
            case X:
                str = "x";
                break;

            case Y:
                str = "y";
                break;
        }
        return str;
    }
}
