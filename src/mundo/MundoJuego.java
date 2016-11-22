package mundo;

import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Logger;

import org.lwjgl.opengl.Display;

import util.Container;

/**
 * Clase abstraccion del gestor del juego
 * @author Titua
 *
 */
public class MundoJuego extends Thread{

	public final Logger log = Logger.getLogger("mundo.MundoJuego");
	private Container container;
	private Long currentTime;
	private Boolean finish;
	
	/**
	 * Constructor MundoJuego
	 * @param container
	 */
	public MundoJuego(Container container, Boolean finish) {
		this.container = container;
		this.currentTime = Calendar.getInstance().getTimeInMillis();
		this.finish = finish;
	}
	
	/**
	 * Metodo principal de Thread
	 */
	public void run() {
		this.container.waitDisplay();
		while (Display.isCreated()) {
			try {
				this.updateWorld();
				sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Actualizar componentes de mundo juego
	 */
	private void updateWorld() {
		long diff = Calendar.getInstance().getTimeInMillis() - this.currentTime; 
		this.updateContainer(diff);
		this.currentTime = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Actualizar valor de vectores ObjetoJuego
	 * @param diff
	 */
	public synchronized void updateContainer(Long diff) {
//		for (ObjetoJuegoNodoImpl o : this.container.getObjects()) {
//			
//			this.container.updateObjetoJuegoNodoImpl(o, diff);
//		}
		
		// Diferenciar entre elementos de Container propio
		Iterator<ObjetoJuegoNodoImpl> it = this.container.getObjects().iterator();
		ObjetoJuegoNodoImpl o;
		while(it.hasNext()) {
			o  = it.next();
			this.container.updateObjetoJuegoNodoImpl(o, diff);
		}
	}
	
	public synchronized void finish() {
		this.finish = Boolean.TRUE;
	}
	
	public synchronized Boolean getFinish() {
		return this.finish;
	}
	
}
