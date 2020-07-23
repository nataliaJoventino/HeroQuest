package br.unicamp.aluno.models;

public class SquareVision {
	// Canto superior esquerdo
	private Point topLeftCorner;

	// Canto inferior esquerdo
	private Point bottomLeftCorner;

	// Maximo x central
	private Point middleLeftCorner;

	// Minimo x central
	private Point middleRightCorner;

	// Canto Superior direito
	private Point topRightCorner;

	// Canto inferior direito
	private Point bottomRightCorner;

	// Centro superior do eixo X
	private Point topCenter;

	// Centro inferior do eixo X
	private Point bottomCenter;

	// Quadrado em si - A nossa cruz mencionada no relatório
	private Point lowerX;
	private Point greaterX;
	private Point lowerY;
	private Point greaterY;

	// Construtor
	public SquareVision() {
		// Instanciando os pontos
		topLeftCorner = new Point(0, 0);
		topRightCorner = new Point(0, 0);
		middleLeftCorner = new Point(0, 0);
		middleRightCorner = new Point(0, 0);
		bottomLeftCorner = new Point(0, 0);
		bottomRightCorner = new Point(0, 0);
		topCenter = new Point(0, 0);
		bottomCenter = new Point(0, 0);

		lowerX = new Point(0, 0);
		lowerY = new Point(0, 0);
		greaterX = new Point(0, 0);
		greaterY = new Point(0, 0);
	}

	// Obtendo o nosso menor X
	public Point getLowerX() {

		if (bottomLeftCorner.getPositionX() >= middleLeftCorner.getPositionX()
				&& bottomLeftCorner.getPositionX() >= topLeftCorner.getPositionX()) {
			lowerX = bottomLeftCorner;
		} else if (middleLeftCorner.getPositionX() >= bottomLeftCorner.getPositionX()
				&& middleLeftCorner.getPositionX() >= topLeftCorner.getPositionX()) {
			lowerX = middleLeftCorner;
		} else {
			lowerX = topLeftCorner;
		}

		return this.lowerX;
	}

	// Obtendo o maior X
	public Point getGreaterX() {

		if (bottomRightCorner.getPositionX() <= middleRightCorner.getPositionX()
				&& bottomRightCorner.getPositionX() <= topRightCorner.getPositionX()) {
			greaterX = bottomRightCorner;
		} else if (middleRightCorner.getPositionX() <= bottomRightCorner.getPositionX()
				&& middleRightCorner.getPositionX() <= topRightCorner.getPositionX()) {
			greaterX = middleRightCorner;
		} else {
			greaterX = topRightCorner;
		}

		return this.greaterX;
	}

	// Obtendo o menor Y
	public Point getLowerY() {
		if (bottomLeftCorner.getPositionY() <= bottomCenter.getPositionY()
				&& bottomLeftCorner.getPositionY() <= bottomRightCorner.getPositionY()) {
			lowerY = bottomLeftCorner;
		} else if (bottomCenter.getPositionY() <= bottomLeftCorner.getPositionY()
				&& bottomCenter.getPositionY() <= bottomRightCorner.getPositionY()) {
			lowerY = bottomCenter;
		} else {
			lowerY = bottomRightCorner;
		}

		return this.lowerY;
	}

	// Obtendo o maior Y
	public Point getGreaterY() {
		if (topLeftCorner.getPositionY() >= topCenter.getPositionY()
				&& topLeftCorner.getPositionY() >= topRightCorner.getPositionY()) {
			greaterY = topLeftCorner;
		} else if (topCenter.getPositionY() >= topLeftCorner.getPositionY()
				&& topCenter.getPositionY() >= topRightCorner.getPositionY()) {
			greaterY = topCenter;
		} else {
			greaterY = topRightCorner;
		}

		return this.greaterY;
	}

	public void setTopCenter(int x, int y) {
		topCenter.updatePosition(x, y);
	}
	
	public void setBottomCenter(int x, int y) {
		bottomCenter.updatePosition(x, y);
	}
	
	public void setTopLeftCorner(int x, int y) {
		topLeftCorner.updatePosition(x, y);
	}

	public void setBottomLeftCorner(int x, int y) {
		bottomLeftCorner.updatePosition(x, y);
	}

	public void setTopRightCorner(int x, int y) {
		topRightCorner.updatePosition(x, y);
	}

	public void setBottomRightCorner(int x, int y) {
		bottomRightCorner.updatePosition(x, y);
	}

	public void setMiddleLeftCorner(int x, int y) {
		middleLeftCorner.updatePosition(x, y);
	}

	public void setMiddleRightCorner(int x, int y) {
		middleRightCorner.updatePosition(x, y);
	}


}
