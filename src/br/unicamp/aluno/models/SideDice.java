package br.unicamp.aluno.models;

public enum SideDice {
    SKULL, HERO_SHIELD, MONSTER_SHIELD;

    public String toString(){
        String dice = "";
        switch (this){
            case SKULL:
                dice = "Skull";
                break;

            case HERO_SHIELD:
                dice = "Hero Shield";
                break;

            case MONSTER_SHIELD:
                dice = "Monster Shield";
                break;
        }

        return dice;
    }
}
