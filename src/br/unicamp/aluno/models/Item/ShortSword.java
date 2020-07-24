package br.unicamp.aluno.models.Item;

public class ShortSword extends Weapon {
    public ShortSword(boolean isDestroyed, boolean bothHands, int weaponReach, int attackBonus) {
        super(false, false, 1, 0); // preciso definir
    }
    
    @Override
    public String toString() {
    	return "Short Sword";
    }
}
