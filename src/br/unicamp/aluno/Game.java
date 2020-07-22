package br.unicamp.aluno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import br.unicamp.aluno.models.Door;
import br.unicamp.aluno.models.Traceable;
import br.unicamp.aluno.models.Trap;
import br.unicamp.aluno.models.Treasure;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Character.Monster.Goblin;
import br.unicamp.aluno.models.Character.Monster.MageSkeleton;
import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Character.Monster.Skeleton;
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

		// guardando o tamanho do mapa
		this.map = new String[xSize][ySize];
		this.xMapSize = xSize;
		this.yMapSize = ySize;
		this.hero = player;

		// Iniciando a variável de localizaveis no mapa
		this.traceablesOnMap = new ArrayList<Traceable>();
		this.monstersOnMap = new ArrayList<Monster>();

		// criando o mapa
		refreshMap();

		// --------------------------------------------

		// Adicionando inimigos em lugares aleatórios do mapa
		Random generator = new Random();

		// Adicionando 6 Goblins no mapa
		for (int i = 0; i < 6; i++) {

			// gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);

			// Quantidade de adagas aleatorizada
			int randomQtdDaggers = generator.nextInt(10);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomX][randomY].equals("--") && !map[randomX][randomY].equals(">>")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo os goblins
			Goblin goblin = new Goblin(randomX, randomY, randomQtdDaggers);
			monstersOnMap.add(goblin);
		}

		// Adicionando 2 esqueletos magos no mapa
		for (int i = 0; i < 2; i++) {

			// gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomX][randomY].equals("--") && !map[randomX][randomY].equals(">>")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo os esqueletos magos
			MageSkeleton mageSkeleton = new MageSkeleton(randomX, randomY, 0); // gerar numero aleatório de punhais
			monstersOnMap.add(mageSkeleton);
		}

		// Adicionando 4 esqueletos no mapa
		for (int i = 0; i < 4; i++) {

			// gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomX][randomY].equals("--") && !map[randomX][randomY].equals(">>")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo os esqueletos
			Skeleton Skeleton = new Skeleton(randomX, randomY, null);
			monstersOnMap.add(Skeleton);
		}

		// ---------------------------------

		// Adicionando uma quantidade entre 4 e 6 tesouros no mapa
		int randomQtdTreasures = ThreadLocalRandom.current().nextInt(4, 7);
		for (int i = 0; i < randomQtdTreasures; i++) {

			// gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomX][randomY].equals(">>") && !map[randomX][randomY].equals("--")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo os tesouros
			Treasure treasure = new Treasure(randomX, randomY);
			addTraceablesOnGame(treasure);
		}
		// Adicionando 8 armadilhas no mapa
		for (int i = 0; i < 8; i++) {

			// gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomX][randomY].equals("--") && !map[randomX][randomY].equals(">>")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo as armadilhas
			Trap trap = new Trap(randomX, randomY);
			addTraceablesOnGame(trap);
		}

		// inserindo as portas no mapa:
		for (int i = 0; i < xSize; i++) {

			// gerando um numero aleatório para ser posto como distância entre as portas
			int randomSpace = ThreadLocalRandom.current().nextInt(3, 5);

			// Percorrendo todas as colunas do mapa
			for (int j = 0; j < ySize; j += randomSpace) {
				// Sempre mudando esse espaço para ser mais aleatório ainda
				randomSpace = ThreadLocalRandom.current().nextInt(3, 5);
				try {
					// Verificando se a porta não ficará na quina da parede das salas
					if (map[i][j].equals("##") && map[i][j - 1].equals("##") && map[i][j + 1].equals("##")
							&& !map[i + 1][j].equals("##") && !map[i - 1][j].equals("##")) {

						// Adicionando a porta no mapa
						traceablesOnMap.add(new Door(i, j));
					}
				}
				// Caso a coluna j-1 ou j+1 esteja fora da nossa tabela
				catch (IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		// Atualizando o mapa com a posição dos personagens
		refreshMap();

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

	private void refreshMap() {

		// resetando o mapa (é mais performático do que ficar mudando cada personagem)
		// Lendo arquivo do mapa
		try {
			// Recebendo o arquivo de texto do mapa
			FileReader arq = new FileReader("map.txt");
			BufferedReader br = new BufferedReader(arq);

			// lê a linha do arquivo
			String line = br.readLine();

			String[] formatedLine = line.strip().split(" ");

			// iterando sobre as colunas
			for (int j = 0; j < yMapSize; j++) {
				this.map[0][j] = formatedLine[j];
			}

			// Setando para continuar a partir da segunda linha
			int i = 1;
			// Lendo as outras linhas
			while (line != null) {

				// Formatando assim como feito acima
				line = br.readLine();

				if (line == null) {
					break;
				}

				formatedLine = line.strip().split(" ");

				// Iterando por colunaa
				for (int j = 0; j < this.yMapSize; j++) {
					this.map[i][j] = formatedLine[j];
				}

				i++;
			}
			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		// Adicionando o Player no mapa
		this.map[hero.getPositionX()][hero.getPositionY()] = hero.toString();

		// Atualizando os monstros
		for (Monster monster : monstersOnMap) {
			int newX = monster.getPositionX();
			int newY = monster.getPositionY();
			map[newX][newY] = monster.toString();
		}

		// Atualizando os outros traceables
		for (Traceable traceable : traceablesOnMap) {
			
			//Caso for armadilha iremos verificar se está ativa para que possamos camuflá-la
			try {
				Trap trap = (Trap)traceable;
				if(trap.isVisible()) {
					int x = trap.getPositionX();
					int y = trap.getPositionY();
					map[x][y] = traceable.toString();
				}
				else {
					//Mantendo a armadilha escondida
					continue;
				}
			}
			//Fluxo normal
			catch(ClassCastException e){
				int newX = traceable.getPositionX();
				int newY = traceable.getPositionY();
				map[newX][newY] = traceable.toString();
			}
			
		}

	}

}