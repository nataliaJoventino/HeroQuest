package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Dice;
import br.unicamp.aluno.models.Item.Weapon;

public class Skeleton extends Monster {
    private Weapon weapon;
    public Skeleton(int x, int y, Weapon weapon) {
        super(x, y, 2,1,3,2); //definir pontos com zero
        this.weapon = weapon; // gerar aleatóriamente aqui?
    }

    public void hit(Character character) {
        int attackBonus = weapon.getAttackBonus();
        addAttackDice(attackBonus);
        super.hit(character);
        removeAttackDice(attackBonus);

        if (weapon.isDestroyed())
            weapon = null;
    }

    @Override
    public boolean isOnSight(Character character) {
        return  onSight(character, weapon);
    }
    
    @Override
	public String toString() {
		return "SK";
	}

}