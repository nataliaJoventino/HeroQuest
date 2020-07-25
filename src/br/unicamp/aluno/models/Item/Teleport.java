package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Point;
import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Character.Character;

public class Teleport extends Spell {
    private Point newPosition;

    public Teleport() {
        super(true);
    }

    public void positionToMove(Point point){
        newPosition = point;
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
