package util;

import static org.lwjgl.opengl.GL11.glColor3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
		this.node = node;
		this.objetoJuegoNodoCol = this.init(node);
	}

	/**
	 * Inicializar contenedor con ObjetoJuegoNodoImpl
	 * @param node
	 * @return
	 */
	private ArrayList<ObjetoJuegoNodoImpl> init(Node node) {
		ArrayList<ObjetoJuegoNodoImpl> lst = new ArrayList<ObjetoJuegoNodoImpl>();
		for (int i = this.node.getId(); i < (GameConstants.WORLD_NUM_ASTEROIDS + this.node.getId()); i++) {
			lst.add(new ObjetoJuegoNodoImpl(i, node));
		}
		return lst;
	}
	
	/**
	 * Agregar un ObjetoJuegoNodo
	 * @param o
	 */
	public void addObjetoJuegoNodo(ObjetoJuegoNodoImpl o) {			
		if(this.objetoJuegoNodoCol.contains(o)) {	
			this.objetoJuegoNodoCol.set(this.objetoJuegoNodoCol.indexOf(o), o);
		} else {
			log.info("*** Node id: " + o.getNode().getId() + " Objeto agregado id: " + o.getId());
			this.objetoJuegoNodoCol.add(o);	
		}
	}
	
	/**
	 * Eliminar objetos del cliente en el mundo del server
	 * @param idNode
	 */
	public synchronized void removeObjetoJuegoNodo(Integer idNode) {
		Iterator<ObjetoJuegoNodoImpl> it = this.objetoJuegoNodoCol.iterator();
		ObjetoJuegoNodoImpl o = null;
		while(it.hasNext()) {
			o = it.next();
			this.objetoJuegoNodoCol.remove(o);
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
	
	public void draw(Integer id, Integer idNode) {
		float phase = 6.5f*((float)Math.PI)/8*(float)idNode;
		float R = ((float)Math.sin(phase)+1)/2f;
		float G = ((float)Math.cos(phase)+1)/2f;
		float B = ((float)Math.sin(-phase)+1)/2f;
		glColor3f(R, G, B);
		this.objetoJuegoNodoCol.get(id).getObjetoJuego().draw();
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