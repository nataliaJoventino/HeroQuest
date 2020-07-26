package br.unicamp.aluno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import br.unicamp.aluno.models.MapObjects.Door;
import br.unicamp.aluno.models.EngineComponents.Point;
import br.unicamp.aluno.models.EngineComponents.SquareVision;
import br.unicamp.aluno.models.EngineComponents.Traceable;
import br.unicamp.aluno.models.MapObjects.Trap;
import br.unicamp.aluno.models.MapObjects.Treasure;
import br.unicamp.aluno.models.Character.Hero.Hero;
import br.unicamp.aluno.models.Character.Monster.Goblin;
import br.unicamp.aluno.models.Character.Monster.MageSkeleton;
import br.unicamp.aluno.models.Character.Monster.Monster;
import br.unicamp.aluno.models.Character.Monster.Skeleton;
import br.unicamp.aluno.models.Enum.Direction;
import br.unicamp.aluno.models.Exceptions.CantMoveException;
import br.unicamp.aluno.models.Exceptions.TrapsHurtMeException;
import br.unicamp.aluno.models.Exceptions.YouWonException;

public class Map {

	private String[][] map;

	private ArrayList<Traceable> traceablesOnMap;
	private ArrayList<Monster> monstersOnMap;
	private int xMapSize;
	private int yMapSize;
	private Hero hero;
	private boolean created;

	// Construtor do Jogo
	public Map(Hero player, int ySize, int xSize) {

		// guardando o tamanho do mapa
		this.map = new String[ySize][xSize];
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
			while (!map[randomY][randomX].equals("--") && !map[randomY][randomX].equals(">>")) {
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

			// Quantidade de adagas aleatorizada
			int randomQtdDaggers = generator.nextInt(10);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomY][randomX].equals("--") && !map[randomY][randomX].equals(">>")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo os esqueletos magos
			MageSkeleton mageSkeleton = new MageSkeleton(randomX, randomY, randomQtdDaggers);

			monstersOnMap.add(mageSkeleton);
		}

