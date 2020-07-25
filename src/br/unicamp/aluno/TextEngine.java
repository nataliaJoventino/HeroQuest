package br.unicamp.aluno;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.unicamp.aluno.models.Exceptions.OnlyNumbersException;
import br.unicamp.aluno.models.Point;
import br.unicamp.aluno.models.Treasure;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Character.Hero.MysticHero;
import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Enum.Hand;
import br.unicamp.aluno.models.Exceptions.CantMoveException;
import br.unicamp.aluno.models.Exceptions.NotEquippableException;
import br.unicamp.aluno.models.Item.Item;
import br.unicamp.aluno.models.Item.SimpleHeal;
import br.unicamp.aluno.models.Item.Spell;
import br.unicamp.aluno.models.Item.Teleport;
import br.unicamp.aluno.models.Item.Weapon;

public class TextEngine {
	private boolean exitSelected;
	private Game map;
	private Hero hero;
	private boolean wave; // controle de truno
	private boolean action; // controle de ação (só pode ser realizada uma vez por turno)
    private boolean move;
	private Scanner scanner;

	public TextEngine(Game map) {
		this.map = map;
		hero = map.getHero();
		this.scanner = new Scanner(System.in);
	}

    public void gameLoop(){
        Scanner scanner = new Scanner(System.in);
        exitSelected = false;
        wave = true;
        action = false;
        move = false;
        hero.generateMoveAllowed();

        System.out.println("Game started!");

        while (!exitSelected){ ///tem que fazer as exceções que param o jogo

            if (wave) {
                map.refreshMap();
                map.printMap();
                System.out.println("Moves allowed: " + hero.getMoveAllowed()
                        +" | Equipped right hand: " + hero.getRightHand()
                        +" | Equipped left hand: " + hero.getLeftHand()
                        +" | Life points: " + hero.getLifePoints()); //fazer if pra caso item seja de suas mãos e para não ficar aparecendo o null
                readCommandFromKeyboard(scanner);
            } else {
                System.out.println("Wave was ended");
                hero.generateMoveAllowed();
                wave = true;
                action = false;
                move = false;
                System.out.println("New wave started");
            }

        }


        map.printMap();
        System.out.println("Game terminated. Bye!");
        scanner.close();
    }

    private void readCommandFromKeyboard(Scanner scanner){
        Direction walking = null;
        String command = "";

        System.out.print("Enter the command : ");
        command = stringScanner();

        if (command.compareTo("w") == 0) //andar para cima
            walking = Direction.UP;

        else if (command.compareTo("a") == 0) // andar para esquerda
            walking = Direction.LEFT;

        else if (command.compareTo("d") == 0) // andar para direita
            walking = Direction.RIGHT;

        else if (command.compareTo("s") == 0) // andar para baixo
            walking = Direction.DOWN;

        else if (command.compareTo("h") == 0) // busca armadilha
            map.searchForTrap();

        else if (command.compareTo("p") == 0) { // abrir porta
            int x = hero.getPositionX() + hero.getCurrentDirection().getPoint().getPositionX();
            int y = hero.getPositionY() + hero.getCurrentDirection().getPoint().getPositionY();
            Point point = new Point(x,y);
            map.openDoor(point);

        }else if (command.compareTo("b") == 0){ //abrir mochila
            hero.printBackpack(); // por enquanto tá vazia
            int choice = choosingItem();
            if (choice != -1)
                equipBackpack(choice);
        }

        else if (command.compareTo("g") == 0) { // coletar tesouro
            Treasure treasure = map.getTreasure();
            boolean loop = true;
            Item item;

            System.out.print("To store, type: "
                    +"\n the number of the item"
                    +"\n e - store all items"
                    +"\n quit - to close treasure");


            while (loop) {
                treasure.printTreasure(); // por enquanto ainda tá vazio os tesouros
                System.out.print("Enter the command : ");
                command = stringScanner();

                if (command.compareTo("e") == 0) { //coletar todos os itens
                    storeAll(treasure);
                    loop = false;
                } else if (command.compareTo("quit") == 0) // andar para esquerda
                    loop = false;
                else {
                    item = treasure.removeTreasure(toInteger(command));
                    hero.storeInBackpack(item);
                }
            }

        }

        else
            action(command);

        try {
            if (walking != null && map.canIWalk(walking))
                hero.move(walking);
        }catch(CantMoveException e) {
            System.out.println(e.getMessage());
            if (hero.getMoveAllowed() == 0)
                wave = false;
        }

        System.out.println();
    }

