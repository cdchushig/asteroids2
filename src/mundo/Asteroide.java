package mundo;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.glu.Sphere;

import util.GameConstants;
import util.Vector;


/**
 * Clase abstraccion de un asteroide
 * @author Titua
 *
 */
@SuppressWarnings("serial")
public class Asteroide extends ObjetoJuegoImpl {
	
	/**
	 * Constructor Asteroide
	 * @param position
	 * @param speed
	 * @param orientation
	 */
	public Asteroide(Vector position, Vector speed, Vector orientation) {
		super(position, speed, orientation);
	}
	
	@Override
	public void update(Long diff) {
		super.update(diff);
		this.checkLimitsObject();	
	}
	
	/**
	 * Comprobar y cambiar posicion de objeto en el mundo
	 */
	private void checkLimitsObject() {		
		this.checkDeepMax();
		this.checkDeepMin();
		this.checkLimitDown();
		this.checkLimitTop();
		this.checkLimitLeft();
		this.checkLimitRight();
	}
	
	private void checkLimitRight() {
		// Limite lateral derecho
		if (getPosition().getX() + GameConstants.ASTEROID_SIZE >= GameConstants.WORLD_LIMIT_RIGHT) {
			getSpeed().setX(- getSpeed().getX());
			getPosition().setX(GameConstants.WORLD_WIDTH - GameConstants.ASTEROID_SIZE);
		}		
	}
	
	private void checkLimitTop() {
		// Limite superior
		if (getPosition().getY() + GameConstants.ASTEROID_SIZE >= GameConstants.WORLD_LIMIT_UP) {
			getSpeed().setY(- getSpeed().getY());
			getPosition().setY(GameConstants.WORLD_HEIGHT - GameConstants.ASTEROID_SIZE);
		}
	}
	
	private void checkLimitLeft() {
		// Limite lateral izquierdo
		if (getPosition().getX() - GameConstants.ASTEROID_SIZE <= GameConstants.WORLD_LIMIT_LEFT) {
			getSpeed().setX(- getSpeed().getX());
			getPosition().setX(GameConstants.ASTEROID_SIZE);
		}
	}
	
	private void checkLimitDown() {
		// Limite inferior
		if (getPosition().getY() - GameConstants.ASTEROID_SIZE <= GameConstants.WORLD_LIMIT_DOWN) {
			getSpeed().setY(- getSpeed().getY());
			getPosition().setY(GameConstants.ASTEROID_SIZE);
		}
	}
	
	private void checkDeepMax() {
		// Limite profundidad max
		if (getPosition().getZ() - GameConstants.ASTEROID_SIZE <= - GameConstants.WORLD_LIMIT_DEEP_MAX) {
			getSpeed().setZ(- getSpeed().getZ());
			getPosition().setZ(- GameConstants.WORLD_LIMIT_DEEP_MAX + GameConstants.ASTEROID_SIZE);
		}
	}
	
	private void checkDeepMin() {
		// Limite profundidad min
		if (getPosition().getZ() + GameConstants.ASTEROID_SIZE >= GameConstants.WORLD_LIMIT_DEEP_MIN) {
			getSpeed().setZ(- getSpeed().getZ());
			getPosition().setZ(- GameConstants.ASTEROID_SIZE);
		}
	}
	
	@Override
	public void draw() {
		glPushMatrix();
		glLoadIdentity();
		glTranslatef(getPosition().getX()-(float)GameConstants.WORLD_WIDTH/2f, 
				 	 getPosition().getY()-(float)GameConstants.WORLD_HEIGHT/2f, 
				 	 getPosition().getZ());
		
		Sphere s = new Sphere();
	    s.draw(GameConstants.ASTEROID_SIZE, 20, 16);
		glPopMatrix();
	}
}
