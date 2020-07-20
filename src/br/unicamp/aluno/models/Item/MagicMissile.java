package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Character.Character;

public class MagicMissile extends Spell {
    private final int DAMAGE_TARGET = 2;
    private final int ARROW_NUMBER = 3; // as três flechas são jogadas de uma vez?

    public MagicMissile(String name) {
        super(name, false);
    }

    @Override
    public void cast(Character character) {
        character.removeLifePoints(ARROW_NUMBER * DAMAGE_TARGET);

    }
}
