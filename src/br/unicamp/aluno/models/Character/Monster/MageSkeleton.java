package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Item.MagicMissile;
import br.unicamp.aluno.models.Item.Spell;
import br.unicamp.aluno.models.Item.Weapon;

import java.util.ArrayList;

public class MageSkeleton extends Monster {
    private ArrayList<Weapon> daggers;
    private Spell spell;
    public MageSkeleton(int x, int y, int numDagger) {
        super(x, y, 2,2,4,2); //definir pontos com zero
        MagicMissile magicMissile = new MagicMissile("Stander Magic Missile");
        spell = magicMissile;
        daggers = new ArrayList();
        for (int i = 0; i < numDagger; i++)
            daggers.add(instanciaPunhal());
    }

    private Weapon instanciaPunhal(){
        // retorna punha
        return null;
    }

    public void throwSpell(Hero hero) {
        spell.cast(hero);
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
		return "MS";
	}

}
