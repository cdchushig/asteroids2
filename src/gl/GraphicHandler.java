package gl;

import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.nio.FloatBuffer;
import java.util.logging.Logger;

import mundo.ObjetoJuegoImpl;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import util.Container;
import util.GameConstants;


/**
 * Gestor gl para pintar mundo juego
 * @author Titua
 *
 */
public class GraphicHandler extends Thread{

	
	public final Logger log = Logger.getLogger("gl.GraphicHandler");
	private Container container;
	private Boolean isActive;
	
	public GraphicHandler(Container container) {
		this.container = container;
		this.isActive = Boolean.TRUE;
	}
	
	
	public void run() {
		this.createDisplay();
		this.setCamera();
		this.setLight();
		
		while(!Display.isCloseRequested()) {
			this.drawObjects();
		}
		// Si cerrar destroy el display
		Display.destroy();
		log.info("GraphicHandler destroyed");
		this.isActive = Boolean.FALSE;
	}	
	
	public synchronized Boolean isActive() {
		return this.isActive;
	}
	
	private void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 
												   600));
			Display.create();
			Display.setTitle(GameConstants.WORLD_TITTLE);
			this.container.notifyDisplay();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}	
	}
	
	private void setCamera() {
		// Background color glClearColor(red, green, blue, alpha);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// Clear depth buffer
		glClearDepth(1.0f);
		// Enable depth testing
		glEnable(GL_DEPTH_TEST);
		// Set the type of test
		glDepthFunc(GL_LEQUAL);
		// Projection Matrix
		glMatrixMode(GL_PROJECTION);
		
		float distance = (GameConstants.WORLD_HEIGHT + 100)/(float)Math.tan(Math.toRadians(GameConstants.WORLD_CAMERA_ANGLE/2))/2.0f;		
		
		GLU.gluPerspective((float)GameConstants.WORLD_CAMERA_ANGLE, 
						  (float)GameConstants.WORLD_WIDTH/(float)GameConstants.WORLD_HEIGHT, 	
						  0.01f, 												
						  distance+((float)GameConstants.WORLD_DEEP)*1.1f);	
		GLU.gluLookAt(0.0f, 
					 0.0f, 
					 distance, 
					 0.0f,				// centerx
					 0.0f, 			// centery
					 0.0f, 			// centerz
					 0.0f, 			// upx
					 1.0f, 			// upy
					 0.0f);			// upz
		
		glMatrixMode(GL_MODELVIEW);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
	}	
	
	private void setLight() {

		glShadeModel(GL_SMOOTH);
		
		glMaterial(GL_FRONT, GL_SPECULAR, this.buildMaterialSpecular());
		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);
		
		glLight(GL_LIGHT0, GL_POSITION, this.buildLightPosition());
		glLight(GL_LIGHT0, GL_SPECULAR, this.buildLightSpecular());
		glLight(GL_LIGHT0, GL_DIFFUSE, this.buildLightDiffuse());
		glLightModel(GL_LIGHT_MODEL_AMBIENT, this.buildLightModelAmbient());
		
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		
	}
	
	private void drawLimitLeft() {
		
	}
	
	public void drawLimit() {
		float width = ((float)800)/2;
		float height = ((float)600)/2;
		float deep =  -((float)400);
		
		glPushMatrix();
		glLoadIdentity();
		
		glColor3f(0.3f, 0.3f, 0.3f);
		glBegin(GL_POLYGON);
		glVertex3f(-width, -height, 0);
		glVertex3f(+width, -height, 0);
		glVertex3f(+width, -height, deep);
		glVertex3f(-width, -height, deep);
		glEnd();
		
		glColor3f(0.4f, 0.4f, 0.4f);
		glBegin(GL_POLYGON);
		glVertex3f(+width, -height, 0);
		glVertex3f(+width, +height, 0);
		glVertex3f(+width, +height, deep);
		glVertex3f(+width, -height, deep);
		glEnd();
	
		glColor3f(0.4f, 0.4f, 0.4f);
		glBegin(GL_POLYGON);
		glVertex3f(-width, -height, 0);
		glVertex3f(-width, +height, 0);
		glVertex3f(-width, +height, deep);
		glVertex3f(-width, -height, deep);
		glEnd();
		
		glColor3f(0.3f, 0.3f, 0.3f);
		glBegin(GL_POLYGON);
		glVertex3f(-width, +height, 0);
		glVertex3f(+width, +height, 0);
		glVertex3f(+width, +height, deep);
		glVertex3f(-width, +height, deep);
		glEnd();
		
		glColor3f(0.2f, 0.2f, 0.2f);
		glBegin(GL_POLYGON);
		glVertex3f(-width, -height, deep);
		glVertex3f(+width, -height, deep);
		glVertex3f(+width, +height, deep);
		glVertex3f(-width, +height, deep);
		glEnd();
		
		glPopMatrix();
		
	}
	
	
	private void drawObjects() {
		Display.sync(GameConstants.GL_FPS);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		this.drawLimit();
		// Dibujar asteroides de container
		for (int i = 0; i < this.container.getSize(); i++) {
			this.container.draw(i, this.container.getObjectoJuegoNodo(i).getNode().getId());
		}
		Display.update();	
	}
	
	public synchronized Container getContainer() {
		return this.container;
	}
	
	private void draw(ObjetoJuegoImpl o, Integer idNode) {
		float phase = 6.5f * ((float)Math.PI)/8 * (float) idNode;
		float red = ((float) Math.sin(phase)+1)/2f;
		float green = ((float) Math.cos(phase)+1)/2f;
		float blue = ((float) Math.sin(-phase)+1)/2f;
		glColor3f(red, green, blue);
		glPushMatrix();
		glLoadIdentity();
		glTranslatef(o.getPosition().getX() - (float) GameConstants.WORLD_WIDTH/2f,
				 o.getPosition().getY() - (float) GameConstants.WORLD_HEIGHT/2f,
				 o.getPosition().getZ());
	
		Sphere sphere = new Sphere();
		sphere.draw(GameConstants.ASTEROID_SIZE, 20, 16);
		glPopMatrix();
	}
	
	private FloatBuffer buildMaterialSpecular() {
		float[] params = {1.0f, 1.0f, 1.0f, 1.0f};
		return this.buildFloatBuffer(params);
	}
	
	private FloatBuffer buildLightSpecular() {
		float[] params = {1.0f, 1.0f, 1.0f, 1.0f}; 
		return this.buildFloatBuffer(params);
	}
	
	private FloatBuffer buildLightPosition() {
		float[] params = {(float) GameConstants.WORLD_WIDTH, 
						  (float) GameConstants.WORLD_HEIGHT, 
						  (float) GameConstants.WORLD_WIDTH, 
						  0.0f}; 
		return this.buildFloatBuffer(params);
	}
	
	private FloatBuffer buildLightModelAmbient() {
		float[] params = {0.5f, 0.5f, 0.5f, 1.0f}; 
		return this.buildFloatBuffer(params);
	}
	
	private FloatBuffer buildLightDiffuse() {
		float[] params = {1.0f, 1.0f, 1.0f, 1.0f}; 
		return this.buildFloatBuffer(params);
	}
	
	/**
	 * Metodo generico para construccion de FloatBuffer
	 * @param params Array de float
	 */
	private FloatBuffer buildFloatBuffer(float[] params) {
		FloatBuffer fb =  BufferUtils.createFloatBuffer(GameConstants.GL_BUFFER_SIZE);
		fb.put(params);
		fb.flip();
		return fb;
	}
}
