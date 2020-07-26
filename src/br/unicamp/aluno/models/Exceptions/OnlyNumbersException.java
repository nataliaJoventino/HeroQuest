package br.unicamp.aluno.models.Exceptions;

public class OnlyNumbersException extends NumberFormatException {

	private static final long serialVersionUID = 1L;

	public OnlyNumbersException() {
		super("Por favor, as coordenadas devem ser números!");
	}

	public OnlyNumbersException(String string) {
		super(string);
	}



}
