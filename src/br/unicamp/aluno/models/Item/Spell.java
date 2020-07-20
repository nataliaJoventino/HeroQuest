package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Character.Character;

//Classe mãe dos feitiços
public abstract class Spell extends Item {
	private boolean isDestroyed;

	public Spell(String name, boolean isDestroyed) {
		super(name);
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() { // retorna se item deve ser detruido depois do uso
		return isDestroyed;
	}

	//Metodo abstrato para lançar feitiços
	public abstract void cast(Character character);

}
