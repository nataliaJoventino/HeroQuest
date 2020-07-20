package br.unicamp.aluno.models.Character.Monster;

import br.unicamp.aluno.models.Item.MagicMissile;

public class MageSkeleton extends Monster {

    public MageSkeleton(int x, int y) {
        super(x, y, 0,0,0,0); //definir pontos com zero
        MagicMissile magicMissile = new MagicMissile("Stander Magic Missile");
        super.storeInBackpack(magicMissile);
    }

    @Override
	public String toString() {
		return "M";
	}

}
