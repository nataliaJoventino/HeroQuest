package br.unicamp.aluno.models.Exceptions;

public class CantThrowSpellException extends ClassCastException {

	private static final long serialVersionUID = 1L;

	public CantThrowSpellException() {
        super("Only mystic hero can cast spell!");
    }

    public CantThrowSpellException(String s) {
        super(s);
    }
}
