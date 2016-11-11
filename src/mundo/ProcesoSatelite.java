package mundo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import util.Container;
import util.Node;

/**
 * Clase abstraccion de un proceso satelite
 * @author Titua
 *
 */
public class ProcesoSatelite {

	public final Logger log = Logger.getLogger("mundo.ProcesoSatelite");
	private Node node;
	// El servidor almacena un conjunto de idnodes para procesosatelite
	private Collection<Node> nodes;
	private Container container;
	private MundoJuego world;
	
	/**
	 * Constructor ProcesoSatelite servidor
	 */
	public ProcesoSatelite() {
		this.nodes = new ArrayList<Node>();
		this.node = new Node(Boolean.TRUE);
	}
	
	/**
	 * Constructor ProcesoSatelite cliente
	 * @param node
	 */
	public ProcesoSatelite(Node node) {
		this.node = node;
	}
	
	public void init() {
		this.container = new Container(this.node);	
		this.world = new MundoJuego(this.container, Boolean.TRUE);
		this.world.start();
	}
	
	/**
	 * Metodo principal Thread
	 */
	public void start() {
		this.init();
		log.info("ProcesoSatelite started!");
	}
	
	/**
	 * Agregar un nodo a catalogo de nodos
	 * @param node
	 */
	public Boolean addNode(Node node) {
		Boolean isAdded = Boolean.FALSE;
		if (!isIncluded(node)) {
			this.nodes.add(node);
			isAdded = Boolean.TRUE;
			log.info("Nuevo node agregado: " + node.getId());
		}	
		return isAdded;
	}	
	
	/**
	 * Generar un Node para asignar al cliente
	 * @return Node
	 */
	public Node generateNode() {
		Node node = new Node();
		this.addNode(node);
		return node;
	}
	
	public void addObjetoJuegoNodo(ObjetoJuegoNodoImpl o) {
		this.container.addObjetoJuegoNodo(o);
	}
	
	/**
	 * Esperar hasta que Display este creado
	 */
	public void waitDisplay() {
		this.container.waitDisplay();
	}
	
	/**
	 * Comprobar que el id del nodo ya esta 
	 * incluido en los clientes
	 * @param node
	 * @return
	 */
	public Boolean isIncluded(Node node) {
		return this.nodes.contains(node);
	}
	
	// Getters and setters
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Collection<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<Node> nodes) {
		this.nodes = nodes;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
	
}
