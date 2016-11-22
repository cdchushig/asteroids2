package com;

import gl.GraphicHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import mundo.ProcesoSatelite;

import org.lwjgl.opengl.Display;

import util.GameConstants;

/**
 * Server aplicacion
 * @author Titua
 *
 */
public class Server extends Thread {

	public final Logger log = Logger.getLogger("com.Server");
	private ProcesoSatelite psatellite;
	private GraphicHandler ghandler;
		
	/**
	 * Inicializar ProcesoSatelite, GraphicHandler
	 */
	public void init() {
		this.psatellite = new ProcesoSatelite();
		this.psatellite.start();
		this.ghandler = new GraphicHandler(this.psatellite.getContainer());
		this.ghandler.start();
		this.psatellite.waitDisplay();
	}
	
	public void run() {
		log.info("Server run!");
	
		try {
			ServerSocket server = new ServerSocket(GameConstants.SERVER_PORT);
			//server.setSoTimeout(1000);
			ExecutorService pool = Executors.newFixedThreadPool(GameConstants.SERVER_MAX_CLIENTS);
			
			while(Display.isCreated()) {
				Handler handler = new Handler(server.accept(), this.psatellite);
				pool.execute(handler);
			}
			
			server.close();
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo principal
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.init();
		server.start();
	}
	
	
}
