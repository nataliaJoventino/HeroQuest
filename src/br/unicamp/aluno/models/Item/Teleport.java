package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Traceable;

public class Teleport extends Spell {
    private Traceable newPosition;

    public Teleport(String name) {
        super(name, false);
        newPosition = new Traceable(0,0);
    }

    public void positionToMove(int x, int y){
        newPosition.updatePosition(x,y);
    }

    @Override
    public void cast(Character character) {
        character.updatePosition(newPosition.getPositionX(), newPosition.getPositionY());
    }
}
