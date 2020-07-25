package br.unicamp.aluno.models.Item;

import java.util.ArrayList;

import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Point;
import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Enum.Direction;

public class Fireball extends Spell{
    private final int DAMAGE_TARGET = 6;
    private final int DAMAGE_ADJACENT = 3;
    private ArrayList<Traceable> adjacent;
    private ArrayList<Character> possibleTarget;


    public Fireball() { // vai precisar receber o mapa para encontrar adjacentes
        super(true);
    }

    public void setPossibleTarget(ArrayList<Monster> characters){
        for (Monster m : characters)
            possibleTarget.add(m);
    }

    public void setPossibleTarget(Hero characters){
        possibleTarget.add(characters);
    }

    @Override
    public void cast(Character character) {
        try{
            Hero hero = (Hero) character; // se alvo for herói não há necessidade de verificar adjacencias, pois só há um herói no mapa
        }catch (ClassCastException e){ // se não é herói alvo é monstro
            listAdjacent(character);
            for (Character c : possibleTarget){
                for(Traceable t : adjacent)
                    if (c.getPositionX() == t.getPositionX() && c.getPositionY() == t.getPositionY())
                        c.removeLifePointsWithDefense(DAMAGE_ADJACENT); // deveria ter os dados de defesa aqui (character ter prorprio dado)
            }
        }
        character.removeLifePointsWithDefense(DAMAGE_TARGET);
    }

    private void listAdjacent(Character character){ // gera lista com todas as adjacencias
        setAdjacent(character, Direction.UP);
        setAdjacent(character, Direction.DOWN);
        setAdjacent(character, Direction.RIGHT);
        setAdjacent(character, Direction.LEFT);
    }

    private void setAdjacent(Character character, Direction direction){ // pega adjacencias de personagem
        int x = character.getPositionX() + direction.getPoint().getPositionX(); // soma direção com posição atual da personagem para pegar adjacente
        int y = character.getPositionY() + direction.getPoint().getPositionY();
        Point point = new Point(x,y);
        adjacent.add(point);
    }
    
    @Override
    public String toString() {
    	return "Fire Ball";
    }
}
