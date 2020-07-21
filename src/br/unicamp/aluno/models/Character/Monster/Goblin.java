package br.unicamp.aluno.models.Character.Monster;

public class Goblin extends Monster {

    public Goblin(int x, int y, int numPunhal) { // ele tá falando de punhal a espadinha ou era para ser poção?
        super(x, y, 1,2,3,2); // definir pontos com zero
        for (int i = 0; i < numPunhal; i++)
            instanciaPunhal();
         //precisa setar punhais na lista de armas
    }

    private void instanciaPunhal(){
        // retorna punha
    }

    @Override
	public String toString() {
		return "GO";
	}
}
