package br.unicamp.aluno.models.Character.Hero;

import br.unicamp.aluno.models.Item.Dagger;
import br.unicamp.aluno.models.Item.Fireball;
import br.unicamp.aluno.models.Item.MagicMissile;
import br.unicamp.aluno.models.Item.Teleport;

public class Wizard extends MysticHero {
	private final int INITIAL_DAGGER = 3;
	private final int INITIAL_MISSILE = 3;
	public Wizard(String name) {
		super(name,1,2,4,6); //Pontos de vida, inteligencia e defesa padrões
		for (int i = 0; i < INITIAL_DAGGER; i++)
			storeInBackpack(new Dagger());

		for (int i = 0; i < INITIAL_MISSILE; i++)
			storeInBackpack(new MagicMissile());

		storeInBackpack(new Fireball());
		storeInBackpack(new Teleport());
	}

	@Override
	public String toString() {
		return "WZ";
	}
}
