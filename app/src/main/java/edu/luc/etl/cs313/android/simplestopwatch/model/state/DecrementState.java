package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class DecrementState implements SimpleTimerState {

    public DecrementState(final SimpleTimerSMStateView sm) {
        this.sm = sm;
    }

    private final SimpleTimerSMStateView sm;


    @Override
    public void onClickButton() {
        sm.actionStop();        //cancel timer
        sm.actionCancel();
        sm.toStoppedState();
        sm.updateButtonName();

    }

    @Override
    public void onTick() {
        sm.actionDecrement();
        sm.toDecrementState();

        //time remain = getClickcount()
        if(sm.getClickcount()==0){
            sm.actionStop();
            sm.toAlarmState();
            sm.updateButtonName();
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
    public void onAlarm() {

    }
}

