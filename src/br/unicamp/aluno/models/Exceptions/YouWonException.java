package br.unicamp.aluno.models.Exceptions;

public class YouWonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public YouWonException() {
		super("Congratulations, you won the game!");
	}
}
