package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Traceable;

public class Teleport extends Spell {
    private Traceable newPosition;

    public Teleport() {
        super(false);
    }

    public void positionToMove(Traceable traceable){
        newPosition = traceable;
    }

    @Override
    public void cast(Character character) {
        character.move(newPosition);
    }
    
    @Override
    public String toString() {
    	return "Teleport";
    }
}
