package br.unicamp.aluno.models.Monster;

import br.unicamp.aluno.models.Item.MagicMissile;

public class MageSkeleton extends Monster {

    public MageSkeleton(int x, int y) {
        super(x, y);
        MagicMissile magicMissile = new MagicMissile();
        setWeapon(magicMissile); // não achei informação sobre usar punhos no pdf, será que era para ser punhais?
    }



}
