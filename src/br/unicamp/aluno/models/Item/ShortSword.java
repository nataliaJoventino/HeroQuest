package br.unicamp.aluno.models.Item;

public class ShortSword extends Weapon {
    public ShortSword() {
        super(false, false, 1, 2); // preciso definir
    }
    
    @Override
    public String toString() {
    	return "Short Sword";
    }
}
