package graphs.firstversion;

import priorityqueue.source.PriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
  
/**
 * Class implementing MST Prim algorithm
 */
public class Prim {

  /**
   * Method implementing the MST-Prim algorithm
   * 
   * @param <V> type of elements used as identifier for graph vertexes
   * @param g the used graph
   * @param startVertex the label of the starting vertex
   * @param comparator a comparator that implements the priority precedence relation between vertexes
   * @return a graph representing the produced minimum spanning tree (MST)
   * @throws IllegalArgumentException when the graph, passed as param, is null
   * @throws UnsupportedOperationException if the passed graph is oriented or it contains negative weights
   * @throws NoSuchElementException if the starting vertex is not contained in the graph
   */
  public static <V> Graph mstPrim(Graph<V> g, V startVertex, Comparator comparator) throws IllegalArgumentException,
          UnsupportedOperationException, NoSuchElementException {
    
    if(g == null)
      throw new IllegalArgumentException("Graph must be not null");
    if(g.isOriented())
      throw new UnsupportedOperationException("Prim only works on not oriented graphs");
    if (!g.containsVertex(startVertex))
      throw new NoSuchElementException("Starting vertex not found in graph");

    LinkedList<V> allVertex = g.getAllVertex();
    Graph<V> result = new Graph(false);
    
    // inializing the priority queue
    Double[] prioritiesArr = new Double[allVertex.size()];
    Arrays.fill(prioritiesArr, Double.MAX_VALUE);
    ArrayList<Double> priorities = new ArrayList(Arrays.asList(prioritiesArr));
    PriorityQueue<V, Double> queue = new PriorityQueue(new ArrayList(allVertex), priorities, comparator);
    
    // initializing prim vertexes
    HashMap<V, Double> weights = new HashMap();
    for (V v : allVertex) {
      weights.put(v, Double.MAX_VALUE);
    }
    
    HashMap<V, V> parents = new HashMap();
    
    // setting starting vertex weight to 0
    weights.replace(startVertex, 0.0);
    queue.updatePriority(startVertex, 0.0);

    while (!queue.isEmpty()) {

      V actual = queue.extractRec();
      
      if (parents.get(actual) == null) {
        result.addVertex(actual);
        weights.replace(actual, 0.0);
      } else {
        result.addEdgeForced(actual, parents.get(actual), weights.get(actual));
      }

      LinkedList<V> adjs = g.getVertexAdjs(actual);

      for (V adiacent : adjs) {
        if (queue.contains(adiacent)) {
          Double edgeWeight = g.getEdgeWeight(actual, adiacent);
          if(edgeWeight < 0)
            throw new UnsupportedOperationException("Prim only works with non negative weights");
          if (weights.get(adiacent) > edgeWeight) {
            weights.put(adiacent, edgeWeight);
            queue.updatePriority(adiacent, edgeWeight);
            parents.put(adiacent, actual);
          }
        }
      }

    }

    return result;

  }

}
