package br.unicamp.aluno.models.MapObjects;

import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.EngineComponents.Traceable;

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
		return "§§";
	}

	public void turnVisible() {
		this.visible = true;

	}
}
