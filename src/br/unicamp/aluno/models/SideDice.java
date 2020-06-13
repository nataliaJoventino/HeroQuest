package br.unicamp.aluno.models;

public enum SideDice {
    SKULL, HERO_SHIELD, MONSTER;

    public String toString(){
        String dice = "";
        switch (this){
            case SKULL:
                dice = "Skull";
                break;

            case HERO_SHIELD:
                dice = "Hero Shield";
                break;

            case MONSTER:
                dice = "Monster";
                break;
        }

        return dice;
    }
}
