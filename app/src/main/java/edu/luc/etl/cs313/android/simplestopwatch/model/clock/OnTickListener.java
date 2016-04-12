package edu.luc.etl.cs313.android.simplestopwatch.model.clock;

/**
 * A listener for onTick events coming from the internal clock model.
 *
 * @author laufer
 */
public interface OnTickListener {
    void onTick();
    void onAlarm();  //added on 4/9/2016 onAlarm listener
}
