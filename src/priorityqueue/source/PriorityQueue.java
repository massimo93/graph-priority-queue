package priorityqueue.source;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class implementing the priority queue data structure
 * 
 * @param <T> type of elements that can be stored in the priority queue
 * @param <P> type of priority of the stored elements
 */
public class PriorityQueue<T, P> {

	private final Comparator<P> c;
	private final ArrayList<QueueElement> heap;
	private final HashMap<T, Integer> shortcut;

	/**
	 * Constructor using a custom comparator
	 *
	 * @param comparator a comparator that implements the priority precedence
	 *                   relation between the heap structure elements
	 */
	public PriorityQueue(Comparator<P> comparator) {
		this.c = comparator;
		this.heap = new ArrayList();
		this.shortcut = new HashMap();
	}

	/**
	 * Custom constructor to build a heap from a given array and its elements
	 * priorities; it's a O(n) implementation
	 *
	 * @param array      the data structure that will fill the priority queue inner
	 *                   heap structure
	 * @param priorities the data structure that contains the respective priorities
	 *                   for the elements of the array passed as param
	 * @param comparator a comparator that implements the priority precedence
	 *                   relation between the heap structure elements
	 */
	public PriorityQueue(ArrayList<T> array, ArrayList<P> priorities, Comparator<P> comparator) {
		this.c = comparator;
		this.heap = new ArrayList();
		this.shortcut = new HashMap();
		for (int i = 0; i < array.size(); i++) {
			QueueElement qe = new QueueElement(array.get(i), priorities.get(i));
			heap.add(qe);
			shortcut.put(array.get(i), heap.size() - 1);
		}
		for (int i = heap.size() / 2; i >= 0; i--) {
			heapify(i);
		}
	}

	/**
	 * An iterative implementation of a method that inserts an element into a
	 * priority queue
	 *
	 * @param element  the element to insert into the priority queue
	 * @param priority the element priority
	 * @throws UnsupportedOperationException if the element that needs to be
	 *                                       inserted is already in queue
	 */
	public void insert(T element, P priority) {
		if (shortcut.containsKey(element)) {
			throw new UnsupportedOperationException("Element already in queue");
		}
		QueueElement qe = new QueueElement(element, priority);
		heap.add(qe);
		shortcut.put(element, heap.size() - 1);
		int i = heap.size() - 1;
		while (i > 0 && c.compare(heap.get(parent(i)).prior, heap.get(i).prior) < 0) {
			/*
			 * invariant: for each x in i..heap.size()-1 : heap.get(x).prior <=
			 * heap.get(right(x)).prior AND heap.get(x).prior <= heap.get(left(x)).prior ->
			 * minHeap
			 */
			/*
			 * invariant: for each x in i..heap.size()-1 : heap.get(x).prior >=
			 * heap.get(right(x)).prior AND heap.get(x).prior >= heap.get(left(x)).prior ->
			 * maxHeap
			 */
			swap(i, parent(i));
			i = parent(i);
		}
	}

	/**
	 * A recursive implementation of a method inserting an element into a heap
	 * structure
	 * 
	 * @param element  the element to insert into the heap
	 * @param priority the element priority
	 * @throws UnsupportedOperationException if the element that needs to be
	 *                                       inserted is already in queue
	 */
	public void insertRec(T element, P priority) {
		if (shortcut.containsKey(element)) {
			throw new UnsupportedOperationException("Element already in queue");
		}
		QueueElement qe = new QueueElement(element, priority);
		heap.add(qe);
		shortcut.put(element, heap.size() - 1);
		insertRec(heap.size() - 1);
	}

	/**
	 * The actual recursive implementation of a method that inserts an element into
	 * a heap structure
	 * 
	 * @param i index of the element from the heap structure that needs to be added
	 */
	protected void insertRec(int i) {
		if (i == 0 || c.compare(heap.get(parent(i)).prior, heap.get(i).prior) >= 0) {
			return;
		}
		swap(i, parent(i));
		insertRec(parent(i));
	}

	/**
	 * An iterative implementation of a method that extracts the first element from
	 * the priority queue
	 *
	 * @return the removed element if the queue is not empty, null if it is
	 * @throws UnsupportedOperationException when calling it on an empty queue
	 */
	public T extract() {
		if (heap.isEmpty()) {
			throw new UnsupportedOperationException("Cannot extract elements from an empty queue");
		}
		swap(heap.size() - 1, 0);
		QueueElement qe = heap.remove(heap.size() - 1);
		T out = qe.elem;
		shortcut.remove(out);
		if (heap.isEmpty()) {
			return out;
		}
		int i = 0;
		while (c.compare(heap.get(i).prior, heap.get(left(i)).prior) < 0
				|| c.compare(heap.get(i).prior, heap.get(right(i)).prior) < 0) {
			/*
			 * invariant: for each x in 0..i : heap.get(x).prior >=
			 * heap.get(parent(x)).prior -> minHeap
			 */
			/*
			 * invariant: for each x in 0..i : heap.get(x).prior <=
			 * heap.get(parent(x)).prior -> maxHeap
			 */
			if (c.compare(heap.get(left(i)).prior, heap.get(right(i)).prior) > 0) {
				swap(i, left(i));
				i = left(i);
			} else {
				swap(i, right(i));
				i = right(i);
			}
		}
		return out;
	}

