package br.unicamp.aluno.models.Exceptions;

public class CantMoveException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CantMoveException() {
		super("A posição desejada está ocupada, tente outro lugar!");
	}

}