    private void action(String command){
        if (!action){
            if (command.compareTo("t") == 0) { // busca tesouro
                map.searchForTreasure();
                action = true;
            }

            else if (command.compareTo("u") == 0){ // ataque/feitiço (falta colocar os pontos que afetam o ataque para o usuario ver)
                Monster monster = null;
                Hand hand = hero.isBothHandsUsed(); // retorna nulo se as duas mãos estão ocupadas ou vazias
                try {
                    if (hand != null) { // segurando um unico item
                        monster = allowAttack(hand);
                        hero.hit(monster, hand); // fazer erro se item não for arma

                    } else {

                        if (!hero.emptyHands() && hero.isBothHandItem()) { // se mãos não vazias e item ocupar as duas mãos
                            monster = allowAttack(null);
                            hero.hit(monster); //ataque

                        } else if (!hero.emptyHands()) {
                            String comm = "";
                            System.out.println("Choose weapon to attack:"
                                    + "\nr- right hand"
                                    + "\nl - left hand");
                            System.out.print("Enter the command : ");
                            comm = stringScanner();

                            if (comm.compareTo("r") == 0) {
                                monster = allowAttack(Hand.RIGHT);
                                hero.hit(monster, Hand.RIGHT);

                            } else if (comm.compareTo("l") == 0) {
                                monster = allowAttack(Hand.LEFT);
                                hero.hit(monster, Hand.LEFT);
                            }

                        } else { //mãos ficam vazias se equipadas com feitiço
                            MysticHero mysticHero = isMysticHero(hero);

                            if (mysticHero != null && mysticHero.getSpell() != null) { // se herói mistico, usa feitiço

                                try {
                                    Teleport teleport = (Teleport) mysticHero.getSpell(); // converte para teleport
                                    teleport(teleport); // imprime mapa e recebe numero da noca posição
                                    mysticHero.throwSpell(mysticHero);

                                } catch (ClassCastException e) {

                                    try {
                                        SimpleHeal simpleHeal = (SimpleHeal) mysticHero.getSpell();
                                        mysticHero.throwSpell(mysticHero);

                                    } catch (ClassCastException m) {
                                        monster = target();
                                        mysticHero.throwSpell(monster); // vai ser fireball ou magicMissile

                                    }
                                }
                            } else {
                                if (hero.emptyHands())
                                    System.out.println("No item equipped");
                                else
                                    System.out.println("Only mystic hero can cast spells"); // fazer excessão para sem spell
                            }
                        }
                    }
                } catch (NullPointerException e){
                    System.out.println("No target on the sight.");
                }

                if (monster != null) {
                    System.out.println("Monster has been attacked, life points left: " + monster.getLifePoints());
                    action = true;
                }
            }
        }
        else {
            System.out.println("Action has already been performed. Actions like attack and search for treasure can be made just once per wave!");
            if (move) // se herói tiver se movimentado e realizou ação wave finaliza
                wave = false;
        }
    }

	private void teleport(Teleport teleport) {
		boolean loop = true;

		while (loop == true) {
			// Printando a area que o teleporte pode ser feito
			map.printTeleportArea();

			// Recebendo as coordenadas
			System.out.print(":");
			try {

				try {
					int number = Integer.parseInt(stringScanner());

					// Teleportando
					Point position = map.teleport(number);
					teleport.positionToMove(position);

					// saindo do loop
					loop = false;
				} catch (NumberFormatException ex) {
					throw new OnlyNumbersException();
				}
			} catch (OnlyNumbersException e) {
				System.out.println(e.getMessage());
				loop = true;
			}
		}
	}

	private Monster target() {
		while (true) {
			// Printando a area que o teleporte pode ser feito
			map.printTarget();

			try {
				// Recebendo as coordenadas
				System.out.print("Enter item number:");
				int number = Integer.parseInt(stringScanner());

				return map.target(number); // retorna alvo
			} catch (ClassCastException e) {
				System.out.println("Por favor, as coordenadas devem ser números!");
			}
		}
	}

