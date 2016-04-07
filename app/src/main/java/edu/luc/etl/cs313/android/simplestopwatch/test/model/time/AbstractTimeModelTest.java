package edu.luc.etl.cs313.android.simplestopwatch.test.model.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * Testcase superclass for the time model abstraction.
 * This is a simple unit test of an object without dependencies.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractTimeModelTest {

    private TimeModel model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final TimeModel model) {
        this.model = model;
    }

    /**
     * Verifies that runtime and laptime are initially 0 or less.
     */
    @Test
    public void testPreconditions() {
        assertEquals(0, model.getRuntime());
       // assertTrue(model.getLaptime() <= 0);
    }

    /**
     * Verifies that runtime is incremented correctly.
     */
    @Test
    public void testIncrementRuntimeOne() {
        final int rt = model.getRuntime();
       // final int lt = model.getLaptime();
        model.decRuntime(2);
        assertEquals((rt - 1), model.getRuntime());
       // assertEquals(lt, model.getLaptime());
    }

    /**
     * Verifies that runtime turns over correctly.
     */
    @Test
    public void testIncrementRuntimeMany() {
        final int rt = model.getRuntime();
        //final int lt = model.getLaptime();
        for (int i = 10; i >0; i --) {
            model.decRuntime(i);
        }
        assertEquals(rt, model.getRuntime());
       // assertEquals(lt, model.getLaptime());
    }

    /**
     * Verifies that laptime works correctly.
     */
   /*
    @Test
    public void testLaptime() {
        final int rt = model.getRuntime();

        for (int i = 0; i < 5; i ++) {
            model.incRuntime();
        }
        assertEquals(rt + 5, model.getRuntime());
        assertEquals(lt, model.getLaptime());
        model.setLaptime();
        assertEquals(rt + 5, model.getLaptime());
        for (int i = 0; i < 5; i ++) {
            model.incRuntime();
        }
        assertEquals(rt + 10, model.getRuntime());
        assertEquals(rt + 5, model.getLaptime());
    }
    */
}
