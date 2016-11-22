package util;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Node implements Serializable{

	public Integer id;
	
	public Node() {
		this.id = this.generateId();
	}
	
	public Node(Boolean isServer) {
		this.id = GameConstants.SATELLITE_MAIN_NODE;
	}
	
	/**
	 * Generar id aleatorio
	 * @return
	 */
	private Integer generateId() {
		return this.buildGenericId();
	}

	private Integer buildGenericId() {
		Random r = new Random();
		return r.nextInt(GameConstants.GENERAL_MAX_RANDOM);
	}
	
	// Getters and setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
		
}

