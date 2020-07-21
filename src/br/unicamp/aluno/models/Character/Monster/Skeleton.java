package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Item.Weapon;

public class Skeleton extends Monster {

    public Skeleton(int x, int y, Weapon weapon) {
        super(x, y, 2,1,3,2); //definir pontos com zero
        super.storeInBackpack(weapon); // gerar aleatóriamente aqui?
    }
    
    @Override
	public String toString() {
		return "SK";
	}

}