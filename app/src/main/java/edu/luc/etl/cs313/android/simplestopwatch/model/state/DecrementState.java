package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class DecrementState implements SimpleTimerState {

    public DecrementState(final SimpleTimerSMStateView sm) {
        this.sm = sm;
    }

    private final SimpleTimerSMStateView sm;


    @Override
    public void onCancel() {
        sm.actionStop();        //cancel timer
        sm.actionReset();
        sm.toStoppedState();

    }

    @Override
    public void onTick() {
        sm.actionDecrement();
        sm.toDecrementState();
        if(sm.getClickcount()==0){          //TO DO
            sm.actionStop();
            sm.updateButtonName();
            sm.toAlarmState();
            sm.actionAlarm();
        }
    }


    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.RUNNING;
    }

    @Override
    public int getValue(){
        return sm.getValue();
    }

    @Override
    public void onIncrement(){}

    @Override
    public void onStop(){}

}

