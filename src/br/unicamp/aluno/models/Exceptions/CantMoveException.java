package br.unicamp.aluno.models.Exceptions;

import br.unicamp.aluno.models.Door;
import br.unicamp.aluno.models.Traceable;

public class CantMoveException extends RuntimeException {
	private boolean isDoor;
	private static final long serialVersionUID = 1L;

	public CantMoveException() {
		super();
	}

	public CantMoveException(String string) {
		super(string);
	}

	public CantMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public CantMoveException(Throwable cause) {
		super(cause);
	}

	public CantMoveException(Traceable traceable) {
		isDoor(traceable);
	}

	private void isDoor(Traceable traceable){
		try {
			if (traceable != null) {
				Door door = (Door) traceable;
				isDoor = true;
			}
		} catch (ClassCastException e) {
			isDoor = false;
		}
	}

	@Override
	public String getMessage() {
		if (isDoor)
			return "Door must be opened first.";
		else
			return "Obstacle on the way. Choose another position!";
	}
}
