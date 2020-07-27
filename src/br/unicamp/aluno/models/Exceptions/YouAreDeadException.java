package br.unicamp.aluno.models.Exceptions;

//O Jogador está morto por algum motivo
public class YouAreDeadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	//Construtor e mensagem
	public YouAreDeadException() {
		super("Your lifepoints now is zero, congratulations you are dead!");
	}


	public YouAreDeadException(String name) {
		super("Your lifepoints now is zero. Congratulations "+ name + " you are dead!");
	}

}
