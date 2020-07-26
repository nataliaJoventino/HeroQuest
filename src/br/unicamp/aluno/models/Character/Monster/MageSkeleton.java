package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Item.MagicMissile;
import br.unicamp.aluno.models.Item.Spell;
import br.unicamp.aluno.models.Item.Weapon;

import java.util.ArrayList;

public class MageSkeleton extends Monster {
    private ArrayList<Weapon> daggers;
    private Spell spell;
    public MageSkeleton(int x, int y, int numDagger) {
        super(x, y, 2,2,4,2); //definir pontos com zero
        MagicMissile magicMissile = new MagicMissile();
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
        Weapon dagger =  daggers.get(daggers.size() - 1);
        int attackBonus = dagger.getAttackBonus(); // pega ultimo elemento na lista sempre fazer excessão caso não haja mais punhais
        addAttackDice(attackBonus);
        super.hit(character);
        removeAttackDice(attackBonus);

        if (dagger.isDestroyed())
            daggers.remove(dagger);
    }

    @Override
    public boolean isOnSight(Character character) {
        Weapon dagger = daggers.get(daggers.size() - 1);
        return  onSight(character, dagger);
    }

    @Override
	public String toString() {
		return "MS";
	}

}
