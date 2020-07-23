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

	// Quadrado em si - A nossa cruz mencionada no relatório
	private Point lowerX;
	private Point greaterX;
	private Point lowerY;
	private Point greaterY;

	//Construtor
	public SquareVision() {
		//Instanciando os pontos
		topLeftCorner = new Point(0,0);
		topRightCorner = new Point(0,0);
		middleLeftCorner = new Point(0,0);
		middleRightCorner = new Point(0,0);
		bottomLeftCorner = new Point(0,0);
		bottomRightCorner = new Point(0,0);
		
		lowerX = new Point(0,0);
		lowerY = new Point(0,0);
		greaterX = new Point(0,0);
		greaterY = new Point(0,0);
	}
	
	// Obtendo o nosso menor X
	public Point getLowerX() {

		if (bottomLeftCorner.getPositionX() <= middleLeftCorner.getPositionX()
				&& bottomLeftCorner.getPositionX() <= topLeftCorner.getPositionX()) {
			lowerX = bottomLeftCorner;
		} else if (middleLeftCorner.getPositionX() <= bottomLeftCorner.getPositionX()
				&& middleLeftCorner.getPositionX() <= topLeftCorner.getPositionX()) {
			lowerX = middleLeftCorner;
		} else {
			lowerX = topLeftCorner;
		}

		return this.lowerX;
	}

	// Obtendo o maior X
	public Point getGreaterX() {

		if (bottomLeftCorner.getPositionX() >= middleLeftCorner.getPositionX()
				&& bottomLeftCorner.getPositionX() >= topLeftCorner.getPositionX()) {
			greaterX = bottomLeftCorner;
		} else if (middleLeftCorner.getPositionX() >= bottomLeftCorner.getPositionX()
				&& middleLeftCorner.getPositionX() >= topLeftCorner.getPositionX()) {
			greaterX = middleLeftCorner;
		} else {
			greaterX = topLeftCorner;
		}

		return this.greaterX;
	}

	// Obtendo o menor Y
	public Point getLowerY() {
		if (bottomLeftCorner.getPositionY() <= middleLeftCorner.getPositionY()
				&& bottomLeftCorner.getPositionY() <= topLeftCorner.getPositionY()) {
			lowerY = bottomLeftCorner;
		} else if (middleLeftCorner.getPositionY() <= bottomLeftCorner.getPositionY()
				&& middleLeftCorner.getPositionY() <= topLeftCorner.getPositionY()) {
			lowerY = middleLeftCorner;
		} else {
			lowerY = topLeftCorner;
		}

		return this.lowerY;
	}
	
	// Obtendo o menor Y
		public Point getGreaterY() {
			if (bottomLeftCorner.getPositionY() >= middleLeftCorner.getPositionY()
					&& bottomLeftCorner.getPositionY() >= topLeftCorner.getPositionY()) {
				greaterY = bottomLeftCorner;
			} else if (middleLeftCorner.getPositionY() >= bottomLeftCorner.getPositionY()
					&& middleLeftCorner.getPositionY() >= topLeftCorner.getPositionY()) {
				greaterY = middleLeftCorner;
			} else {
				greaterY = topLeftCorner;
			}

			return this.greaterY;
		}
	
	

	public Point getTopLeftCorner() {
		return topLeftCorner;
	}

	public void setTopLeftCorner(int x, int y) {
		topLeftCorner.updatePosition(x, y);
	}

	public Point getBottomLeftCorner() {
		return bottomLeftCorner;
	}

	public void setBottomLeftCorner(int x, int y) {
		bottomLeftCorner.updatePosition(x, y);
	}

	public Point getTopRightCorner() {
		return topRightCorner;
	}

	public void setTopRightCorner(int x, int y) {
		topRightCorner.updatePosition(x, y);
	}

	public Point getBottomRightCorner() {
		return bottomRightCorner;
	}

	public void setBottomRightCorner(int x, int y) {
		bottomRightCorner.updatePosition(x, y);
	}

	public Point getMiddleLeftCorner() {
		return middleLeftCorner;
	}

	public void setMiddleLeftCorner(int x, int y) {
		middleLeftCorner.updatePosition(x, y);
	}

	public Point getMiddleRightCorner() {
		return middleRightCorner;
	}

	public void setMiddleRightCorner(int x, int y) {
		middleRightCorner.updatePosition(x, y);
	}

}
