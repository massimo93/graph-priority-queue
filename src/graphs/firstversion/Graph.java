package graphs.firstversion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class implementing a graph data structure
 *
 * @param <V> type of elements used as identifier for graph vertexes
 */
public class Graph<V> {

	private final HashMap<V, LinkedList<Edge>> adjs;
	private final boolean oriented;

	/**
	 * Constructor for an empty graph
	 *
	 * @param oriented boolean value specifying the graph orientation: true if
	 *                 oriented, false if not
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Graph(boolean oriented) {
		this.adjs = new HashMap();
		this.oriented = oriented;
	}

	/**
	 * Method that adds a vertex to graph
	 *
	 * @param vertexName the label of the vertex to add
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addVertex(V vertexName) {
		adjs.putIfAbsent(vertexName, new LinkedList());
	}

	/**
	 * Method adding a connection between two vertexes already present in the graph
	 *
	 * @param src    the label of the connection starting vertex
	 * @param dest   the label of the connection ending vertex
	 * @param weight the cost of the connection
	 * @throws NoSuchElementException if one of the two vertexes doesn't exist in
	 *                                the graph
	 */
	public void addEdge(V src, V dest, double weight) {
		if (!containsVertex(src)) {
			throw new NoSuchElementException("Vertex " + src.toString() + " not found while creating edge");
		}
		if (!containsVertex(dest)) {
			throw new NoSuchElementException("Vertex " + dest.toString() + " not found while creating edge");
		}
		if (!containsEdge(src, dest)) {
			adjs.get(src).add(new Edge(dest, weight));
			if (!oriented) {
				adjs.get(dest).add(new Edge(src, weight));
			}
		}
	}

	/**
	 * Method that adds a connection between two vertexes; if they don't exist they
	 * are created
	 *
	 * @param src    the label of the connection starting vertex
	 * @param dest   the label of the connection ending vertex
	 * @param weight the cost of the connection
	 */
	public void addEdgeForced(V src, V dest, double weight) {
		addVertex(src);
		addVertex(dest);
		if (!containsEdge(src, dest)) {
			adjs.get(src).add(new Edge(dest, weight));
			if (!oriented) {
				adjs.get(dest).add(new Edge(src, weight));
			}
		}
	}

	/**
	 * Method that removes a vertex and all its references from the graph
	 *
	 * @param vertexName the label of the vertex to remove
	 * @throws NoSuchElementException if the vertex to be removed is not contained
	 *                                in the graph
	 */
	public void removeVertex(V vertexName) {
		if (!containsVertex(vertexName)) {
			throw new NoSuchElementException("Vertex " + vertexName.toString() + " not found while removing it");
		}
		adjs.remove(vertexName);
		for (LinkedList<Edge> link : adjs.values()) {
			ListIterator<Edge> i = link.listIterator();
			while (i.hasNext()) {
				Edge e = i.next();
				if (e.getDestination() == vertexName) {
					i.remove();
				}
			}
		}
	}

	/**
	 * Method that removes a connection between two vertexes
	 *
	 * @param src  the label of the connection starting vertex
	 * @param dest the label of the connection ending vertex
	 * @throws NoSuchElementException if one of the two vertexes isn't contained in
	 *                                the graph or the edge does not exist
	 */
	public void removeEdge(V src, V dest) {
		if (!containsVertex(src)) {
			throw new NoSuchElementException(
					"Edge can't be removed because vertex " + src.toString() + " doesn't exist");
		}
		if (!containsVertex(dest)) {
			throw new NoSuchElementException(
					"Edge can't be removed because vertex " + dest.toString() + " doesn't exist");
		}
		if (!containsEdge(src, dest)) {
			throw new NoSuchElementException("Edge to be removed doesn't exist");
		}

		ListIterator<Edge> i = adjs.get(src).listIterator();
		while (i.hasNext()) {
			Edge e = i.next();
			if (e.dest == dest) {
				i.remove();
			}
		}

		if (!oriented) {
			i = adjs.get(dest).listIterator();
			while (i.hasNext()) {
				Edge e = i.next();
				if (e.dest == src) {
					i.remove();
				}
			}
		}

	}

	/**
	 * Method returning all vertexes contained in the graph
	 *
	 * @return a list of all graph vertexes
	 */
	public LinkedList<V> getAllVertex() {
		return new LinkedList<V>(adjs.keySet());
	}

	/**
	 * Method returning all connections of a given vertex on the graph
	 * 
	 * @param vertex the label of the vertex
	 * @return a list of all connections related to the vertex, passed as param
	 */
	public LinkedList<V> getVertexAdjs(V vertex) {
		LinkedList<V> out = new LinkedList<V>();
		for (Edge e : adjs.get(vertex)) {
			out.add(e.dest);
		}
		return out;
	}

