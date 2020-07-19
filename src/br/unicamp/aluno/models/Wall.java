package br.unicamp.aluno.models;

//Paredes
public class Wall extends Traceable {

	//Construtor da classe
	public Wall(int x, int y) {
		super(x, y);
	}
	
	//ToString
	@Override
	public String toString() {
		return "#";
	}
	
	

}
