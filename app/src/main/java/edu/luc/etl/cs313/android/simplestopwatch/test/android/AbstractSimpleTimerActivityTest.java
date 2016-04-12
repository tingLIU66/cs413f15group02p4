package edu.luc.etl.cs313.android.simplestopwatch.test.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import android.content.pm.ActivityInfo;
import org.junit.Test;

import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.android.SimpleTimerAdapter;

/**
 * Abstract GUI-level test superclass of several essential stopwatch scenarios.
 *
 * @author laufer
 *
 * TODO move this and the other tests to src/test once Android Studio supports
 * non-instrumentation unit tests properly.
 */
public abstract class AbstractSimpleTimerActivityTest {

    // testLaunch – activity is successfully launched
    public void testActivityCheckTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", getActivity());
    }

    /**
     * Verifies the following scenario: time is 0.
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioInit() throws Throwable {
        getActivity().runOnUiThread(() -> assertEquals(0, getDisplayedValue()));
    }
    // testActivityScenarioInc performs these checks:
    // There should be a button that is able to increase the number on the display,
    // and it should be increased the value by 1.
    public void testActivityScenarioInc() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertTrue(getButton().performClick());
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue());
        });
    }

    // testActivityScenarioRun: Initially the displayed value is 0,
    // with the button acting as the increment button,
    // and every click of the button increments the value by 1.
    // If 3 seconds elapse after the last click, the clock starts counting down.
    public void testActivityScenarioRun() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertTrue(getButton().performClick());
        });
        Thread.sleep(3000);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals("Running", getState());
            // Need to write a getState() method
        });
    }

    // testActivityScenarioIncUntil99: The test checks for when the display value is at 99,
    // it doesn’t increase but instead it stays the same when the button is pressed.
    // When the value reaches 99, the name of the button is changed from Increment to Cancel.
    public void testActivityScenarioUntil99() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals("Increment", getButtonText());
            for (int i = 0; i < 101; i++)
                assertTrue(getButton().performClick());
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(99, getDisplayedValue());
            assertEquals("Running", getState());
        });
    }

    // testButtonFunctionPerState: When the timer is at the stop state, the button acts as an increment button;
    // when the timer is at the running state, the button acts as a cancel button;
    // when the timer is at the alarm state, the button acts as a stop beeping button.
    public void testButtonFunctionPerState() throws Throwable {
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());
            assertEquals("Increment", getButtonText());
            for (int i = 0; i < 3; i++)
                assertTrue(getButton().performClick());
        });
        Thread.sleep(5000);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(1, getDisplayedValue());  //set time to 3, wait for 5 seconds, the remaining time is 1
            assertEquals("Running", getState());
            assertEquals("Cancel", getButtonText());
        });
        Thread.sleep(10000);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            assertEquals(0, getDisplayedValue());     //10 seconds elapse, counting down is finished, the time is 0 on the display
            assertEquals("ALARM", getState());
            assertEquals("STOP", getButtonText());
        });
    }

    // testRotation – The display will not be changed when the screen is rotated.
    public void testRotation() throws Throwable {
        assertEquals(0, getDisplayedValue());
        assertTrue(getButton().performClick());
        assertTrue(getButton().performClick());
        assertTrue(getButton().performClick());
        assertEquals(3, getDisplayedValue());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertEquals(3, getDisplayedValue());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        assertEquals(3, getDisplayedValue());
    }

    // auxiliary methods for easy access to UI widgets

    protected abstract SimpleTimerAdapter getActivity();

    protected int tvToInt(final TextView t) {
        return Integer.parseInt(t.getText().toString().trim());
    }

    protected int getDisplayedValue() {
        final TextView ts = (TextView) getActivity().findViewById(R.id.seconds);
        //final TextView tm = (TextView) getActivity().findViewById(R.id.minutes);
        return tvToInt(ts);
    }

    protected Button getButton() {
        return (Button) getActivity().findViewById(R.id.button);
    }

    protected String getState() {
        final TextView ts = (TextView) getActivity().findViewById(R.id.stateName);
        return ts.getText().toString();
    }

    protected String getButtonText() {
        final TextView bt = (TextView)getActivity().findViewById(R.id.button);
        return bt.getText().toString();
    }

    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() { }
}
