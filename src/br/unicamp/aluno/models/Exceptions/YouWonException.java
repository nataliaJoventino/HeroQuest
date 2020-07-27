package br.unicamp.aluno.models.Exceptions;

public class YouWonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public YouWonException() {
		super("Congratulations, you won the game!");
	}

	public YouWonException(String name) {
		super("Congratulations, you won the game!" + name + "you won the game!");
	}

}
