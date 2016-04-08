package edu.luc.etl.cs313.android.simplestopwatch.test.model.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.BuildConfig;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.SimpleTimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.OnTickListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.counter.BoundedCounterModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.SimpleTimerStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;
import java8.util.function.BooleanSupplier;

/**
 * Testcase superclass for the stopwatch state machine model. Unit-tests the state
 * machine in fast-forward mode by directly triggering successive tick events
 * without the presence of a pseudo-real-time clock. Uses a single unified mock
 * object for all dependencies of the state machine model.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractSimpleTimerStateMachineTest {

    private SimpleTimerStateMachine model;

    private UnifiedMockDependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = new UnifiedMockDependency();
    }

    @After
    public void tearDown() {
        dependency = null;
    }

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final SimpleTimerStateMachine model) {
        this.model = model;
        if (model == null)
            return;
        this.model.setUIUpdateListener(dependency);
        this.model.actionInit();
    }

    protected UnifiedMockDependency getDependency() {
        return dependency;
    }

    /**
     * Verifies that we're initially in the stopped state (and told the listener
     * about it).
     */
    @Test
    public void testPreconditions() {
        assertEquals(R.string.STOPPED, dependency.getState());
    }

    /**
     * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
     * expect time 5.
     */
    @Test
    public void testScenarioRun() {
        assertTimeEquals(0);
        // directly invoke the button press event handler methods
        model.onClickButton();
        onTickRepeat(5);
        assertTimeEquals(5);
    }

    /**
     * Verifies the following scenario: time is 0, press start, wait 5+ seconds,
     * expect time 5, press lap, wait 4 seconds, expect time 5, press start,
     * expect time 5, press lap, expect time 9, press lap, expect time 0.
     *
     * @throws Throwable
     */
    @Test
    public void testScenarioRunLapReset() {
        assertTimeEquals(0);
        // directly invoke the button press event handler methods
        model.onClickButton();
        assertEquals(R.string.SETTIME, dependency.getState());
        assertTrue(dependency.isStarted());
        onTickRepeat(5);
        assertTimeEquals(5);

    }

    /**
     * Sends the given number of tick events to the model.
     *
     *  @param n the number of tick events
     */
    protected void onTickRepeat(final int n) {
        for (int i = 99; i > 0; i--)
            model.onTick();
    }

    /**
     * Checks whether the model has invoked the expected time-keeping
     * methods on the mock object.
     */
    protected void assertTimeEquals(final int t) {
        assertEquals(t, dependency.getTime());
    }
}

/**
 * Manually implemented mock object that unifies the three dependencies of the
 * stopwatch state machine model. The three dependencies correspond to the three
 * interfaces this mock object implements.
 *
 * @author laufer
 */
class UnifiedMockDependency implements TimeModel, ClockModel, BoundedCounterModel, SimpleTimerUIUpdateListener {

    private int timeValue = -1, stateId = -1;

    private int runningTime = 0, lapTime = -1;

    private boolean started = false;

    public int getTime() {
        return timeValue;
    }

    public int getState() {
        return stateId;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void updateTime(final int timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public void updateState(final int stateId) {
        this.stateId = stateId;
    }

    @Override
    public void updateButton(int stateId) {
        this.stateId = stateId;
    }      //added on 4/6/2016

    @Override
    public void updateCount() {
    }                  //added on 4/6/2016

    @Override
    public void setOnTickListener(OnTickListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void decRuntime(int runningTime) {
        this.runningTime = runningTime--;
        //tickcount++;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }


    /**
     * The current value of the counter.
     */
    private int value;


    /**
     * Indicates whether this counter currently satisfies its internal data
     * invariant: the counter value is within the bounds.
     *
     * @return whether the data invariant is satisfied
     */
    protected boolean dataInvariant() {
        return 0 <= value && value <= 99;
    }

    @Override
    public void increment() {
        assertIfDebug(() -> dataInvariant() && !isFull());
        ++value;
        assertIfDebug(this::dataInvariant);
    }

    @Override
    public void decrement() {
        assertIfDebug(() -> dataInvariant() && !isEmpty());
        --value;
        assertIfDebug(this::dataInvariant);
    }

    @Override
    public void reset() {
        while (!isEmpty()) {
            decrement();
        }
    }

    @Override
    public int getClickValue() {
        return value;
    }

    @Override
    public boolean isFull() {
        return value >= 99;
    }

    @Override
    public boolean isEmpty() {
        return value <= 0;
    }

    protected void assertIfDebug(final BooleanSupplier p) {
        if (BuildConfig.DEBUG && !p.getAsBoolean()) {
            throw new AssertionError();
        }
    }

}
