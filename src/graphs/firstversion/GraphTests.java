package graphs.firstversion;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Class containing a set of various methods for unit testing on given method 
 * implemented in "Graph.java"
 */
public class GraphTests {
  
  class MinHeapComparator<T extends Comparable<? super T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return -o1.compareTo(o2);
    }
  }
  
  private String city1, city2, city3, city4, city5, city6, city7;
  private String[] cities;
  private Graph orientedGraph;
  private Graph notOrientedGraph, notOrientedConnectedGraph, orientedConnectedGraph, emptyNotOrientedGraph;
          
  @Before
  public void setUp() {
    city1 = "Londra";
    city2 = "New York";
    city3 = "Dubai";
    city4 = "Parigi";
    city5 = "Milano";
    city6 = "Manchester";
    city7 = "Roma";
    cities = new String[]{city1, city2, city3, city4, city5, city6, city7};
    notOrientedGraph = new Graph(false);
    notOrientedConnectedGraph = new Graph(false);
    orientedConnectedGraph = new Graph(true);
    
    for(String city : cities) {
      notOrientedGraph.addVertex(city);
    }
    
    notOrientedConnectedGraph.addEdgeForced("Londra", "New York", 5);
    notOrientedConnectedGraph.addEdgeForced("Dubai", "Londra", 12);
    notOrientedConnectedGraph.addEdgeForced("Parigi", "New York", 3);
    notOrientedConnectedGraph.addEdgeForced("Roma", "Londra", 6);
    notOrientedConnectedGraph.addEdgeForced("Roma", "Dubai", 2);
    notOrientedConnectedGraph.addEdgeForced("Milano", "New York", 7);
    notOrientedConnectedGraph.addEdgeForced("Manchester", "Parigi", 1);
    
    orientedConnectedGraph.addEdgeForced("Londra", "New York", 5);
    orientedConnectedGraph.addEdgeForced("Dubai", "Londra", 12);
    orientedConnectedGraph.addEdgeForced("Parigi", "New York", 3);
    orientedConnectedGraph.addEdgeForced("Roma", "Londra", 6);
    orientedConnectedGraph.addEdgeForced("Roma", "Dubai", 2);
    orientedConnectedGraph.addEdgeForced("Milano", "New York", 7);
    orientedConnectedGraph.addEdgeForced("Manchester", "Parigi", 1);
    
    emptyNotOrientedGraph = new Graph(false);
    orientedGraph = new Graph(true);
  }
  
  @Test
  public void testAddVertex_Fail() {
    String addedCity = "Roma";
    notOrientedGraph.addVertex(addedCity);
    int expectedSize = 7;
    int actualSize = notOrientedGraph.getAllVertex().size();
    assertEquals(expectedSize, actualSize);
  }
  
  @Test
  public void testAddVertex_Success() {
    notOrientedGraph.addVertex("Berlino");
    int expectedSize = 8;
    int actualSize = notOrientedGraph.getAllVertex().size();
    assertEquals(expectedSize, actualSize);
  }
  
  @Test (expected = NoSuchElementException.class)
  public void testAddEdge_Fail() throws NoSuchElementException {
    notOrientedGraph.addEdge("Berlino", "Parigi", 10);
  }
  
  @Test
  public void testAddEdge_Success() {
    notOrientedGraph.addEdge("Manchester", "Milano", 5);
  }
  
  @Test
  public void testAddEdgeForced() {
    notOrientedGraph.addEdgeForced("Berlino", "Madrid", 10);
  }
  
  @Test (expected = NoSuchElementException.class)
  public void testRemoveVertex_Fail() {
    notOrientedGraph.removeVertex("Berlino");
  }
  
  @Test
  public void testRemoveVertex_Success() {
    notOrientedGraph.removeVertex("Manchester");
  }
  
  @Test (expected = NoSuchElementException.class)
  public void testRemoveEdge_Fail_FirstVertex() throws NoSuchElementException {
    notOrientedGraph.removeEdge("Berlino", "Parigi");
  }
  
  @Test (expected = NoSuchElementException.class)
  public void testRemoveEdge_Fail_SecondVertex() throws NoSuchElementException {
    notOrientedGraph.removeEdge("Parigi", "Berlino");
  }
  
  @Test
  public void testRemoveEdge_Success() {
    notOrientedGraph.addEdgeForced("Londra", "New York" , 10);
    notOrientedGraph.removeEdge("Londra", "New York");
  }
  
    @Test
  public void testVertexCount_EmptyGraph() {
    
    int expectedNumVertex = 0;
    int actualNumVertex = emptyNotOrientedGraph.vertexCount();
    assertEquals(expectedNumVertex, actualNumVertex);
  }
  
  @Test
  public void testVertexCount_NotEmptyGraph() {
    int expectedNumVertex = 7;
    int actualNumVertex = notOrientedGraph.vertexCount();
    assertEquals(expectedNumVertex, actualNumVertex);
  }
  
  @Test
  public void testEdgeCount() {
    notOrientedGraph.addEdgeForced("Londra", "New York" , 20);
    notOrientedGraph.addEdgeForced("Roma", "Dubai" , 10);
    notOrientedGraph.addEdgeForced("Roma", "Milano" , 5);
    int expectedNumEdges = 3;
    int actualNumEdges = notOrientedGraph.edgeCount();
    assertEquals(expectedNumEdges, actualNumEdges);
  }

  
  @Test
  public void testGraphWeight() {
    double expectedGraphWeight = 36;
    double actualGraphWeight = notOrientedConnectedGraph.weight();
    assertEquals(expectedGraphWeight, actualGraphWeight, 0);
  }
  
  @Test
  public void testIsEmpty_EmptyGraph() {
    assertEquals(true, emptyNotOrientedGraph.isEmpty());
  }
  
    @Test
  public void testIsEmpty_NotEmptyGraph() {
    assertEquals(false, notOrientedGraph.isEmpty());
  }
  
  @Test
  public void testGraphIsNotOriented() {
    assertEquals(false, notOrientedGraph.isOriented());
  }
  
  @Test
  public void testGraphIsOriented() {
    assertEquals(true, orientedGraph.isOriented());
  }
  
  @Test
  public void testContainsVertex_False() {
    String city = "Berlino";
    assertEquals(false, notOrientedGraph.containsVertex(city));
  }
  
  @Test
  public void testContainsVertex_True() {
    String city = "Manchester";
    assertEquals(true, notOrientedGraph.containsVertex(city));
  }
  
  @Test
  public void testContainsEdge_False_NotOriented() {
    String firstCity = "Roma";
    String secondCity = "Manchester";
    assertEquals(false, notOrientedConnectedGraph.containsEdge(firstCity, secondCity));
  }
  
  @Test
  public void testContainsEdge_True_NotOriented() {
    String firstCity = "Dubai";
    String secondCity = "Roma";
    assertEquals(true, notOrientedConnectedGraph.containsEdge(firstCity, secondCity));
  }
  
  @Test
  public void testContainsEdge_False_Oriented() {
    String firstCity = "Dubai";
    String secondCity = "Roma";
    assertEquals(false, orientedConnectedGraph.containsEdge(firstCity, secondCity));
  }
  
  @Test
  public void testContainsEdge_True_Oriented() {
    String firstCity = "Roma";
    String secondCity = "Dubai";
    assertEquals(true, orientedConnectedGraph.containsEdge(firstCity, secondCity));
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testPrim__Fail_NullGraph() {
    Graph nullGraph = null;
    Prim.mstPrim(nullGraph, "Roma", new MinHeapComparator());
  }
  
  @Test (expected = UnsupportedOperationException.class)
  public void testPrim_Fail_OrientedGraph() {
    Prim.mstPrim(orientedGraph, "Roma", new MinHeapComparator());
  }
  
  @Test (expected = NoSuchElementException.class)
  public void testPrim_Fail_StartingVertexNotFound() {
    Prim.mstPrim(notOrientedConnectedGraph, "Redmond", new MinHeapComparator());
  }
  
  @Test (expected = UnsupportedOperationException.class)
  public void testPrim_Fail_NegativeWeights() {
    Graph negativeWeightsGraph = new Graph(false);
    negativeWeightsGraph.addEdgeForced("Roma", "Berlino", 4);
    negativeWeightsGraph.addEdgeForced("Berlino", "Parigi", -2);
    Prim.mstPrim(negativeWeightsGraph, "Roma", new MinHeapComparator());
  }
  
  @Test
  public void testPrim_Success() {
    Graph mstGraph = Prim.mstPrim(notOrientedConnectedGraph, "Roma", new MinHeapComparator());
    
    LinkedList actualVertexList = mstGraph.getAllVertex();
    LinkedList expectedVertexList = new LinkedList(Arrays.asList(
            "Londra","New York", "Dubai", "Parigi", "Roma", "Milano", "Manchester"));
    
    HashMap<String, LinkedList<String>> expectedAdjs = new HashMap();
    LinkedList londraAdjs = new LinkedList(Arrays.asList("New York", "Roma"));
    LinkedList dubaiAdjs = new LinkedList(Arrays.asList("Roma"));
    LinkedList romaAdjs = new LinkedList(Arrays.asList("Londra", "Dubai"));
    LinkedList newYorkAdjs = new LinkedList(Arrays.asList("Milano", "Londra", "Parigi"));
    LinkedList milanoAdjs = new LinkedList(Arrays.asList("New York"));
    LinkedList parigiAdjs = new LinkedList(Arrays.asList("New York", "Manchester"));
    LinkedList manchesterAdjs = new LinkedList(Arrays.asList("Parigi"));
    expectedAdjs.put("Londra", londraAdjs);
    expectedAdjs.put("Dubai", dubaiAdjs);
    expectedAdjs.put("Roma", romaAdjs);
    expectedAdjs.put("New York", newYorkAdjs);
    expectedAdjs.put("Milano", milanoAdjs);
    expectedAdjs.put("Parigi", parigiAdjs);
    expectedAdjs.put("Manchester", manchesterAdjs);
    
    boolean resultVertex = new HashSet<>(actualVertexList).equals(new HashSet<>(expectedVertexList));
    boolean resultEdge = true;
    for(int i = 0; i < actualVertexList.size() && resultEdge; i++) {
      LinkedList actualVertexAdjs = mstGraph.getVertexAdjs(actualVertexList.get(i));
      LinkedList expectedVertexAdjs = expectedAdjs.get(actualVertexList.get(i));      
      resultEdge = resultEdge && new HashSet<>(actualVertexAdjs).equals(new HashSet<>(expectedVertexAdjs));
    }
    
    assertEquals(true, resultVertex && resultEdge);
  }
  
}
