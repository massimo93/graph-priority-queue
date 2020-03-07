package priorityqueue.source;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Class containing a set of various methods for unit testing on given methods
 * implemented in "PriorityQueue.java"
 */
public class PriorityQueueTests {

  class NaturalComparator<T extends Comparable<? super T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
      return o1.compareTo(o2);
    }
  }
  
  private PriorityQueue<String, Integer> pqi;

  private Integer i1, i2, i3, i4, i5, i6;
  private String s1, s2, s3, s4, s5, s6;
  private PriorityQueue.QueueElement q1, q2, q3, q4, q5, q6;

  @Before
  public void setUp() {
    s1 = "aquila";
    s2 = "bradipo";
    s3 = "camaleonte";
    s4 = "delfino";
    s5 = "elefante";
    s6 = "falco";
    i1 = 1;
    i2 = 2;
    i3 = 3;
    i4 = 4;
    i5 = 5;
    i6 = 6;
    pqi = new PriorityQueue(new NaturalComparator());
    q1 = pqi.new QueueElement(s1,i1);
    q2 = pqi.new QueueElement(s2,i2);
    q3 = pqi.new QueueElement(s3,i3);
    q4 = pqi.new QueueElement(s4,i4);
    q5 = pqi.new QueueElement(s5,i5);
    q6 = pqi.new QueueElement(s6,i6);
  }
  
  @Test
  public void testInsert() {
    pqi.insert(s3,i3);
    pqi.insert(s2,i2);
    pqi.insert(s4,i4);
    pqi.insert(s5,i5);
    pqi.insert(s1,i1);
    pqi.insert(s6,i6);
    String[] expectedArray = {s6, s4, s5, s2, s1, s3};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test
  public void testInsertRec() {
    pqi.insertRec(s3,i3);
    pqi.insertRec(s2,i2);
    pqi.insertRec(s4,i4);
    pqi.insertRec(s5,i5);
    pqi.insertRec(s1,i1);
    pqi.insertRec(s6,i6);
    String[] expectedArray = {s6, s4, s5, s2, s1, s3};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test
  public void testInsertRec_protected() {
    ArrayList<String> strings = new ArrayList();
    ArrayList<Integer> priorities = new ArrayList();
    strings.add(s3);
    strings.add(s2);
    strings.add(s4);
    strings.add(s5);
    strings.add(s1);
    strings.add(s6);
    priorities.add(i3);
    priorities.add(i2);
    priorities.add(i4);
    priorities.add(i5);
    priorities.add(i1);
    priorities.add(i6);
    pqi = new PriorityQueue(strings, priorities, new NaturalComparator());
    int index = priorities.size()-1;
    pqi.insertRec(index);
    String[] expectedArray = {s6, s5, s4, s2, s1, s3};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testExtract_EmptyQueue() throws UnsupportedOperationException {
      pqi.extract();
  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void testExtractRec_EmptyQueue() throws UnsupportedOperationException {
    pqi.extractRec();
  }
  
  @Test
  public void testExtract_NotEmptyQueue() {
    pqi.insertRec(s1,i1);
    assertEquals(s1, pqi.extract());
  }
  
  @Test
  public void testExtractRec_NotEmptyQueue() {
    pqi.insertRec(s3,i3);
    pqi.insertRec(s2,i2);
    pqi.insertRec(s4,i4);
    String valExpected = s4;
    String valActual = pqi.extractRec();
    assertEquals(valExpected, valActual);
  }
  
  @Test
  public void testQueueSize_Extract() {
    pqi.insertRec(s3,i3);
    pqi.insertRec(s2,i2);
    pqi.insertRec(s4,i4);
    pqi.extract();
    int sizeExpected = 2;
    int sizeActual = pqi.getHeap().size();
    assertEquals(sizeExpected, sizeActual);
  }
  
  @Test
  public void testQueueSize_ExtractRec() {
    pqi.insertRec(s3,i3);
    pqi.insertRec(s2,i2);
    pqi.insertRec(s4,i4);
    pqi.extractRec();
    int sizeExpected = 2;
    int sizeActual = pqi.getHeap().size();
    assertEquals(sizeExpected, sizeActual);
  }
  
  @Test
  public void testExtract_NotEmptyQueue_Heap() {
    pqi.insertRec(s3,i3);
    pqi.insertRec(s2,i2);
    pqi.insertRec(s4,i4);
    pqi.extract();
    String[] expectedArray = {s3, s2};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test
  public void testExtractRec_NotEmptyQueue_Heap() {
    pqi.insertRec(s3,i3);
    pqi.insertRec(s2,i2);
    pqi.insertRec(s4,i4);
    pqi.extractRec();
    String[] expectedArray = {s3, s2};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test
  public void testHeapify() {
    pqi.getHeap().add(q3);
    pqi.getHeap().add(q2);
    pqi.getHeap().add(q4);
    pqi.getHeap().add(q5);
    pqi.getHeap().add(q1);
    pqi.getHeap().add(q6);
    for (int i = pqi.getHeap().size()/2; i >= 0; i--) {
      pqi.heapify(i);
    }
    String[] expectedArray = {s6, s5, s4, s2, s1, s3};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test
  public void testParent() {
    int valueExpected = i1;
    int index = 3;
    int valueActual = pqi.parent(index);
    assertEquals(valueExpected, valueActual);
  }
  
  @Test
  public void testLeft_returnIndex() {
    pqi.insert(s1,i1);
    pqi.insert(s4,i4);
    pqi.insert(s3,i3);
    int index = 2;
    int valueExpected = i2;
    int valueActual = pqi.left(index);
    assertEquals(valueExpected, valueActual);
  }
  
  @Test
  public void testRight_returnIndex() {
    pqi.insert(s1,i1);
    pqi.insert(s4,i4);
    pqi.insert(s3,i3);
    int index = 2;
    int valueExpected = i2;
    int valueActual = pqi.right(index);
    assertEquals(valueExpected, valueActual);
  }
  
  @Test
  public void testLeft() {
    pqi.insert(s1,i1);
    pqi.insert(s4,i4);
    pqi.insert(s3,i3);
    int index = 0;
    int valueExpected = 1;
    int valueActual = pqi.left(index);
    assertEquals(valueExpected, valueActual);
  }
  
  @Test
  public void testRight() {
    pqi.insert(s1,i1);
    pqi.insert(s4,i4);
    pqi.insert(s3,i3);
    int index = 0;
    int valueExpected = 2;
    int valueActual = pqi.right(index);
    assertEquals(valueExpected, valueActual);
  }
  
  @Test
  public void testSwap() {
    pqi.insert(s1,i1);
    pqi.insert(s4,i4);
    pqi.swap(0, 1);
    String[] expectedArray = {s1,s4};
    ArrayList<String> actualArray = new ArrayList();
    for(int i = 0; i < pqi.getHeap().size(); i++)
      actualArray.add(pqi.getHeap().get(i).getElement());
    assertArrayEquals(expectedArray, actualArray.toArray());
  }
  
  @Test
  public void testMax() {
    int valueExpected = 1;
    pqi.getHeap().add(q1);
    pqi.getHeap().add(q4);
    pqi.getHeap().add(q2);
    pqi.getHeap().add(q3);
    int valueActual = pqi.max(0,1,2,3);
    assertEquals(valueExpected, valueActual);
  }
  
  @Test
  public void testContains_EmptyQueue() {
    assertEquals(false, pqi.contains(s1));
  }
  
  @Test
  public void testContains_False_NotEmptyQueue() {
    pqi.insert(s1, i1);
    assertEquals(false, pqi.contains(s2));
  }
  
  @Test
  public void testContains_True_NotEmptyQueue() {
    pqi.insert(s1, i1);
    assertEquals(true, pqi.contains(s1));
  }
  
}
