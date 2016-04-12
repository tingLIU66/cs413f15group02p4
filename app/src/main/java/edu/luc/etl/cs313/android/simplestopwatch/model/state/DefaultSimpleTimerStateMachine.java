package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.luc.etl.cs313.android.simplestopwatch.common.SimpleTimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.counter.BoundedCounterModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultSimpleTimerStateMachine implements SimpleTimerStateMachine {

    public DefaultSimpleTimerStateMachine(final ClockModel clockModel, final BoundedCounterModel boundedcounterModel) {
       // this.timeModel = timeModel;
        this.clockModel = clockModel;
        this.boundedcounterModel = boundedcounterModel;           //added on 4/42016
    }

    private boolean stopbeepflag = false;
    //private AtomicBoolean stopbeepflag ;

    //private int clickcount = getValue();

    private int tickcount = 0;

    //private final TimeModel timeModel;

    private final ClockModel clockModel;

    private final BoundedCounterModel boundedcounterModel;        //added on 4/42016

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private SimpleTimerState state;

    protected void setState(final SimpleTimerState state) {
        this.state = state;
        uiUpdateListener.updateState(state.getId());
    }

    private SimpleTimerUIUpdateListener uiUpdateListener;

    @Override
    public void setUIUpdateListener(final SimpleTimerUIUpdateListener uiUpdateListener) {
        this.uiUpdateListener = uiUpdateListener;
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread
    @Override public synchronized void onClickButton() { state.onClickButton(); }
    @Override public synchronized void onTick()        { state.onTick(); }
    @Override public synchronized void onAlarm()       { actionBeep(); }

    @Override public void updateUIRuntime()   { uiUpdateListener.updateTime(boundedcounterModel.getRuntime()); }
    @Override public void updateButtonName()  { uiUpdateListener.updateButton(state.getId());}        //added on 4/1/2016
    @Override public void updateCountValue()  { uiUpdateListener.updateCount(); }                     //added on 4/4/2016
    @Override public void actionBeep()        { uiUpdateListener.playDefaultALARM(); }                //added on 4/10/2016

    // known states
    private final SimpleTimerState STOPPED     = new StoppedState(this);
    private final SimpleTimerState RUNNING     = new DecrementState(this);
    private final SimpleTimerState SETTIME     = new IncrementState(this);
    private final SimpleTimerState ALARM       = new AlarmState(this);

    // transitions
    @Override public void toDecrementState()    { setState(RUNNING); }
    @Override public void toIncrementState()    { setState(SETTIME); }
    @Override public void toStoppedState()    { setState(STOPPED); }
    @Override public void toAlarmState()      { setState(ALARM); }

    // actions
    @Override public void actionInit()       { toStoppedState(); actionReset(); }
    @Override public void actionReset()      { boundedcounterModel.resetRuntime(); actionUpdateView(); }
    @Override public void actionCancel()      { boundedcounterModel.reset(); updateCountValue(); }
    @Override public void actionStart()      { clockModel.start(); actionUpdateView();}  //added on 4/4/2016
    @Override public void actionStop()       { clockModel.stop(); }
    @Override public void actionAlarm()      { clockModel.startAlarm();}  //added on 4/10/2016
    @Override public void actionStopAlarm()   {clockModel.stopAlarm();}   //added on 4/10/2016
    @Override public void actionIncrement()   { boundedcounterModel.increment(); actionUpdateView(); }
    @Override public void actionDecrement()   { boundedcounterModel.decRuntime(); actionUpdateView();}
    @Override public int getClickcount()      {return boundedcounterModel.getRuntime();}   //added on 4/4/2016 time remain = getClickcount()
    @Override public void actionUpdateView()   { state.updateView(); }
    @Override public int getValue()            { return boundedcounterModel.getClickValue();}        //added on 4/4/2016
    @Override public boolean isFull()          {return boundedcounterModel.isFull();}


}
