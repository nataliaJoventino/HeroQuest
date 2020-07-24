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
        int x = this.getPositionX() + (getCurrentDirection().getPoint().getPositionX() * weapon.getWeaponReach()); // pega direção atual e multiplica pelo alcance da arma somando com a coordenada atual para projetar ataque
        int y = this.getPositionY() + (getCurrentDirection().getPoint().getPositionY() * weapon.getWeaponReach());

        if ((character.getPositionX() > this.getPositionX() && character.getPositionX() <= x) || (character.getPositionX() >= x && character.getPositionX() < this.getPositionX())) //verifica se personagem esta a entre o monstro e alcance da arma em x
            if ((character.getPositionY() > this.getPositionY() && character.getPositionY() <= y) || (character.getPositionY() >= y && character.getPositionY() < this.getPositionY()))
                return  true;

        return false;
    }
    
    @Override
	public String toString() {
		return "SK";
	}

}