	/**
	 * A recursive implementation of a method extracting the first element from the
	 * priority queue
	 *
	 * @return the removed element if the queue is not empty, null if it is
	 * @throws UnsupportedOperationException when calling it on an empty queue
	 */
	public T extractRec() {
		if (heap.isEmpty()) {
			throw new UnsupportedOperationException("Cannot extract elements from an empty queue");
		}
		swap(heap.size() - 1, 0);
		QueueElement qe = heap.remove(heap.size() - 1);
		T out = qe.elem;
		shortcut.remove(out);
		if (!heap.isEmpty()) {
			heapify(0);
		}
		return out;
	}

	/**
	 * A recursive implementation of a method building the heap structure of the
	 * priority queue
	 *
	 * @param i the root index of the priority queue inner tree
	 */
	protected void heapify(int i) {
		int largest = max(i, left(i), right(i));
		if (largest != i) {
			swap(largest, i);
			heapify(largest);
		}
	}

	/**
	 * Method that updates the priority of a given element with a new priority, also
	 * passed as param
	 *
	 * @param element  of which the priority needs to be updated
	 * @param priority the updated priority value for the given element
	 * @throws NoSuchElementException if the specified element is not in the queue
	 */
	public void updatePriority(T element, P priority) {
		if (!shortcut.containsKey(element)) {
			throw new NoSuchElementException("Element not found in queue");
		}
		int elementIndex = shortcut.get(element);
		P oldPrior = heap.get(elementIndex).prior;
		heap.get(elementIndex).prior = priority;
		if (c.compare(priority, oldPrior) > 0) {
			insertRec(elementIndex); // resetting heap from child up to parent
		} else {
			heapify(elementIndex); // resetting heap form parent down to left or right children
		}
	}

	/**
	 * Method that calculates the parent node index of given node index, passed as
	 * param
	 *
	 * @param i the node index
	 * @return the parent node index of node index, passed as param
	 */
	protected int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Method calculating the left sibling index of given node index
	 *
	 * @param i the node index
	 * @return the left sibling node index of node index, passed as param
	 */
	protected int left(int i) {
		return 2 * i + 1 < heap.size() ? 2 * i + 1 : i;
	}

	/**
	 * Method calculating the right sibling index of given node index
	 *
	 * @param i the node index
	 * @return the right sibling node index of node index, passed as param
	 */
	protected int right(int i) {
		return 2 * i + 2 < heap.size() ? 2 * i + 2 : i;
	}

	/**
	 * Method that swaps two elements of the heap structure, given their indexes
	 *
	 * @param i index of the first element
	 * @param j index of the second element
	 */
	protected void swap(int i, int j) {
		QueueElement temp = heap.get(i);
		heap.set(i, heap.get(j));
		shortcut.replace(heap.get(j).elem, i);
		heap.set(j, temp);
		shortcut.replace(temp.elem, j);
	}

	/**
	 * Method set to calculate the maximum integer value of a set of values, passed
	 * as params
	 *
	 * @param indexes integers values
	 * @return the relatively calculated maximum value
	 */
	public int max(int... indexes) {
		int max = indexes[0];
		for (int i = 1; i < indexes.length; i++) {
			if (c.compare(heap.get(indexes[i]).prior, heap.get(max).prior) > 0) {
				max = indexes[i];
			}
		}
		return max;
	}

	/**
	 * Auxiliary method getting the heap structure, useful for unit testing purposes
	 *
	 * @return the heap structure
	 */
	protected ArrayList<QueueElement> getHeap() {
		return heap;
	}

	/**
	 * Method telling if the inner heap structure of the priority queue is empty or
	 * not
	 *
	 * @return true if the heap structure is empty, false if it's not
	 */
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/**
	 * Method telling if an element, passed as param, is contained in the priority
	 * queue
	 * 
	 * @param element the element to search
	 * @return true if the priority queue contains the element, false if it does not
	 */
	public boolean contains(T element) {
		return shortcut.containsKey(element);
	}

	/**
	 * Method returning a string representation of the heap structure
	 *
	 * @return a string representing the heap structure
	 */
	@Override
	public String toString() {
		return heap.toString();
	}

	/**
	 * Inner class representing the a generic type of elements accepted by the heap
	 * structure of the priority queue
	 */
	protected class QueueElement {

		private T elem;
		private P prior;

		/**
		 * Constructor accepting a generic element and its priority, also generic
		 * 
		 * @param element  a generic element that can be inserted in the priority queue
		 *                 heap structure
		 * @param priority a generic priority value of the corresponding element, passed
		 *                 as param
		 */
		public QueueElement(T element, P priority) {
			this.elem = element;
			this.prior = priority;
		}

		/**
		 * Method returning a string representation of the queue element
		 * 
		 * @return a string representing the queue element
		 */
		@Override
		public String toString() {
			return "<" + this.elem + ", " + this.prior + ">";
		}

		/**
		 * Auxiliary method getting the element value of the queue element, useful for
		 * unit testing purposes
		 * 
		 * @return the element value of the queue element instance
		 */
		public T getElement() {
			return this.elem;
		}

		/**
		 * Auxiliary method getting the priority value of the queue element, useful for
		 * unit testing purposes
		 * 
		 * @return the priority value of the queue element instance
		 */
		public P getPriority() {
			return this.prior;
		}

	}

}
