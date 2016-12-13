package com;

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
 * Handler aplicacion
 * @author Titua
 *
 */
public class Handler extends Thread {

	public final Logger log = Logger.getLogger("com.Handler");
	private Socket socket;
	private ProcesoSatelite psatellite;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	/**
	 * Constructor Handler
	 * @param socket
	 * @param server
	 */
	public Handler(Socket socket, ProcesoSatelite psatellite) {
		this.socket = socket;
		this.psatellite = psatellite;
	}

	
	private void init() throws IOException{
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
	}
	
	public void run() {
		try {
			this.init();
			Object oabs = in.readObject();
			if (oabs instanceof Integer) {
				Node node = this.psatellite.generateNode();				
				this.out.writeObject(node);
				this.out.flush();
				log.info("****Nueva conexion. Node id: " + node.getId());
			} 
			
			ObjetoJuegoNodoImpl o;
			while((o = (ObjetoJuegoNodoImpl) this.in.readObject()) != null) {
				if (o.getId().intValue() == GameConstants.PROTOCOL_FINISH.intValue()) {
					this.psatellite.getContainer().removeObjetoJuegoNodo(o.getNode().getId());
				} else {
					this.psatellite.addObjetoJuegoNodo((ObjetoJuegoNodoImpl) o);
				}
			}
		
			log.info("Handler finished");
			
			this.out.close();
			this.in.close();
			
		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException cne) {
			cne.getMessage();
		}
		
		
	}
	
}
