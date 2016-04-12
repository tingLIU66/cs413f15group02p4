package edu.luc.etl.cs313.android.simplestopwatch.model.clock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An implementation of the internal clock.
 *
 * @author laufer
 */
public class DefaultClockModel implements ClockModel {

    // TODO make accurate by keeping track of partial seconds when canceled etc.

    private Timer timer,alarmtimer;

    private OnTickListener listener;

    @Override
    public void setOnTickListener(final OnTickListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() {
        timer = new Timer();

        // The clock model runs onTick every 1000 milliseconds
        timer.schedule(new TimerTask() {
            @Override public void run() {
                // fire event
                listener.onTick();
            }
        }, /*initial delay*/ 1000, /*periodic delay*/ 1000);
    }

    @Override
    public void stop() {
        timer.cancel();
    }


    @Override
    public void startAlarm() {
        alarmtimer = new Timer();
        //added on 4/9/2016
        // The clock model runs onAlarm every 2000 milliseconds
        alarmtimer.schedule(new TimerTask() {
            @Override public void run() {
                // fire event
                listener.onAlarm();
            }
        }, /*initial delay*/ 1000, /*periodic delay*/ 2000);
    }

    @Override
    public void stopAlarm() {
        alarmtimer.cancel();
    }
}