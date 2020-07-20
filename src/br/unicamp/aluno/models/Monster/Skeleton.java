package br.unicamp.aluno.models.Monster;

import br.unicamp.aluno.models.Item.Weapon;

public class Skeleton extends Monster {

    public Skeleton(int x, int y, Weapon weapon) {
        super(x, y);
        setWeapon(weapon); // recebe qualquer arma
    }
    
    @Override
	public String toString() {
		return "SK";
	}

}