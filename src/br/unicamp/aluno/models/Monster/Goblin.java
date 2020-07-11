package br.unicamp.aluno.models.Monster;

import br.unicamp.aluno.models.Item.Item;

public class Goblin extends Monster {

    public Goblin(int x, int y, int numPunhal) { // ele tá falando de punhal a espadinha ou era para ser poção?
        super(x, y);
        for (int i = 0; i < numPunhal; i++)
            instanciaPunhal();
//            setWeapon(); //seta punhais na lista de armas
    }

    private void instanciaPunhal(){
        // retorna punha
    }

}
