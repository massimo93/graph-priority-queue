package graphs.usage;

import java.util.Comparator;

/**
 * A comparator implementing the Comparator interface accepting only generic objects that are comparable
 * @param <T> type of the Comparable and Comparator interface element
 */
public class MinComparator<T extends Comparable<? super T>> implements Comparator<T> {

    /**
     * 
     * @param o1 first element that needs to be compared
     * @param o2 second element which previous element is compared with
     * @return the value 0 if the argument o1 value is equal to the argument o2 value (according to its comparison principle);
     * a value less than 0 if the argument o2 value is less than the argument o1 value (according to its comparison principle);
     * a value greater than 0 if the argument o2 value is greater than the argument o1 value (according to its comparison principle)
     */
    @Override
    public int compare(T o1, T o2) {
        return -o1.compareTo(o2);
    }
    
}