		// Adicionando 4 esqueletos no mapa
		for (int i = 0; i < 4; i++) {

			// gerando x e y aleatórios dentro do mapa
			int randomX = generator.nextInt(xSize);
			int randomY = generator.nextInt(ySize);

			// Caso o numero gerado não seja uma coordenada livre tentaremos gerar outro
			// número aleatório
			while (!map[randomY][randomX].equals("--") && !map[randomY][randomX].equals(">>")) {
				randomX = generator.nextInt(xSize);
				randomY = generator.nextInt(ySize);
			}

			// inserindo os esqueletos
			Skeleton skeleton = new Skeleton(randomX, randomY);
			monstersOnMap.add(skeleton);
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
			while (!map[randomY][randomX].equals(">>") && !map[randomY][randomX].equals("--")) {
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
			while (!map[randomY][randomX].equals("--") && !map[randomY][randomX].equals(">>")) {
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
						traceablesOnMap.add(new Door(j, i));
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

	public ArrayList<Monster> getMonstersOnMap() {
		return monstersOnMap;
	}

	public Hero getHero() {
		return hero;
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
	public boolean canIWalk(Direction direction) {
		int xRequested = hero.getPositionX() + direction.getPoint().getPositionX();
		int yRequested = hero.getPositionY() + direction.getPoint().getPositionY();

		// caso tiver o espaço desejado caminharemos com o player
		if (this.map[yRequested][xRequested].equals("--") || this.map[yRequested][xRequested].equals(">>")
				|| this.map[yRequested][xRequested].equals("//")) {

			// Verificando se irá pisar em alguma armadilha
			amIOnSomeTrap(xRequested, yRequested);

			return true;
		} else {
			Traceable traceable = null;
			for (Traceable t : traceablesOnMap)
				if (t.getPositionX() == xRequested && t.getPositionY() == yRequested) {
					traceable = t;
					break;
				}
			throw new CantMoveException(traceable);
		}
	}

	// Printa todo conteúdo do mapa
	public void printAllMap() {
		for (int i = 0; i < this.yMapSize; i++) {
			for (int j = 0; j < this.xMapSize; j++) {
				System.out.print(this.map[i][j] + " ");
			}
			System.out.println("");
		}
	}

	// Printa todo conteúdo do mapa que é visível ao player
	public void printMap() {

		// Os limites do primeiro quadrado da visão do player são:
		int firstLowerX = hero.getFirstSquareVision().getLowerX();
		int firstUpperX = hero.getFirstSquareVision().getGreaterX();

		int firstLowerY = hero.getFirstSquareVision().getLowerY();
		int firstUpperY = hero.getFirstSquareVision().getGreaterY();

		// Os limites do segundo quadrado são
		int secondLowerX = hero.getSecondSquareVision().getLowerX();
		int secondUpperX = hero.getSecondSquareVision().getGreaterX();

		int secondLowerY = hero.getSecondSquareVision().getLowerY();
		int secondUpperY = hero.getSecondSquareVision().getGreaterY();

		// Mostrando somente o primeiro quadrado que o player tem visão
		for (int i = 0; i < this.yMapSize; i++) {
			for (int j = 0; j < this.xMapSize; j++) {

				// Caso esteja dentro do campo de visão dele
				if ((j >= firstLowerX && j <= firstUpperX && i <= firstLowerY && i >= firstUpperY)
						|| (j >= secondLowerX && j <= secondUpperX && i <= secondLowerY && i >= secondUpperY)) {

					// Printando a posição do mapa
					System.out.print(this.map[i][j] + " ");
				}

				else {
					System.out.print("^^ ");
				}

			}
			System.out.println("");
		}
	}

	public void searchForTreasure() {
		// Buscando somente pelo campo de visão do heroi

		// Os limites do primeiro quadrado são:
		int firstLowerX = hero.getFirstSquareVision().getLowerX();
		int firstUpperX = hero.getFirstSquareVision().getGreaterX();

		int firstLowerY = hero.getFirstSquareVision().getLowerY();
		int firstUpperY = hero.getFirstSquareVision().getGreaterY();

		// Os limites do segundo quadrado são
		int secondLowerX = hero.getSecondSquareVision().getLowerX();
		int secondUpperX = hero.getSecondSquareVision().getGreaterX();

		int secondLowerY = hero.getSecondSquareVision().getLowerY();
		int secondUpperY = hero.getSecondSquareVision().getGreaterY();

		// Verificando a lista de traceables
		for (Traceable traceable : traceablesOnMap) {
			// Caso esteja dentro do campo de visão dele
			if ((traceable.getPositionX() >= firstLowerX && traceable.getPositionX() <= firstUpperX
					&& traceable.getPositionY() <= firstLowerY && traceable.getPositionY() >= firstUpperY)
					|| (traceable.getPositionX() >= secondLowerX && traceable.getPositionX() <= secondUpperX
							&& traceable.getPositionY() <= secondLowerY && traceable.getPositionY() >= secondUpperY)) {

				try {
					Treasure treasure = (Treasure) traceable;
					treasure.turnVisible();

					// Verificando a possibilidade de nascer um monstro:
					boolean willBorn = generateARandomBoolean();

					// Caso vá nascer
					if (willBorn) {
						maybeARandomMonster(treasure);
					}

					refreshMap();

				} catch (ClassCastException e) {
					continue;
				}
			}
		}
	}

	// Talvez um monstro nasça perto do baú, como no enunciado
	private void maybeARandomMonster(Treasure treasure) {

		int xTreasure = treasure.getPositionX();
		int yTreasure = treasure.getPositionY();

		// Verificando se as posições adjacentes ao tesouro estão livres e pegando uma
		Point adjacentPosition = new Point(0, 0);

		if (map[yTreasure][xTreasure + 1].contentEquals("--") || map[yTreasure][xTreasure + 1].contentEquals(">>")) {
			adjacentPosition.updatePosition(xTreasure + 1, yTreasure);
		} else if (map[yTreasure][xTreasure - 1].contentEquals("--")
				|| map[yTreasure][xTreasure - 1].contentEquals(">>")) {
			adjacentPosition.updatePosition(xTreasure - 1, yTreasure);
		} else if (map[yTreasure + 1][xTreasure].contentEquals("--")
				|| map[yTreasure + 1][xTreasure].contentEquals(">>")) {
			adjacentPosition.updatePosition(xTreasure, yTreasure + 1);
		} else if (map[yTreasure - 1][xTreasure].contentEquals("--")
				|| map[yTreasure - 1][xTreasure].contentEquals(">>")) {
			adjacentPosition.updatePosition(xTreasure, yTreasure - 1);
		}

		int adjacentX = adjacentPosition.getPositionX();
		int adjacentY = adjacentPosition.getPositionY();

		// Gerando um monstro aleatório:
		Random random = new Random();

		// Gera um numero aleatório entre 0 e 2
		switch (random.nextInt(3)) {
		// caso 0 criaremos um esqueleto
		case 0:
			monstersOnMap.add(new Skeleton(adjacentX, adjacentY));
			break;

		// caso 1 criamos um Esqueleto Mago
		case 1:
			// Quantidade de adagas aleatorizada
			int randomQtdDaggers = random.nextInt(10);

			// inserindo os esqueletos magos
			MageSkeleton mageSkeleton = new MageSkeleton(adjacentX, adjacentY, randomQtdDaggers);
			monstersOnMap.add(mageSkeleton);

		case 2:
			// Quantidade de adagas aleatorizada
			randomQtdDaggers = random.nextInt(10);
			// inserindo os goblins
			Goblin goblin = new Goblin(adjacentX, adjacentY, randomQtdDaggers);
			monstersOnMap.add(goblin);
		default:
			break;
		}

	}

	private boolean generateARandomBoolean() {
		Random random = new Random();
		return random.nextBoolean();
	}

	public void searchForTrap() {
		// Buscando somente pelo campo de visão do heroi

		// Os limites do primeiro quadrado são:
		int firstLowerX = hero.getFirstSquareVision().getLowerX();
		int firstUpperX = hero.getFirstSquareVision().getGreaterX();

		int firstLowerY = hero.getFirstSquareVision().getLowerY();
		int firstUpperY = hero.getFirstSquareVision().getGreaterY();

		// Os limites do segundo quadrado são
		int secondLowerX = hero.getSecondSquareVision().getLowerX();
		int secondUpperX = hero.getSecondSquareVision().getGreaterX();

		int secondLowerY = hero.getSecondSquareVision().getLowerY();
		int secondUpperY = hero.getSecondSquareVision().getGreaterY();

		// Verificando a lista de traceables
		for (Traceable traceable : traceablesOnMap) {
			// Caso esteja dentro do campo de visão dele
			if ((traceable.getPositionX() >= firstLowerX && traceable.getPositionX() <= firstUpperX
					&& traceable.getPositionY() <= firstLowerY && traceable.getPositionY() >= firstUpperY)
					|| (traceable.getPositionX() >= secondLowerX && traceable.getPositionX() <= secondUpperX
							&& traceable.getPositionY() <= secondLowerY && traceable.getPositionY() >= secondUpperY)) {

				try {
					Trap trap = (Trap) traceable;
					trap.turnVisible();
					refreshMap();

				} catch (ClassCastException e) {
					continue;
				}
			}
		}
	}

	// Verifica se o heroi pisou em alguma armadilha e retira vida dele
	private void amIOnSomeTrap(int xRequested, int yRequested) {
		for (Traceable traceable : traceablesOnMap) {
			// Caso a posição do herói seja a mesma que a da armadilha:
			if (xRequested == traceable.getPositionX() && yRequested == traceable.getPositionY()) {

				try {
					//Cast - Verificando se é uma armadilha
					Trap trap = (Trap) traceable;

					// Ativando a armadilha
					trap.active(hero);

					// Encerrando o turno do jogador com uma mensagem de erro
					throw new TrapsHurtMeException();

				} catch (ClassCastException e) {
					continue;
				}

			}
		}
	}

	public void refreshMap() {

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
			for (int j = 0; j < xMapSize; j++) {
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
				for (int j = 0; j < this.xMapSize; j++) {
					this.map[i][j] = formatedLine[j];
				}

				i++;
			}
			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		// Caso não tenham monstros no mapa o player vence
		if (monstersOnMap.isEmpty() && created) {
			throw new YouWonException();
		}

		// Atualizando os monstros
		for (Monster monster : monstersOnMap) {
			int newX, newY;
			if (monster.getLifePoints() > 0) {
				newX = monster.getPositionX();
				newY = monster.getPositionY();
				map[newY][newX] = monster.toString();
			}
		}

		// Atualizando os outros traceables
		for (Traceable traceable : traceablesOnMap) {

			// Caso for armadilha iremos verificar se está ativa para que possamos
			// camuflá-la
			try {
				Trap trap = (Trap) traceable;
				if (trap.isVisible()) {
					int x = trap.getPositionX();
					int y = trap.getPositionY();
					map[y][x] = traceable.toString();
				} else {
					// Mantendo a armadilha escondida
					continue;
				}
			}
			// Fluxo normal
			catch (ClassCastException e) {
				try {
					Treasure treasure = (Treasure) traceable;
					if (treasure.isVisible()) {
						int x = treasure.getPositionX();
						int y = treasure.getPositionY();
						map[y][x] = traceable.toString();
					} else {
						// Mantendo o tesouro escondido
						continue;
					}
				} catch (ClassCastException ex) {
					int newX = traceable.getPositionX();
					int newY = traceable.getPositionY();
					map[newY][newX] = traceable.toString();
				}
			}

		}

		// Adicionando o Player no mapa
		this.map[hero.getPositionY()][hero.getPositionX()] = hero.toString();

		calculateHeroVision();
		this.created = true;
	}

	private void calculateHeroVision() {
		int heroX = hero.getPositionX();
		int heroY = hero.getPositionY();

		// Variáveis que irão verificar até onde nosso personagem consegue ver
		int yVision = heroY;
		int xVision = heroX;

		// Para a visão efetuaremos uma dupla checagem
		// Isso está melhor desenhado e explicadp no nosso relatório

		// Primeiro quadrado de visão Y -> X
		SquareVision firstSquare = hero.getFirstSquareVision();

		// Buscando o canto superior esquerdo -------------------------

		// Verificando o máximo que conseguimos ver acima do personagem
		while (map[yVision][heroX].equals("--") || map[yVision][heroX].equals(">>")
				|| map[yVision][heroX].equals(hero.toString())) {
			yVision--;
		}
		// Verificando no maximoY qual seria o menor X visível
		while (map[yVision + 1][xVision].equals("--") || map[yVision + 1][xVision].equals(">>")
				|| map[yVision + 1][xVision].equals(hero.toString())) {
			xVision--;
		}

		// Guardando a coordenada X e Y do canto superior esquerdo
		firstSquare.setTopLeftCorner(xVision, yVision);

		// Buscando o canto superior direito -------------------------

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Verificando o máximo que conseguimos ver acima do personagem
		while (map[yVision][heroX].equals("--") || map[yVision][heroX].equals(">>")
				|| map[yVision][heroX].equals(hero.toString())) {
			yVision--;
		}

		// Verificando no maximoY qual seria o menor X visível
		while (map[yVision + 1][xVision].equals("--") || map[yVision + 1][xVision].equals(">>")
				|| map[yVision + 1][xVision].equals(hero.toString())) {
			xVision++;
		}

		// Guardando o ponto superior direito
		firstSquare.setTopRightCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o maximo central do eixo X -------------------------
		while (map[heroY][xVision].equals("--") || map[heroY][xVision].equals(">>")
				|| map[heroY][xVision].equals(hero.toString())) {
			xVision++;
		}

		// Guardando o ponto maximo central da direita
		firstSquare.setMiddleRightCorner(xVision, heroY);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o máximo central -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			yVision--;
		}

		// Guardando o ponto maximo central
		firstSquare.setTopCenter(heroX, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o minimo central -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			yVision++;
		}

		// Guardando o ponto maximo central
		firstSquare.setBottomCenter(heroX, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o minimo central do eixo X -------------------------
		while (map[heroY][xVision].equals("--") || map[heroY][xVision].equals(">>")
				|| map[heroY][xVision].equals(hero.toString())) {
			xVision--;
		}

		// Guardando o ponto minimo central do eixo X
		firstSquare.setMiddleLeftCorner(xVision, heroY);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o canto inferior esquerdo ---------------------------
		while (map[yVision][heroX].equals("--") || map[yVision][heroX].equals(">>")
				|| map[yVision][heroX].equals(hero.toString())) {
			yVision++;
		}
		// Verificando no maximoY qual seria o menor X visível
		while (map[yVision - 1][xVision].equals("--") || map[yVision - 1][xVision].equals(">>")
				|| map[yVision - 1][xVision].equals(hero.toString())) {
			xVision--;
		}

		firstSquare.setBottomLeftCorner(xVision, yVision);

		// Buscando o canto inferior direito ---------------------------

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Verificando o máximo que conseguimos ver abaixo do personagem
		while (map[yVision][heroX].equals("--") || map[yVision][heroX].equals(">>")
				|| map[yVision][heroX].equals(hero.toString())) {
			yVision++;
		}

		// Verificando no maximoY qual seria o maior X visível
		while (map[yVision - 1][xVision].equals("--") || map[yVision - 1][xVision].equals(">>")
				|| map[yVision - 1][xVision].equals(hero.toString())) {
			xVision++;
		}

		firstSquare.setBottomRightCorner(xVision, yVision);

		// Segundo quadrado de visão X -> Y
		// --------------------------------------------------
		SquareVision secondSquare = new SquareVision();

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o canto superior esquerdo -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			xVision--;
		}

		while (map[yVision][xVision + 1].equals("--") || map[yVision][xVision + 1].equals(">>")
				|| map[yVision][xVision + 1].equals(hero.toString())) {
			yVision--;
		}

		// Guardando o dado
		secondSquare.setTopLeftCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o canto superior direito -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			xVision++;
		}

		while (map[yVision][xVision - 1].equals("--") || map[yVision][xVision - 1].equals(">>")
				|| map[yVision][xVision - 1].equals(hero.toString())) {
			yVision--;
		}

		secondSquare.setTopRightCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o centro superior -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			yVision--;
		}
		// Guardando o dado
		secondSquare.setTopCenter(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o minimo do eixo X -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			xVision--;
		}
		// Guardando o dado
		secondSquare.setMiddleLeftCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o centro maximo do eixo X -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			xVision++;
		}
		// Guardando o dado
		secondSquare.setMiddleRightCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o canto inferior esquerdo -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			xVision--;
		}

