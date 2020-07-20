package br.unicamp.aluno.models;

import br.unicamp.aluno.models.Character.Hero.Hero;

//Armadilha
public class Trap extends Traceable {
	
	//A armadilha está visivel?
	public boolean visible;

	//Ativando armadilha
	public void active(Hero hero) {
		//A Trap ficará visível após ser ativada
		this.visible = true;
		//Removendo vida do heroi
		hero.removeLifePoints(1);
	}
	
	//Verifica se está ativa
	public boolean isVisible() {
		return this.visible;
	}
	
	//Construtor da classe
	public Trap(int x, int y) {
		super(x, y);
	}

	//ToString
	@Override
	public String toString() {
		//Caso estiver visível
		if(isVisible()) {
			return "§§";			
		}
		//Caso ainda não tenha sido detectada
		else {
			return "--";
		}
	}
}
