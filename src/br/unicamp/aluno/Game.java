package br.unicamp.aluno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Trap;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Character.Monster.Goblin;
import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Exceptions.TrapsHurtMeException;
import br.unicamp.aluno.models.Exceptions.YouAreDeadException;

public class Game {

	private String[][] map;

	private ArrayList<Traceable> traceablesOnMap;
	private ArrayList<Monster> monstersOnMap;

	private int xMapSize;
	private int yMapSize;
	private Hero hero;

	// Construtor do Jogo
	public Game(Hero player, int xSize, int ySize) {

		// Criando o mapa do jogo -----------------------------------------

		// guardando o tamanho do mapa
		this.map = new String[xSize][ySize];
		this.xMapSize = xSize;
		this.yMapSize = ySize;
		this.hero = player;

		// Iniciando a variável de localizaveis no mapa
		this.traceablesOnMap = new ArrayList<Traceable>();
		this.monstersOnMap = new ArrayList<Monster>();

		// Lendo arquivo do mapa
		try {
			// Recebendo o arquivo de texto do mapa
			FileReader arq = new FileReader("map.txt");
			BufferedReader br = new BufferedReader(arq);

			// lê a linha do arquivo
			String line = br.readLine();
			
			String[] formatedLine = line.strip().split(" ");
						
			//iterando sobre as colunas
			for (int j = 0; j < ySize; j++) {
				this.map[0][j] = formatedLine[j];
			}

			//Setando para continuar a partir da segunda linha
			int i = 1;
			//Lendo as outras linhas
			while (line != null) {
				
				//Formatando assim como feito acima
				line = br.readLine();
				
				if(line == null) {
					break;
				}
				
				formatedLine = line.strip().split(" ");
				
				//Iterando por colunaa
				for (int j = 0; j < ySize; j++) {
					this.map[i][j] = formatedLine[j];
				}
				
				i++;
			}
			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		// --------------------------------------------

		// Adicionando o Player no mapa
		this.map[hero.getPositionX()][hero.getPositionY()] = hero.toString();
		
		//Adicionando inimigos em lugares aleatórios do mapa
		Random generator = new Random();
		
		//imaginando que teremos 4 goblins
		for (int i = 0; i < 5; i++) {
				
			//gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);
			
			//Quantidade de adagas aleatorizada
			int randomQtdDaggers = generator.nextInt(10);
			
			//Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro número aleatório
			while(!map[randomX][randomY].equals("--")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}
			
			//adicionando o goblin no mapa
			Goblin goblin = new Goblin(randomX, randomY, randomQtdDaggers);
			monstersOnMap.add(goblin);
			map[randomX][randomY] = goblin.toString();
		}

	}

	// Adiciona localizaveis no nosso mapa
	public void addTreaceables(Traceable traceable) {
		// Obtendo a localização do localizavel
		int traceableX = traceable.getPositionX();
		int traceableY = traceable.getPositionY();

		// Adicionando o localizavel no mapa
		this.map[traceableX][traceableY] = traceable.toString();

		// Registrando o localizavel nos traceables no jogo atual
		this.addTraceablesOnGame(traceable);

	}

	// Registra os traceables que estão no mapa
	private void addTraceablesOnGame(Traceable traceable) {
		this.traceablesOnMap.add(traceable);
	}

	// Remove os localizaveis que estão no jogo (Sem uso por enquanto, mas usaremos
	// para matar monstros, etc depois)
//		private void removeTraceablesOnGame(Traceable traceable) {
//			this.traceablesOnMap.remove(traceable);
//		}

	// verifica a possibilidade e caminha com o player para a posição
	// xNow e yNow dizem a posição atual do player
	// xRequested e yRequested dizem a posiçao solicitada pelo usuário
	public boolean canIWalk(int xRequested, int yRequested) {

		int xNow = hero.getPositionX();
		int yNow = hero.getPositionY();

		// caso tiver o espaço desejado caminharemos com o player
		if (this.map[xRequested][yRequested].equals("--")) {

			// Andando com o player
			this.map[xRequested][yRequested] = hero.toString();
			this.map[xNow][yNow] = "--";

			return true;
		} else {
			// Depois tratamos isso com exceptions, deixa assim por enquanto kk
			System.out.println("Não pode andar ai não, a posição ta ocupada!");
			return false;
		}
	}

	// Printa todo conteúdo do mapa
	public void printMap() {

		for (int i = 0; i < this.xMapSize; i++) {
			for (int j = 0; j < this.yMapSize; j++) {
				System.out.print(this.map[i][j]);
			}
			System.out.println("");
		}
	}

	// Verifica se o heroi pisou em alguma armadilha e retira vida dele
	private void amIOnSomeTrap() {
		for (Traceable traceable : traceablesOnMap) {
			// Caso a posição do herói seja a mesma que a da armadilha:
			if (hero.getPositionX() == traceable.getPositionX() && hero.getPositionY() == traceable.getPositionY()
					&& traceable instanceof Trap) {
				// Cast
				Trap trap = (Trap) traceable;

				// Ativando a armadilha
				trap.active(hero);

				// Encerrando o turno do jogador com uma mensagem de erro
				throw new TrapsHurtMeException();

			}
		}
	}

	// Iniciando o jogo/turno
	public void start() {

		boolean exit = false;
		System.out.println("Game started!");

		// Ciclo do jogo
		while (!exit) {

			// Acontecimentos do jogo
			try {

			}

			// Tratamento de excessões que possam surgir
			catch (TrapsHurtMeException e) {
				System.out.println(e.getMessage());
			} catch (YouAreDeadException e) {
				System.out.println(e.getMessage());
				exit = true;
			}
		}

		System.out.println("Game termined. Bye!");

	}

}