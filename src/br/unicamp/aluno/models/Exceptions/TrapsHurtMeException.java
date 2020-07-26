package br.unicamp.aluno.models.Exceptions;

//Exception que é lançada quando o jogador é pego em alguma armadilha
public class TrapsHurtMeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	//Mensagem lançada
	public TrapsHurtMeException() {
		super("Hurt by trap!");
	}


	public TrapsHurtMeException(String message) {
		super(message);
	}

	public TrapsHurtMeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TrapsHurtMeException(Throwable cause) {
		super(cause);
	}
}