	private Monster allowAttack(Hand hand) { // permite ataque do ao monstro que está dentro da mira e de menor distancia (primeiro monstro a frente)
		ArrayList<Monster> isInSight = new ArrayList();
		for (Monster m : map.getMonstersOnMap())
			if (hand != null) {
				if (hero.isOnSight(m, hand)) // verifica se monstro está na mira da arma da mçao selecionada
					isInSight.add(m);
			} else if (hero.isOnSight(m)) // verifica se monstro esta na mira de arma que ocupa as duas mãos
				isInSight.add(m);

		return shortDistance(isInSight);
	}

    private Monster shortDistance(ArrayList<Monster> monsters){ // calcula qual monstro é o primeiro  na mira
        Monster shortMonster;
        try {
            int toCompare;
            shortMonster = monsters.get(0);
            int shorter = distance(hero.getPositionX(), hero.getPositionY(), shortMonster.getPositionX(), shortMonster.getPositionY());

            for (Monster m : monsters) {
                toCompare = distance(hero.getPositionX(), hero.getPositionY(), m.getPositionX(), m.getPositionY());
                if (shorter >= toCompare) {
                    shortMonster = m;
                    shorter = toCompare;
                }
            }

            System.out.println("Monster life points: " + shortMonster.getLifePoints());
        } catch (IndexOutOfBoundsException e){
            shortMonster = null;
        }
        return shortMonster;
    }

	private int distance(int x0, int y0, int x1, int y1) { // distancia entre dois pontos (geometria taxi)
		int absX = Math.abs((x0 - x1)); // guarda valor absoluto da diferença x
		int absY = Math.abs((y0 - y1));// guarda valor absoluto da diferença y

		return absX + absY;
	}

	private String stringScanner() { // lê entrada do teclado tipo string
		String command = "";
		boolean loop = true;

		while (loop) {
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

	private int choosingItem() {
		String command;
		boolean loop = true;

		System.out.print("Enter item number to equip it or type 'quit' to close backpack: ");
		command = stringScanner();

		if (command.compareTo("quit") == 0)
			return -1;
		else {
			return toInteger(command);
		}
	}

	private Weapon isWeapon(Item item) {
		try {
			Weapon weapon = (Weapon) item;
			return weapon;
		} catch (ClassCastException e) {
			return null;
		}
	}

	private Spell isSpell(Item item) {
		try {
			Spell spell = (Spell) item;
			return spell;
		} catch (ClassCastException e) {
			return null;
		}
	}

	private MysticHero isMysticHero(Hero hero) {
		try {
			MysticHero mysticHero = (MysticHero) hero;
			return mysticHero;
		} catch (ClassCastException e) {
			return null;
		}
	}

	private void equipBackpack(int command) {
		String comm = "";

		try {
			Weapon weapon = isWeapon(map.getHero().getInBackpack(command)); // verifica se é arma

			if (weapon != null) // se for equipa

				if (weapon.isBothHands()) // se é de duas mãos
					hero.equip(weapon);

				else { // se não precisa receber comando de qual mão carregar
					System.out.println("Choose hand to equip:" + "\nr- right hand" + "\nl - left hand");
					System.out.print("Enter the command : ");
					comm = stringScanner();

					if (comm.compareTo("r") == 0)
						hero.equip(weapon, Hand.RIGHT);

					else if (comm.compareTo("l") == 0)
						hero.equip(weapon, Hand.LEFT);
				}

			else
				throw new NotEquippableException();

		} catch (NotEquippableException e) {
			if (isMysticHero(hero) != null) { // só equipa feitiço se héroi é mistico
				Spell spell = isSpell(hero.getInBackpack(command));

				if (spell != null) // spell deve ser equipado sozinho
					hero.equip(spell);
				else
					throw new NotEquippableException();
			} else
				System.out.println("Herói não pode usar feitiço"); // fazer exception
		}
	}

	private int toInteger(String command) {
		try {
			int integer = Integer.parseInt(command);
			return integer;
		} catch (NumberFormatException e) {
			return -1; // não conseguiu converter
		}
	}

	private void storeAll(Treasure treasure) {
		for (int i = 0; i < treasure.size(); i++)
			hero.storeInBackpack(treasure.removeTreasure(i));
	}


}
