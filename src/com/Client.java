package com;

import gl.GraphicHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import mundo.MundoJuego;
import mundo.ObjetoJuegoNodoImpl;
import mundo.ProcesoSatelite;
import util.Container;
import util.GameConstants;
import util.Node;

/**
 * Cliente aplicacion
 * @author Titua
 *
 */
public class Client extends Thread{

	public final Logger log = Logger.getLogger("com.Client");
	private ProcesoSatelite psatellite;
	private GraphicHandler ghandler;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Integer port;
	private String server;
	private Node node;
	
	private Container container;
	private MundoJuego world;
	
	/**
	 * Constructor Client
	 */
	public Client(String server, Integer port) {
		this.server = server;
		this.port = port;
	}
	
	public void run() {
		this.initConnection();
		this.init();
		this.establishConnection();	
	}
	
	private void init() {
//		this.psatellite = new ProcesoSatelite(this.node);
//		this.psatellite.start();
		//this.ghandler = new GraphicHandler(this.psatellite.getContainer());
		//this.ghandler.start();
		//this.psatellite.waitDisplay();
		this.container = new Container(this.node);	
		this.world = new MundoJuego(this.container, Boolean.TRUE);
		this.world.start();
		
	}
	
	/**
	 * Inicializar protocolo de comunicacion
	 */
	private void initConnection() {
		try {
			this.socket = new Socket(this.server, this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
			log.info("Connected with server in port: " + this.port);
			
			this.out.writeObject(GameConstants.PROTOCOL_INIT);
			this.out.flush();
			
			this.node = (Node) this.in.readObject();
			
		} catch (IOException e) {
			log.warning("Error init: Creating streams");
			e.printStackTrace();
		} catch (ClassNotFoundException cne) {
			cne.printStackTrace();
		}	
	}
	
	/**
	 * Establecer y enviar datos con server
	 */
	private void establishConnection() {
		try {		
			while(this.isConnectedStream()) {
				//for (int i = 0; i < this.psatellite.getContainer().getSize(); i++) {
				for (int i = 0; i < this.container.getSize(); i++) {

					this.out.writeObject(this.container.getObjectoJuegoNodo(i));
					this.out.flush();
					this.out.reset();
//					log.info("From client idnode: " + this.node.getId() + " object id: " + i);
					sleep(10000);
				}
			}
			
			this.out.close();
			this.in.close();
			
		} catch (IOException io) {
			io.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	
	}
	
	
	private Boolean isConnectedStream() {
		Boolean isConnected = Boolean.TRUE;
		if ((out == null) || (this.in == null)) {
			isConnected = Boolean.FALSE;
		}
		return isConnected;
	}
	
	public static void main(String[] args) {
		Client client = new Client(GameConstants.SERVER_HOSTNAME, GameConstants.SERVER_PORT);
		client.start();
	}
	
}