		while (map[yVision][xVision + 1].equals("--") || map[yVision][xVision + 1].equals(">>")
				|| map[yVision][xVision + 1].equals(hero.toString())) {
			yVision++;
		}

		// Guardando o dado
		secondSquare.setBottomLeftCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o canto inferior direito -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			xVision++;
		}

		while (map[yVision][xVision - 1].equals("--") || map[yVision][xVision - 1].equals(">>")
				|| map[yVision][xVision - 1].equals(hero.toString())) {
			yVision++;
		}

		// Guardando o dado
		secondSquare.setBottomRightCorner(xVision, yVision);

		// Resetando
		yVision = heroY;
		xVision = heroX;

		// Buscando o centro inferior do eixo Y -------------------------
		while (map[yVision][xVision].equals("--") || map[yVision][xVision].equals(">>")
				|| map[yVision][xVision].equals(hero.toString())) {
			yVision++;
		}
		// Guardando o dado
		secondSquare.setBottomCenter(xVision, yVision);

		hero.updateVision(firstSquare, secondSquare);
	}

	// Para o personagem teleportar dentro dos limites de sua visão
	public void printTeleportArea() {

		// Os limites do primeiro quadrado da visão do player são:
		int firstLowerX = hero.getFirstSquareVision().getLowerX();
		int firstUpperX = hero.getFirstSquareVision().getGreaterX();

		int firstLowerY = hero.getFirstSquareVision().getLowerY();
		int firstUpperY = hero.getFirstSquareVision().getGreaterY();

		// Os limites do segundo quadrado são
		int secondLowerX = hero.getSecondSquareVision().getLowerX();
		int secondUpperX = hero.getSecondSquareVision().getGreaterX();

		int secondLowerY = hero.getSecondSquareVision().getLowerY();
		int secondUpperY = hero.getSecondSquareVision().getGreaterY();

		// Preparando os index que o personagem irá ver
		int index = 0;

		// Mostrando somente o primeiro quadrado que o player tem visão
		for (int i = 0; i < this.yMapSize; i++) {
			for (int j = 0; j < this.xMapSize; j++) {

				// Caso esteja dentro do campo de visão dele
				if ((j >= firstLowerX && j <= firstUpperX && i <= firstLowerY && i >= firstUpperY)
						|| (j >= secondLowerX && j <= secondUpperX && i <= secondLowerY && i >= secondUpperY)) {

					// Trocando os espaços livres do mapa por indexes que o heroi escolherá
					if (this.map[i][j].equals("--") || this.map[i][j].equals(">>")) {
						// Adicionando o index com o 0 à esquerda caso seja menor que 10
						if (index < 10) {
							this.map[i][j] = "0" + Integer.toString(index);
							System.out.print(map[i][j] + " ");
						} else {
							this.map[i][j] = Integer.toString(index);
							System.out.print(map[i][j] + " ");
						}
						index++;
					} else {
						System.out.print(map[i][j] + " ");
					}
				}

				else {
					System.out.print("^^ ");
				}

			}
			System.out.println();
		}

	}

	public Point teleport(int index) {

		for (int i = 0; i < this.yMapSize; i++) {
			for (int j = 0; j < this.xMapSize; j++) {
				if (this.map[i][j].equals(Integer.toString(index))
						|| this.map[i][j].equals("0" + Integer.toString(index))) {
					refreshMap(); // volta o mapa para o que era antes
					return new Point(j, i);
//					hero.updatePosition(j, i);
				}
			}
		}

		refreshMap();
		return null;
	}

	// Para o personagem teleportar dentro dos limites de sua visão
	public void printTarget() {

		// Os limites do primeiro quadrado da visão do player são:
		int firstLowerX = hero.getFirstSquareVision().getLowerX();
		int firstUpperX = hero.getFirstSquareVision().getGreaterX();

		int firstLowerY = hero.getFirstSquareVision().getLowerY();
		int firstUpperY = hero.getFirstSquareVision().getGreaterY();

		// Os limites do segundo quadrado são
		int secondLowerX = hero.getSecondSquareVision().getLowerX();
		int secondUpperX = hero.getSecondSquareVision().getGreaterX();

		int secondLowerY = hero.getSecondSquareVision().getLowerY();
		int secondUpperY = hero.getSecondSquareVision().getGreaterY();

		// Preparando os index que o personagem irá ver
		int index = 0;

		ArrayList<String> monsterToString = monsterToString();
		boolean print = false;

		// Mostrando somente o primeiro quadrado que o player tem visão
		for (int i = 0; i < this.yMapSize; i++) {
			for (int j = 0; j < this.xMapSize; j++) {

				// Caso esteja dentro do campo de visão dele
				if ((j >= firstLowerX && j <= firstUpperX && i <= firstLowerY && i >= firstUpperY)
						|| (j >= secondLowerX && j <= secondUpperX && i <= secondLowerY && i >= secondUpperY)) {

					for (String monster : monsterToString) { // usa lista de monstros que estão no mapa

						// Trocando os espaços livres do mapa por indexes que o heroi escolherá
						if (this.map[i][j].equals(monster)) {
							// Adicionando o index com o 0 à esquerda caso seja menor que 10
							if (index < 10) {
								this.map[i][j] = "0" + Integer.toString(index);
								System.out.print(map[i][j]);
							} else {
								this.map[i][j] = Integer.toString(index);
								System.out.print(map[i][j]);
							}
							index++;
							print = false;
							break;
						} else {
							print = true;
						}
					}

					if (print)
						System.out.print(map[i][j]);
				}

				else {
					System.out.print("^^ ");
				}

			}
			System.out.println();
		}

	}

	public Monster target(int index) {

		for (int i = 0; i < this.yMapSize; i++) {
			for (int j = 0; j < this.xMapSize; j++) {
				if (this.map[i][j].equals(Integer.toString(index))
						|| this.map[i][j].equals("0" + Integer.toString(index))) {
					refreshMap();
					return getPosition(j, i);
				}
			}
		}

		refreshMap();
		return null;
	}

	private Monster getPosition(int x, int y) {
		for (Monster m : monstersOnMap)
			if (m.getPositionX() == x && m.getPositionY() == y)
				return m;
		return null; // tratar retorno nulo
	}

	private ArrayList<String> monsterToString() { // se forem criados novos monstros não precisará mudar nada
		ArrayList<String> monsters = new ArrayList<String>();

		for (Monster m : monstersOnMap) {
			if (!monsters.contains(m.toString()))
				monsters.add(m.toString());
		}
		return monsters;
	}

	public Treasure getTreasure() {
		int x = hero.getPositionX() + hero.getCurrentDirection().getPoint().getPositionX(); // pega item na frente da
																							// direção que herói está
		int y = hero.getPositionY() + hero.getCurrentDirection().getPoint().getPositionY();
		Treasure treasure = null;

		for (Traceable traceable : traceablesOnMap) {
			treasure = isTreasure(traceable);
			// se for tesouro verifica posição
			if (traceable.getPositionX() == x && traceable.getPositionY() == y && treasure.isVisible()) {// está em
																											// frente ao
																											// tesouro e
																											// verifica
																											// só se
																											// tesour
																											// visivel
				return treasure;
			}
		}

		return null;
	}

	private Treasure isTreasure(Traceable traceable) {
		try {
			Treasure treasure = (Treasure) traceable;
			return treasure;
		} catch (ClassCastException e) {
			return null;
		}
	}

	public boolean openDoor(Traceable traceable) {
		int x = traceable.getPositionX();
		int y = traceable.getPositionY();
		Door door;

		for (Traceable t : traceablesOnMap) {
			door = isDoor(t);
			if (t.getPositionX() == x && t.getPositionY() == y && door != null) {
				door.open();
				return true;
			}
		}

		return false;
	}

	private Door isDoor(Traceable traceable) {
		try {
			Door door = (Door) traceable;
			return door;
		} catch (ClassCastException e) {
			return null;
		}
	}

	public String getFromMap(int x, int y) {
		return map[y][x];
	}

}