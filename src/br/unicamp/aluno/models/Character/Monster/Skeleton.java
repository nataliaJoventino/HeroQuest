package br.unicamp.aluno.models.Character.Monster;

import java.util.Random;

import br.unicamp.aluno.models.Character.Character;
import br.unicamp.aluno.models.Item.LongSword;
import br.unicamp.aluno.models.Item.ShortSword;
import br.unicamp.aluno.models.Item.Weapon;

public class Skeleton extends Monster {
	private Weapon weapon;

	public Skeleton(int x, int y) {
		super(x, y, 2, 1, 3, 2); // definir pontos com zero

		// Gerando uma arma aleatória
		Random random = new Random();

		// Gera um numero aleatório entre 0 e 1
		switch (random.nextInt(2)) {
		
		// caso 0 criaremos uma LongSword
		case 0:
			weapon = new LongSword();
			break;

		// caso 1 criamos uma ShortSword
		case 1:
			weapon = new ShortSword();
			break;
		default:
			break;
		}
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
		int x = this.getPositionX() + (getCurrentDirection().getPoint().getPositionX() * weapon.getWeaponReach()); // pega
		int y = this.getPositionY() + (getCurrentDirection().getPoint().getPositionY() * weapon.getWeaponReach());
		if ((character.getPositionX() > this.getPositionX() && character.getPositionX() <= x)
				|| (character.getPositionX() >= x && character.getPositionX() < this.getPositionX())) // verifica se
			if ((character.getPositionY() > this.getPositionY() && character.getPositionY() <= y)
					|| (character.getPositionY() >= y && character.getPositionY() < this.getPositionY()))
				return true;

		return false;
	}

    @Override
    public boolean isOnSight(Character character) {
        return onSight(character, weapon);
    }
    
    @Override
	public String toString() {
		return "SK";
	}

}