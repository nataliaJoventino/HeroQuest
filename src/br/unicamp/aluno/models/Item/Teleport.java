package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Traceable;

public class Teleport extends Spell {
    private Traceable newPosition;

    public Teleport(String name) {
        super(name, false);
    }

    public void positionToMove(Traceable traceable){
        newPosition = traceable;
    }

    @Override
    public void cast(Character character) {
        character.move(newPosition);
    }
}
