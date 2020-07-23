package br.unicamp.aluno;

import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Exceptions.NotEquippableException;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Item.Spell;
import br.unicamp.aluno.models.Item.Weapon;

import java.util.InputMismatchException;
import java.util.Scanner;

// AINDA TÔ AARRUMANDO E DEFININDO (TA BOM KKKKK)
public class TextEngine {
    private Game map;
    
    public TextEngine(Game map){
        this.map = map;
    }

    public Direction readCommandFromKeyboard(){
    	Scanner scanner = new Scanner(System.in);
        String command = "";
        boolean loop = true;

        System.out.print("Enter the command : ");

        command = stringScanner(scanner);

        if (command.compareTo("w") == 0) //andar para coma
            return Direction.UP;

        else if (command.compareTo("a") == 0) // andar para esquerda
            return Direction.LEFT;

        else if (command.compareTo("d") == 0) // andar para direita
            return Direction.RIGHT;

        else if (command.compareTo("s") == 0) // andar para baixo
            return Direction.DOWN;

        else if (command.compareTo("m") == 0) { //abrir mochila
            map.getHero().printBackpack();
            choosingItem(scanner);
        }
        //Busca por tesouros
        else if (command.compareTo("t") == 0) {
        	map.searchForTreasure();
        }
        //Busca por armadilhas
        else if (command.compareTo("h") == 0) {
        	map.searchForTrap();
        }
        //Teleporte
        else if (command.compareTo("v") == 0) {
        	loop = true;
        	while(loop == true) {
        		//Printando a area que o teleporte pode ser feito
        		map.printTeleportArea();
        		
        		try {
        			
        			//Recebendo as coordenadas
        			System.out.print("Digite o número desejado:");
        			int number = Integer.parseInt(stringScanner(scanner));
        			
        			//Teleportando
        			map.teleport(number);
        			
        			map.printMap();
        			
        			//saindo do loop
        			loop = false;        			
        		}catch(ClassCastException e) {
        			System.out.println("Por favor, as coordenadas devem ser números!");
        		}

        	}
        	
        }


        System.out.println();

        return null;



    }

    private String stringScanner(Scanner scanner){
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

    private void equipBackpack(int command, Scanner scanner){
        String comm = "";
        boolean loop = true;

        try{
            Weapon weapon = isWeapon(map.getHero().getInBackpack(command)); // verifica se é arma

            if (weapon != null) // se for equipa

                if(weapon.isBothHands()) // se é de duas mãos
                    map.getHero().equip(weapon);

                else { // se não precisa receber comando de qual mão carregar
                    System.out.print("Escolha a mão para equipar (r para direita ou l para esquerda): ");
                    comm = stringScanner(scanner);

                    if (comm.compareTo("r") == 0)
                        map.getHero().equip(weapon, Hand.RIGHT);

                    else if (comm.compareTo("l") == 0)
                        map.getHero().equip(weapon, Hand.LEFT);
                }

            else
                throw new NotEquippableException();

        } catch (NotEquippableException e){
            Spell spell = isSpell(map.getHero().getInBackpack(command));

            if (spell != null) //spell deve ser equipado sozinho
                map.getHero().equip(spell);
            else
                throw new NotEquippableException();
        }
    }



}
