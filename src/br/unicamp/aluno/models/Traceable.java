package br.unicamp.aluno.models;

//Objetos que possuem localização no mapa
public class Traceable {
	private int positionX;
	private int positionY;
	
	//Construtor da classe
	public Traceable(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}
	
	//Obtendo a posição x
	public int getPositionX() {
		return positionX;
	}
	
	//Obtendo a posição y
	public int getPositionY() {
		return positionY;
	}

	//Alterando a posição do traceable
	protected void updatePosition(int x, int y){
		this.positionX = x;
		this.positionY = y;
	}
	
}
