package priorityqueue.source;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Class set for main runner for a set of unit-tests
 */
public class PriorityQueueTestsRunner {

	/**
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(PriorityQueueTests.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());
	}

}
