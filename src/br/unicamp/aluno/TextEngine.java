package br.unicamp.aluno;

import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Character.Hero.MisticHero;
import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Exceptions.NotEquippableException;
import br.unicamp.aluno.models.Item.*;
import br.unicamp.aluno.models.Traceable;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// AINDA TÔ AARRUMANDO E DEFININDO (TA BOM KKKKK)
public class TextEngine {
    private Game map;
    private Hero hero;

    public TextEngine(Game map){
        this.map = map;
        hero = map.getHero();
    }

    public void gameLoop(){
        Scanner scanner = new Scanner(System.in);

        while (true){ ///tem que fazer as exceções que param o jogo (não olhei ainda o exemplo no texto do projeto)
            map.printMap();
            readCommandFromKeyboard(scanner);

        }

//Habilitar para quando arrumar
//        map.printMap();
//        System.out.println("Mapa finalizado! Parabens");
//        scanner.close();
    }


    private void readCommandFromKeyboard(Scanner scanner){
        Direction walking = null;
        String command = "";
        boolean loop = true;

        System.out.print("Enter the command : ");

        command = stringScanner(scanner);

        if (command.compareTo("w") == 0) //andar para cima
            walking = Direction.UP;

        else if (command.compareTo("a") == 0) // andar para esquerda
            walking = Direction.LEFT;

        else if (command.compareTo("d") == 0) // andar para direita
            walking = Direction.RIGHT;

        else if (command.compareTo("s") == 0) // andar para baixo
            walking = Direction.DOWN;

        else if (command.compareTo("m") == 0) { //abrir mochila
            hero.printBackpack();
            choosingItem(scanner);

        } else if (command.compareTo("v") == 0) { //Teleporte


        } else if (command.compareTo("t") == 0) // busca tesouro
            map.searchForTreasure();

        else if (command.compareTo("h") == 0) // busca armadilha
            map.searchForTrap();

        else if (command.compareTo("u") == 0){ // ataque/feitiço (falta colocar os pontos que afetam o ataque para o usuario ver)
            Hand hand = hero.isBothHandsUsed(); // retorna nulo se as duas mãos estão ocupadas ou vazias

            if ( hand != null) // segurando um unico item
                hero.hit(allowAttack(hand), hand); // fazer erro se item não for arma

            else{

                if (!hero.emptyHands() && hero.isBothHandItem()) { // se mãos não vazias e item ocupar as duas mãos
                    hero.hit(allowAttack(null)); //ataque

                } else if (!hero.emptyHands()) {
                    String comm = "";
                    System.out.print("Escolha a arma para ataque (r para direita ou l para esquerda): ");
                    comm = stringScanner(scanner);

                    if (comm.compareTo("r") == 0)
                        hero.hit(allowAttack(Hand.RIGHT), Hand.RIGHT);

                    else if (comm.compareTo("l") == 0)
                        hero.hit(allowAttack(Hand.LEFT), Hand.LEFT);

                } else { //mãos ficam vazias se equipadas com feitiço
                    MisticHero misticHero = isMisticHero(hero);

                    if (misticHero != null){ // se herói mistico, usa feitiço

                        try{
                            Teleport teleport = (Teleport) misticHero.getSpell(); // converte para teleport
                            teleport(scanner, teleport); // imprime mapa e recebe numero da noca posição
                            misticHero.throwSpell(misticHero);

                        }catch (ClassCastException e){

                            try{
                                SimpleHeal simpleHeal = (SimpleHeal) misticHero.getSpell();
                                misticHero.throwSpell(misticHero);

                            }catch (ClassCastException m){
                                misticHero.throwSpell(target(scanner)); // vai ser fireball ou magicMissile

                            }
                        }
                    } else {
                        System.out.println("Herói não pode usar feitiço"); // fazer excessão
                    }
                }
            }

        }

        if (walking != null && map.canIWalk(walking))
            hero.move(walking);

        System.out.println();
    }

    private void teleport(Scanner scanner, Teleport teleport) {
        boolean loop = true;

        while (loop == true) {
            //Printando a area que o teleporte pode ser feito
            map.printTeleportArea();

            try {

                //Recebendo as coordenadas
                System.out.print("Digite o número desejado:");
                int number = Integer.parseInt(stringScanner(scanner));

                //Teleportando
                teleport.positionToMove(map.teleport(number));
//                map.printMap();

                //saindo do loop
                loop = false;
            } catch (ClassCastException e) {
                System.out.println("Por favor, as coordenadas devem ser números!");
            }
        }
    }

