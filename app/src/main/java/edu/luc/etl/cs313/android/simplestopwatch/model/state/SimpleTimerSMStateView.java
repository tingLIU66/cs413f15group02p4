package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface SimpleTimerSMStateView {

    // transitions
    void toIncrementState();          //added on 4/1/2016
    void toDecrementState();          //added on 4/1/2016
    void toStoppedState();          //added on 4/1/2016
    void toAlarmState();            //added on 4/1/2016

    // actions
    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    void actionBeep();
    void actionAlarm();
    void actionStopAlarm();
    void actionIncrement();
    void actionDecrement();
    void actionUpdateView();
    int getValue();                //added on 4/1/2016
    int getClickcount();
    int getTickcount();
    boolean isFull();

    // state-dependent UI updates
    void updateUIRuntime();
    void updateButtonName();        //added on 4/1/2016
    void updateCountValue();        //added on 4/4/2016
}
