package util;

/**
 * 
 * @author Titua
 *
 */
public class GameConstants {

	public static final int WORLD_WIDTH = 800;		// ancho
	public static final int WORLD_HEIGHT = 600;		// alto
	public static final int WORLD_DEEP = 400;		// profundidad

	public static final Integer WORLD_LIMIT_LEFT = 0;
	public static final Integer WORLD_LIMIT_RIGHT = 800;
	public static final Integer WORLD_LIMIT_UP = 600;
	public static final Integer WORLD_LIMIT_DOWN = 0;
	public static final Integer WORLD_LIMIT_DEEP_MAX = 400;
	public static final Integer WORLD_LIMIT_DEEP_MIN = 0;
	public static final String WORLD_TITTLE = "3d Asteroids Game";
	public static final Integer WORLD_NUM_ASTEROIDS = 10;
	public static final Integer WORLD_FREQUENCY = 60;
	public static final float WORLD_SPEED = 1f;
	public static final float WORLD_CAMERA_ANGLE = 45;
	

	public static final Float ASTEROID_SIZE = (float) 10;
	
	public static final Integer SERVER_PORT = 1342;
	public static final Integer SERVER_MAX_CLIENTS = 10;
	public static final String SERVER_HOSTNAME = "localhost";
	
	public static final Integer SATELLITE_MAIN_NODE = 0;
	
	
	public static final Integer GENERAL_MAX_RANDOM = 5000;
	public static final Integer GENERAL_MAX_NODES_IDS = 4000;
	
	public static final Integer GL_BUFFER_SIZE = 4;
	public static final float GL_SHININESS = 50;	// RGBA light intensity
	public static final Integer GL_FPS = 60;
	
	public static final Integer PROTOCOL_INIT = 0;
	public static final Integer PROTOCOL_FINISH = -1;
	
}
