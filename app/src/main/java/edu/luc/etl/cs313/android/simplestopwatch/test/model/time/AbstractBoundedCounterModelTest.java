package edu.luc.etl.cs313.android.simplestopwatch.test.model.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.model.counter.BoundedCounterModel;

/**
 * Testcase superclass for the time model abstraction.
 * This is a simple unit test of an object without dependencies.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractBoundedCounterModelTest {

    private BoundedCounterModel model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final BoundedCounterModel model) {
        this.model = model;
    }

    // testInitiallyAtMin – the timer is initially at 0
    public void testPreconditions() {
        assertEquals(0, model.getRuntime());
    }

    // testIncrement – one is added to the displayed value
    public void testIncrementtimeOne() {
        final int rt = model.getClickValue();
        model.increment();
        assertEquals((rt + 1), model.getClickValue());
    }

    // testFullAtMax – the maximum number that can be displayed on the timer is 99
    public void tesFullAtMax() {
        final int rt = model.getClickValue();
        for (int i = 0; i < 100; i ++) {
            model.increment();
        }
        assertEquals(99, model.getClickValue());
    }

    // testEmptyAtMin – the timer is at 0 when emptied
    public void tesFullAtMin() {
        final int rt = model.getClickValue();

        for (int i = 10; i > 0; i --) {
            model.decrement();
        }
        assertEquals(0, model.getClickValue());
    }

    // testGet – the timer value is consistent across gets

    // testIsFull – the timer isn’t full if decremented at full (99)
    public void tesIsFull() {
        final int rt = model.getRuntime();
        for (int i = 0; i < 101; i ++) {
            model.increment();
        }
        assertTrue(model.isFull());

    }

    // testIsEmpty – the timer isn’t empty if incremented at empty (0)
    public void tesIsEmpty() {
        final int rt = model.getRuntime();
        for (int i = 10; i >0; i --) {
            model.decrement();
        }
        assertTrue(model.isEmpty());
    }

    // testBeeping – When 3 seconds elapse after the user presses the increment button,
    // the timer beeping; when the timer counts down to 0, it starts beeping;
    // when the time is incremented to 99, the timer starts beeping.
    public void tesBeeping() {
//        final int rt = model.getRuntime();
//        model.incRuntime(); (do this for a few times)
//        (sleeps for 3 seconds)
//        (assert that the timer is beeping)
//        model.decRuntime(); (do this for a few times - make sure it’s emptied)
//        (assert that the timer is beeping)
//        model.incRuntime(); (do this for until the value reaches 99)
//        (assert that the timer is beeping)
    }

}
