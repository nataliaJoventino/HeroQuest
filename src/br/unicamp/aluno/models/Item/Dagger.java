package br.unicamp.aluno.models.Item;

public class Dagger extends Weapon {

    public Dagger() {
        super(true, false, 4, 1); //preciso definir
    }
    
    @Override
    public String toString() {
    	return "Dagger";
    }
}
