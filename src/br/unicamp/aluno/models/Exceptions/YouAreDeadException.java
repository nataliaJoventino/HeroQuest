package br.unicamp.aluno.models.Exceptions;

//O Jogador está morto por algum motivo
public class YouAreDeadException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//Construtor e mensagem
	public YouAreDeadException() {
		super("Seus Pontos de vida chegaram a zero, você está morto!");
	}


	public YouAreDeadException(String message) {
		super(message);
	}

	public YouAreDeadException(String message, Throwable cause) {
		super(message, cause);
	}

	public YouAreDeadException(Throwable cause) {
		super(cause);
	}
}
