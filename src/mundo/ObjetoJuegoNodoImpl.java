package mundo;

import java.io.Serializable;
import java.util.Random;

import util.GameConstants;
import util.Node;
import util.Vector;

@SuppressWarnings("serial")
public class ObjetoJuegoNodoImpl implements Serializable{

	private ObjetoJuegoImpl objetoJuego;
	public Node node;
	public Integer id;
		
	/**
	 * Constructor ObjetoJuegoNodoImpl
	 * @param node
	 */
	public ObjetoJuegoNodoImpl(Integer id, Node node) {
		this.id = id;
		this.node = node;
		this.objetoJuego = this.buildObjetoJuego();
	}
	
	public void update(Long diff) {
		this.objetoJuego.update(diff);
	}
	
	// Metodos privados
	
	/**
	 * Construir ObjetoJuegoImp con vectores
	 * position, orientation, speed random
	 * @return ObjetoJuegoImpl
	 */
	private ObjetoJuegoImpl buildObjetoJuego() {
		
		Random r = new Random();
		
		Vector position = new Vector(r.nextFloat() * GameConstants.WORLD_WIDTH,
				   					 r.nextFloat() * GameConstants.WORLD_HEIGHT, 
				   					 0.0f);
		
		Vector orientation = new Vector(r.nextFloat(), 
										r.nextFloat(), 
										0.0f);
		
		Vector speed = new Vector((r.nextFloat() - 0.5f) / GameConstants.WORLD_SPEED, 
								  (r.nextFloat() - 0.5f) / GameConstants.WORLD_SPEED,
								  (r.nextFloat() - 0.5f) / GameConstants.WORLD_SPEED);
		
		return new Asteroide(position, speed, orientation);
	}

	
	 @Override
	 public boolean equals(Object obj) {
		 if (this == obj)
			 return true;
		 if (obj == null)
			 return false;
		 if (getClass() != obj.getClass())
			 return false;
		 ObjetoJuegoNodoImpl other = (ObjetoJuegoNodoImpl) obj;
		 if (id.intValue() != other.getId().intValue()) 
			 return false;
		 return true;
	 }
	 
	// Getters and setters

	public ObjetoJuegoImpl getObjetoJuego() {
		return objetoJuego;
	}

	public void setObjetoJuego(ObjetoJuegoImpl objetoJuego) {
		this.objetoJuego = objetoJuego;
	}
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
