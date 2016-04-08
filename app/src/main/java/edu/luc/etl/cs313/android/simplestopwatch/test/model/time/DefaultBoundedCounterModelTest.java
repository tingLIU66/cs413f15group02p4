package edu.luc.etl.cs313.android.simplestopwatch.test.model.time;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simplestopwatch.model.counter.DefaultBoundedCounterModel;


/**
     * Concrete testcase subclass for the default time model implementation.
     *
     * @author laufer
     * @see http://xunitpatterns.com/Testcase%20Superclass.html
     */
    public class DefaultBoundedCounterModelTest extends AbstractBoundedCounterModelTest {

        @Before
        public void setUp() throws Exception {
            setModel(new DefaultBoundedCounterModel(0,99));
        }

        @After
        public void tearDown() throws Exception {
            setModel(null);
        }
    }

