package br.unicamp.aluno.models;

//Porta
public class Door extends Traceable{
	
	private boolean opened;
	
	//Construtor da classe
	public Door(int x, int y) {
		super(x, y);
	}
	
	//Abre a porta
	public void open() {
		this.opened = true;
		return;
	}
	
	//Verifica se a porta está aberta
	private boolean isOpened() {
		return opened;
	}
	
	//ToString
	@Override
	public String toString() {
		//Caso a porta esteja aberta
		if(this.isOpened()) {
			return "/";
		}
		//Caso a porta esteja fechada
		else {
			return "U";
		}
	}

}
