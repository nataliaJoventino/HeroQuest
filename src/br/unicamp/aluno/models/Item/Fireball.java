package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.Game;
import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Traceable;

import java.util.ArrayList;

public class Fireball extends Spell{
    private final int DAMAGE_TARGET = 6;
    private final int DAMAGE_ADJACENT = 3;
    private ArrayList<Traceable> adjacent;
    private Game map;


    public Fireball(String name, Game map) { // vai precisar receber o mapa para encontrar adjacentes
        super(name, true);
        this.map = map;
    }

    @Override
    public void cast(Character character) {
        character.removeLifePoints(DAMAGE_TARGET);
        listAdjacent(character);

        for (Character c : map.getCharacter()){
            for(Traceable t : adjacent)
            if (c.getPositionX() == t.getPositionX() && c.getPositionY() == t.getPositionY())
                c.removeLifePoints(DAMAGE_ADJACENT); // deveria ter os dados de defesa aqui (character ter prorprio dado)
        }

        // precisa receber o mapa para causar danos na adjacencias, precisa de metodo em mapa que retorne objeto dada posição, caso seja item;

    }

    private void listAdjacent(Character character){ // gera lista com todas as adjacencias
        setAdjacent(character, Direction.UP);
        setAdjacent(character, Direction.DOWN);
        setAdjacent(character, Direction.RIGHT);
        setAdjacent(character, Direction.LEFT);
    }

    private void setAdjacent(Character character, Direction direction){ // pega adjacencias de personagem
        int x = character.getPositionX() + direction.getTraceable().getPositionX(); // soma direção com posição atual da personagem para pegar adjacente
        int y = character.getPositionY() + direction.getTraceable().getPositionY();
        Traceable traceable = new Traceable(x,y);
        adjacent.add(traceable);
    }
}
