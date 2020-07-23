package br.unicamp.aluno.models.Exceptions;

public class NotEquippableException extends NullPointerException{

    public NotEquippableException() {
        super("Item não pode ser equipado!");
    }

    public NotEquippableException(String s) {
        super(s);
    }
}
