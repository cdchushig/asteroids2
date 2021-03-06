package util;

import static org.lwjgl.opengl.GL11.glColor3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import mundo.ObjetoJuegoNodoImpl;

import org.lwjgl.opengl.Display;

/**
 * Clase que contiene los elementos ObjetoJuegoNodo del mundo
 * @author Titua
 *
 */
public class Container {

	public final Logger log = Logger.getLogger("util.Container");
	private ArrayList<ObjetoJuegoNodoImpl> objetoJuegoNodoCol;
	private Node node;
	
	/**
	 * Constructor Container
	 * @param node
	 */
	public Container(Node node) {
		this.objetoJuegoNodoCol = this.init(node);
	}

	/**
	 * Inicializar contenedor con ObjetoJuegoNodoImpl
	 * @param node
	 * @return
	 */
	private ArrayList<ObjetoJuegoNodoImpl> init(Node node) {
		ArrayList<ObjetoJuegoNodoImpl> lst = new ArrayList<ObjetoJuegoNodoImpl>();
		for (int i = 0; i < GameConstants.WORLD_NUM_ASTEROIDS; i++) {
			lst.add(new ObjetoJuegoNodoImpl(node, i));
		}
		return lst;
	}
	
	/**
	 * Agregar un ObjetoJuegoNodo
	 * @param o
	 */
	public synchronized void addObjetoJuegoNodo(ObjetoJuegoNodoImpl o) {		
		
		
		if(this.objetoJuegoNodoCol.contains(o)) {
			
			log.info("*****IS INCLUDED*******" + o.getId() + "idnode: " + o.getNode().getId()); 

			this.objetoJuegoNodoCol.set(this.objetoJuegoNodoCol.indexOf(o), o);

		} else {
			log.info("***add ***");

			this.objetoJuegoNodoCol.add(o);	

		}
		
	}
	
	
	/**
	 * Devolver un ObjetoJuegoNodo
	 * @param i
	 * @return
	 */
	public synchronized ObjetoJuegoNodoImpl getObjectoJuegoNodo(Integer i) {
		return this.objetoJuegoNodoCol.get(i);
	}
	
	/**
	 * Devolver todos los objects del mundo juego
	 * @return
	 */
	public synchronized Collection<ObjetoJuegoNodoImpl> getObjects() {
		return this.objetoJuegoNodoCol;
	}

	/**
	 * Actualizar el ObjetoJuegoNodeImpl
	 * @param o
	 * @param diff
	 */
	public synchronized void updateObjetoJuegoNodoImpl(ObjetoJuegoNodoImpl o, Long diff) {
		o.update(diff);
	}
	
	public void draw3d(int numObj) {
		float fase = 6.5f*((float)Math.PI)/8*(float)50;

		float R = ((float)Math.sin(fase)+1)/2f;
		float G = ((float)Math.cos(fase)+1)/2f;
		float B = ((float)Math.sin(-fase)+1)/2f;
		glColor3f(R, G, B);
		this.objetoJuegoNodoCol.get(numObj).getObjetoJuego().draw3d();
		
	}
	
	/**
	 * Retornar tamanio del contenedor
	 * @return
	 */
	public synchronized Integer getSize() {
		return objetoJuegoNodoCol.size();
	}
	
	// Control de construccion de Display
	
	/**
	 * Esperar hasta que el display este creado
	 */
	public synchronized void waitDisplay() {
		while (!Display.isCreated()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * Liberar thread cuando display este creado
	 */
	public synchronized void notifyDisplay() {
		this.notifyAll();
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
	

}