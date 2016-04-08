package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class AlarmState implements SimpleTimerState {

    public AlarmState(final SimpleTimerSMStateView sm) {
        this.sm = sm;
    }

    private final SimpleTimerSMStateView sm;

    @Override
    public void onClickButton() {
        //sm.actionStopAlarm();
        sm.actionStop();
        sm.actionReset();
        sm.toStoppedState();
        sm.updateButtonName();

    }

    public boolean setStopAlarmFlag(){
        return true;
    }
    @Override
    public void onTick() {
        throw new UnsupportedOperationException("onTick");
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARM;
    }


    @Override
    public int getValue()
    {
        return 0; //return sm.getValue();
    }

}