package mundo;

import java.io.Serializable;

import util.Vector;

/**
 * Clase abstracta para un objeto juego
 * @author Titua
 *	
 */
@SuppressWarnings("serial")
public abstract class ObjetoJuegoImpl implements ObjetoJuego, Serializable {

	private Vector position;
	private Vector speed;
	private Vector orientation;
	
	/**
	 * Constructor clase ObjectoJuegoImpl
	 * @param position
	 * @param speed
	 * @param orientation
	 */
	public ObjetoJuegoImpl(Vector position, Vector speed, Vector orientation) {
		this.position = position;
		this.speed = speed;
		this.orientation = orientation;
		this.orientation.normalize();
	}
	
	/**
	 * Actualizar posicion de vector
	 */
	public void update(Long diff) {
		this.setPosition(Vector.addition(this.position, Vector.multiplication(this.speed, (float) diff)));
	}
	
	// Getters and setters

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getSpeed() {
		return speed;
	}


	public void setSpeed(Vector speed) {
		this.speed = speed;
	}


	public Vector getOrientation() {
		return orientation;
	}


	public void setOrientation(Vector orientation) {
		this.orientation = orientation;
	}

}
