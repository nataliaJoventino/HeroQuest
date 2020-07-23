package br.unicamp.aluno.models.Character.Monster;

import java.util.ArrayList;

import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Item.Dagger;
import br.unicamp.aluno.models.Item.Weapon;

public class Goblin extends Monster {
    private ArrayList<Weapon> daggers;


    public Goblin(int x, int y, int numDagger) { // ele tá falando de punhal a espadinha ou era para ser poção?
        super(x, y, 1,2,3,2); // definir pontos com zero
        daggers = new ArrayList();
        for (int i = 0; i < numDagger; i++)
            daggers.add(instanciaPunhal());
    }

    private Weapon instanciaPunhal(){
        return  new Dagger();
    }

    private int distAdjacent(Hero hero, Direction direction){ //dada a direção faz uma projeção do goblin na mesma e analisa distancia
        Traceable dir = direction.getTraceable();
        return distance(hero.getPositionX(), hero.getPositionY(), (this.getPositionX() + dir.getPositionX()), (this.getPositionY() + dir.getPositionY()));
    }

    private int distance(int x0, int y0, int x1, int y1){ // distancia entre dois pontos (geometria taxi)
        int absX = Math.abs((x0 - x1)); // guarda valor absoluto da diferença x
        int absY = Math.abs((y0 - y1));// guarda valor absoluto da diferença y

        return absX + absY;
    }

    private boolean smallest(int a, int b){ // encontra menor entre a e b
        if (a <= b)
            return true;

        return false;
    }


    public void move(Hero hero){
        int distUp = distAdjacent(hero, Direction.UP), distDown = distAdjacent(hero, Direction.DOWN);
        int distRight = distAdjacent(hero, Direction.RIGHT), distLeft = distAdjacent(hero, Direction.LEFT);

        if (smallest(distUp, distDown) && smallest(distUp, distRight) && smallest(distUp, distLeft)){ //verifica se direção para cima é menor distancia de todas
            move(Direction.UP);
            setCurrentDirection(Direction.UP);
        } else if (smallest(distDown, distUp) && smallest(distDown, distRight) && smallest(distDown, distLeft)){ //verifica se direção para baixo é menor distancia de todas
            move(Direction.DOWN);
            setCurrentDirection(Direction.DOWN);
        } else if (smallest(distRight, distLeft) && smallest(distRight, distUp) && smallest(distRight, distDown)) { //verifica se direção para direita é menor distancia de todas
            move(Direction.RIGHT);
            setCurrentDirection(Direction.RIGHT);
        } else { // caso nenhuma anterior seja verdadeira resta a esquerda como menor
            move(Direction.LEFT);
            setCurrentDirection(Direction.LEFT);
        }
    }

    @Override
    public void hit(Character character) {
        int attackBonus = daggers.get(daggers.size() - 1).getAttackBonus(); // pega ultimo elemento na lista sempre fazer excessão caso não haja mais punhais
        addAttackDice(attackBonus);
        super.hit(character);
        removeAttackDice(attackBonus);
    }

    @Override
    public boolean isOnSight(Character character) {
        int x = this.getPositionX() + (getCurrentDirection().getTraceable().getPositionX() * daggers.get(daggers.size() - 1).getWeaponReach()); // pega direção atual e multiplica pelo alcance da arma somando com a coordenada atual para projetar ataque
        int y = this.getPositionY() + (getCurrentDirection().getTraceable().getPositionY() * daggers.get(daggers.size() - 1).getWeaponReach());

        if (character.getPositionX() > this.getPositionX() && character.getPositionX() <= x) //verifica se personagem esta a frente de monstro ou no alcance da arma em x
            if (character.getPositionY() > this.getPositionY() && character.getPositionY() <= y)
                return  true;

        return false;
    }

    @Override
    public String toString() {
        return "GO";
    }
}