	/**
	 * Method telling if a certain edge, specified by its vertexes, is contained in
	 * the graph
	 * 
	 * @param src  the label of the connection starting vertex
	 * @param dest the label of the connection ending ertex
	 * @return true if the calculated edge is contained in the graph, false if not
	 * @throws NoSuchElementException when the edge does not exist because one (or
	 *                                both) specified vertex (vertexes) is (are) not
	 *                                in the graph
	 */
	public boolean containsEdge(V src, V dest) {
		if (!containsVertex(src)) {
			throw new NoSuchElementException("Edge not found because vertex " + src.toString() + " doesn't exist");
		}
		if (!containsVertex(dest)) {
			throw new NoSuchElementException("Edge not found because vertex " + dest.toString() + " doesn't exist");
		}
		for (Edge e : adjs.get(src)) {
			if (e.dest == dest) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method returning the weight of an edge, specified by its vertexes
	 * 
	 * @param src  the label of the connection starting vertex
	 * @param dest the label of the connection ending vertex
	 * @return a double value for the edge weight
	 * @throws NoSuchElementException when the edge does not exist because one (or
	 *                                both) specified vertex (vertexes) is (are) not
	 *                                in the graph
	 */
	public Double getEdgeWeight(V src, V dest) {
		if (!containsVertex(src)) {
			throw new NoSuchElementException("Edge not found because vertex " + src.toString() + " doesn't exist");
		}
		if (!containsVertex(dest)) {
			throw new NoSuchElementException("Edge not found because vertex " + dest.toString() + " doesn't exist");
		}
		LinkedList<Edge> srcEdges = adjs.get(src);
		for (Edge e : srcEdges) {
			if (dest == e.dest)
				return e.weight;
		}
		throw new NoSuchElementException("Edge doesn't exist");
	}

	/**
	 * Method returning the number of vertexes contained in the graph
	 *
	 * @return an integer value of the vertexes count
	 */
	public int vertexCount() {
		return adjs.size();
	}

	/**
	 * Method returning the number of connections contained in the graph
	 *
	 * @return an integer value counting the number of connections in the graph
	 */
	public int edgeCount() {
		int count = 0;
		for (LinkedList<Edge> link : adjs.values()) {
			count += link.size();
		}
		return oriented ? count : count / 2;
	}

	/**
	 * Method that calculates the total cost of every single connection contained in
	 * the graph
	 *
	 * @return a double value counting the cost of the whole graph
	 */
	public double weight() {
		double weight = 0;
		for (LinkedList<Edge> link : adjs.values()) {
			for (Edge e : link) {
				weight += e.weight;
			}
		}
		return oriented ? weight : weight / 2;
	}

	/**
	 * Method checking if the graph is empty or not (meaning it doesn't contain a
	 * single vertex)
	 *
	 * @return true if the graph is empty, false if not
	 */
	public boolean isEmpty() {
		return adjs.isEmpty();
	}

	/**
	 * Method telling if the graph is oriented or not
	 *
	 * @return true of it is oriented, false if it's not
	 */
	public boolean isOriented() {
		return oriented;
	}

	/**
	 * Method checking if a given vertex is contained in the graph
	 *
	 * @param vertexName the label of the vertex to find
	 * @return true if the specified vertex has been found in the graph, false if it
	 *         has not
	 */
	public boolean containsVertex(V vertexName) {
		return adjs.containsKey(vertexName);
	}

	/**
	 * Method returning a string representation of the graph structure
	 *
	 * @return a string representing the graph structure
	 */
	@Override
	public String toString() {
		String out = "";
		out += oriented ? "Oriented Graph" : "Not Oriented Graph";
		out += "\n";
		out += "Vertex count: " + this.vertexCount() + "\n";
		out += "Edge count: " + this.edgeCount() + "\n";
		out += "Total weight: " + this.weight() + "\n";
		out += "Vertex list: [ ";
		for (V v : adjs.keySet()) {
			out += v.toString() + ", ";
		}
		if (this.vertexCount() > 0) {
			out = out.substring(0, out.length() - 2);
		}
		out += " ]\n";
		out += "Adjacencies: {\n";
		for (Map.Entry<V, LinkedList<Edge>> entry : adjs.entrySet()) {
			out += "\t" + entry.getKey().toString() + ": [ ";
			LinkedList<Edge> dest = entry.getValue();
			for (Edge e : dest) {
				out += e.toString() + ", ";
			}
			if (dest.size() > 0) {
				out = out.substring(0, out.length() - 2);
			}
			out += " ]\n";
		}
		out += "}";
		return out;
	}

	/**
	 * Inner class representing a single connection/edge in the graph
	 */
	protected class Edge {

		private final V dest;
		private final double weight;

		/**
		 * Constructor for a graph edge object passing the destination vertex and the
		 * edge weight
		 * 
		 * @param destination the label of the connection ending vertex
		 * @param weight      a double value for the edge weight
		 */
		public Edge(V destination, double weight) {
			this.dest = destination;
			this.weight = weight;
		}

		/**
		 * Auxiliary method getting the edge destination vertex label
		 * 
		 * @return the edge destination vertex label
		 */
		public V getDestination() {
			return dest;
		}

		/**
		 * Auxilary method getting the edge weight
		 * 
		 * @return a double value of the edge weight
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Auxiliary method telling if an object, passed as param, is equal to the
		 * current edge object
		 * 
		 * @param o the object that needs to be compared with the current edge object
		 * @return true if the edge object and the passed object are equal, false if
		 *         they are not
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o.getClass() == getClass()))
				return false;
			Edge otherEdge = (Edge) o;
			return this.dest == otherEdge.dest && this.weight == otherEdge.weight;
		}

		/**
		 * Method returning a string representation of the current edge object
		 * specifying the destination vertex label and the required weight
		 * 
		 * @return a string representing the current edge object
		 */
		@Override
		public String toString() {
			return "<to " + dest.toString() + " in " + weight + ">";
		}

	}

}