    private Monster target(Scanner scanner) {
        while (true) {
            //Printando a area que o teleporte pode ser feito
            map.printTarget();

            try {
                //Recebendo as coordenadas
                System.out.print("Digite o número desejado:");
                int number = Integer.parseInt(stringScanner(scanner));

                return map.target(number); // retorna alvo
//                map.printMap();
            } catch (ClassCastException e) {
                System.out.println("Por favor, as coordenadas devem ser números!");
            }
        }
    }

    private Monster allowAttack(Hand hand){ // permite ataque do ao monstro que está dentro da mira e de menor distancia (primeiro monstro a frente)
        ArrayList<Monster> isInSight = new ArrayList();
        for (Monster m : map.getMonstersOnMap())
            if (hand != null) {
                if (hero.isOnSight(m, hand)) // verifica se monstro está na mira da arma da mçao selecionada
                    isInSight.add(m);
            }else
            if (hero.isOnSight(m)) // verifica se monstro esta na mira de arma que ocupa as duas mãos
                isInSight.add(m);
        return shortDistance(isInSight);
    }

    private Monster shortDistance(ArrayList<Monster> monsters){ // calcula qual monstro é o primeiro  na mira
        int toCompare;
        Monster shortMonster = monsters.get(0);
        int shorter = distance(hero.getPositionX(), hero.getPositionY(), shortMonster.getPositionX(), shortMonster.getPositionY());

        for (Monster m : monsters) {
            toCompare = distance(hero.getPositionX(), hero.getPositionY(), m.getPositionX(), m.getPositionY());
            if (shorter >= toCompare){
                shortMonster = m;
                shorter = toCompare;
            }
        }

        return shortMonster;
    }

    private int distance(int x0, int y0, int x1, int y1){ // distancia entre dois pontos (geometria taxi)
        int absX = Math.abs((x0 - x1)); // guarda valor absoluto da diferença x
        int absY = Math.abs((y0 - y1));// guarda valor absoluto da diferença y

        return absX + absY;
    }

    private String stringScanner(Scanner scanner){ // lê entrada do teclado tipo string
        String command = "";
        boolean loop = true;

        while(loop) {
            try {
                command = scanner.nextLine();
                loop = false;

            } catch (InputMismatchException e) {
                System.out.println(e.getCause());
                System.out.println("Input Mismatch, try again");
                loop = true;

            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                System.out.print("Null Pointer, try a valid input");
                loop = true;
            }
        }

        return command;
    }

    private void choosingItem(Scanner scanner) {
        int command = 0;
        boolean loop = true;

        System.out.print("Enter item number to equip/use it: ");

        while (loop) {
            try {
                command = scanner.nextInt();
                loop = false;

            } catch (InputMismatchException e) {
                System.out.println(e.getCause());
                System.out.println("Input Mismatch, try again");
                loop = true;

            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                System.out.print("Null Pointer, try a valid input");
                loop = true;
            }
        }

        equipBackpack(command, scanner);
    }

    private Weapon isWeapon(Item item){
        try{
            Weapon weapon = (Weapon) item;
            return weapon;
        } catch (ClassCastException e){
            return null;
        }
    }

    private Spell isSpell(Item item){
        try{
            Spell spell = (Spell) item;
            return spell;
        } catch (ClassCastException e){
            return null;
        }
    }

    private MisticHero isMisticHero(Hero hero){
        try{
            MisticHero misticHero = (MisticHero) hero;
            return misticHero;
        } catch (ClassCastException e){
            return null;
        }
    }

    private void equipBackpack(int command, Scanner scanner){
        String comm = "";
        boolean loop = true;

        try{
            Weapon weapon = isWeapon(map.getHero().getInBackpack(command)); // verifica se é arma

            if (weapon != null) // se for equipa

                if(weapon.isBothHands()) // se é de duas mãos
                    hero.equip(weapon);

                else { // se não precisa receber comando de qual mão carregar
                    System.out.print("Escolha a mão para equipar (r para direita ou l para esquerda): ");
                    comm = stringScanner(scanner);

                    if (comm.compareTo("r") == 0)
                        hero.equip(weapon, Hand.RIGHT);

                    else if (comm.compareTo("l") == 0)
                        hero.equip(weapon, Hand.LEFT);
                }

            else
                throw new NotEquippableException();

        } catch (NotEquippableException e){
            if (isMisticHero(hero) != null) { // só equipa feitiço se héroi é mistico
                Spell spell = isSpell(hero.getInBackpack(command));

                if (spell != null) //spell deve ser equipado sozinho
                    hero.equip(spell);
                else
                    throw new NotEquippableException();
            } else
                System.out.println("Herói não pode usar feitiço"); // fazer exception
        }
    }



}
