package util;

import java.io.Serializable;

/**
 * Clase que define abstraccion de un vector 3d
 * @author Titua
 *
 */
@SuppressWarnings("serial")
public class Vector implements Serializable{

	private float x;
	private float y;
	private float z;
	
	/**
	 * Constructor Vector
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	/**
	 * Sumar vectorialmente dos vectores
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector addition(Vector v1, Vector v2) {
		return new Vector(v1.getX() + v2.getX(), 
						  v1.getY() + v2.getY(), 
						  v1.getZ() + v2.getZ());
	}
	
	/**
	 * Multiplicar vector por un escalar
	 * @param v Vector
	 * @param k Escalar
	 * @return
	 */
	public static Vector multiplication(Vector v, float k) {
		return new Vector(v.getX() * k, v.getY() * k, v.getZ() * k);
	}
	
	/**
	 * Calcular modulo de vector
	 * @param v
	 * @return
	 */
	public float module() {
		return (float) Math.sqrt((this.getX() * this.getX())
							+ (this.getY() * this.getY())
							+ (this.getZ() * this.getZ()));
	}
	
	/**
	 * Normalizar vector
	 */
	public void normalize() {
		Float module = this.module();
		this.setX(this.getX() / module);
		this.setY(this.getY() / module);
		this.setZ(this.getZ() / module);
	}
	
	// Getters and setters
	
	public float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(Float z) {
		this.z = z;
	}
}
