package com;

import gl.GraphicHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import org.lwjgl.opengl.Display;

import mundo.ObjetoJuegoNodoImpl;
import mundo.ProcesoSatelite;
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
	
	/**
	 * Constructor Client
	 */
	public Client(String server, Integer port) {
		this.server = server;
		this.port = port;
	}
	
	public void run() {
		this.initConnection();
		this.initComponents();
		this.establishConnection();	
	}
	
	/**
	 * Iniciar ProcesoSatelite, GraphicHandler
	 */
	private void initComponents() {
		this.psatellite = new ProcesoSatelite(this.node);
		this.psatellite.start();
		this.ghandler = new GraphicHandler(this.psatellite.getContainer());
		this.ghandler.start();
		this.psatellite.waitDisplay();
	}
	
	/**
	 * Inicializar protocolo de comunicacion
	 */
	private void initConnection() {
		try {
			this.socket = new Socket(this.server, this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
			
			this.out.writeObject(GameConstants.PROTOCOL_INIT);
			this.out.flush();
			
			this.node = (Node) this.in.readObject();
			
			log.info("Connected with server in port: " + this.port + "Node id: " + this.node);
		
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
			while(this.isConnectedStream() && (Display.isCreated())) {				
				for (ObjetoJuegoNodoImpl o : this.psatellite.getContainer().getObjects()) {
					this.out.writeObject(o);
					this.out.flush();
					this.out.reset();
					sleep(1000);
				}
			}
			
			// Finalizar protocolo com
			this.out.writeObject(this.psatellite.finishProtocol(this.node.getId()));
			this.out.flush();
			
			// Cerrar streams
			this.out.close();
			this.in.close();
			
		} catch (IOException io) {
			io.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	
	}
	
	/**
	 * Comprobar que el Display este activo
	 * @return
	 */
	private Boolean isTx() {
		return this.psatellite.isWorkingProcess();
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
