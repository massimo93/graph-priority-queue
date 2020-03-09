package graphs.secondversion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class implementing a graph data structure
 *
 * @param <V> type of elements used as identifier for graph vertexes
 */
public class Graph<V> {

	private final HashMap<V, LinkedList<V>> adjs;
	private final WeightTable weights;
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
		this.weights = new WeightTable();
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
		if (weights.get(src, dest) == null) {
			adjs.get(src).add(dest);
			weights.set(src, dest, weight);
			if (!oriented) {
				adjs.get(dest).add(src);
				weights.set(dest, src, weight);
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
		if (weights.get(src, dest) == null) {
			adjs.get(src).add(dest);
			weights.set(src, dest, weight);
			if (!oriented) {
				adjs.get(dest).add(src);
				weights.set(dest, src, weight);
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
		for (LinkedList<V> link : adjs.values()) {
			link.remove(vertexName);
		}
		for (Map.Entry<V, LinkedList<V>> entry : adjs.entrySet()) {
			weights.remove(entry.getKey(), vertexName);
			weights.remove(vertexName, entry.getKey());
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
		if (weights.get(src, dest) == null) {
			throw new NoSuchElementException("Edge to be removed doesn't exist");
		}
		adjs.get(src).remove(dest);
		weights.remove(src, dest);
		if (!oriented) {
			adjs.get(dest).remove(src);
			weights.remove(dest, src);
		}
	}

	/**
	 * Method returning all vertexes contained in the graph
	 *
	 * @return a list of all graph vertexes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LinkedList<V> getAllVertex() {
		return new LinkedList(adjs.keySet());
	}

	/**
	 * Method returning all connections of a given vertex on the graph
	 * 
	 * @param vertex the label of the vertex
	 * @return a list of all connections related to the vertex, passed as param
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LinkedList<V> getVertexAdjs(V vertex) {
		LinkedList<V> out = new LinkedList();
		for (V v : adjs.get(vertex)) {
			out.add(v);
		}
		return out;
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
		if (weights.get(src, dest) == null) {
			throw new NoSuchElementException("Edge not found");
		}
		return weights.get(src, dest);
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
		if (weights.get(src, dest) != null)
			return true;
		return false;
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
		for (LinkedList<V> link : adjs.values()) {
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
		return oriented ? weights.getWeight() : weights.getWeight() / 2;
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
	 * Method returning a stringified representation of the graph structure
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
		for (Map.Entry<V, LinkedList<V>> entry : adjs.entrySet()) {
			out += "\t" + entry.getKey().toString() + ": [ ";
			LinkedList<V> adjs = entry.getValue();
			for (V adiacent : adjs) {
				out += "to " + adiacent.toString() + " in " + weights.get(entry.getKey(), adiacent) + ", ";
			}
			if (adjs.size() > 0) {
				out = out.substring(0, out.length() - 2);
			}
			out += " ]\n";
		}
		out += "}";
		return out;
	}

	/**
	 * Inner class representing a adjacencies table of the graph
	 */
	protected class WeightTable {

		private final HashMap<V, HashMap<V, Double>> table;
		private Double weight;

		/**
		 * Simple constructor
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public WeightTable() {
			table = new HashMap();
			weight = 0.0;
		}

		/**
		 * Method setting the value of the cell with coords are given by r and c
		 * 
		 * @param r   the row label
		 * @param c   the column label
		 * @param val the double value to set
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void set(V r, V c, Double val) {
			if (!table.containsKey(r)) {
				table.put(r, new HashMap());
			}
			table.get(r).put(c, val);
			weight += val;
		}

		/**
		 * Method getting the value of the cell with coords are given by r and c
		 * 
		 * @param r the row label
		 * @param c the column label
		 * @return the double value to get
		 */
		public Double get(V r, V c) {
			if (table.containsKey(r)) {
				if (table.containsKey(c)) {
					return table.get(r).get(c);
				}
			}
			return null;
		}

		/**
		 * Method removing the value of the cell with coords are given by r and c
		 * 
		 * @param r the row label
		 * @param c the column label
		 */
		public void remove(V r, V c) {
			if (table.containsKey(r)) {
				weight -= get(r, c);
				table.get(r).remove(c);
			}
		}

		/**
		 * Auxiliary method getting the weight value
		 * 
		 * @return the double value of the weight
		 */
		public Double getWeight() {
			return weight;
		}

		/**
		 * Method getting a stringified representation of the table
		 * 
		 * @return a string representing the table
		 */
		@Override
		public String toString() {
			String out = "";
			for (Map.Entry<V, HashMap<V, Double>> outer : table.entrySet()) {
				for (Map.Entry<V, Double> inner : outer.getValue().entrySet()) {
					out += "<" + outer.getKey() + ", " + inner.getKey() + ", " + inner.getValue() + ">";
				}
				out += "\n";
			}
			return out;
		}

	}

}
