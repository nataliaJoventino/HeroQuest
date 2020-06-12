package br.unicamp.aluno.models;

public abstract class Traceable {
	private int positionX;
	private int positionY;
	
	public Traceable(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}
	
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	
	
}
