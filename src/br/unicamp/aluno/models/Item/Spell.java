package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Character.Character;

//Classe mãe dos feitiços
public abstract class Spell implements Item {
	private boolean isDestroyed;

	public Spell(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	
	//Retorna se item deve ser detruido depois do uso
	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	//Metodo abstrato para lançar feitiços
	public abstract void cast(Character character);

}
