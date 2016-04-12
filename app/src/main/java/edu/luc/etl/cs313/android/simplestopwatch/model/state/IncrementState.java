package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import android.os.CountDownTimer;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class IncrementState implements SimpleTimerState {

    public IncrementState(final SimpleTimerSMStateView sm) {
        this.sm = sm;
    }

    private final SimpleTimerSMStateView sm;

    CountDownTimer cdt;

   private int tickcount = 0;

    volatile boolean ifthrees = false;

    @Override
    public void onClickButton() {
        if(cdt!= null)
           cdt.cancel();
        sm.actionIncrement();
        sm.actionUpdateView();

       //added on 4/11/2016
       //clickcount = getValue, if 0 < clickcount < 99, wait for 3 second, then count down
        //if clickcount = 99, count down immediately


            //else
            if (this.getValue() == 99)
            {
                  sm.actionBeep();
                  sm.actionStart();
                  sm.toDecrementState();
                  sm.updateButtonName();
            }

                 else sm.toIncrementState();
        }


    @Override
    public void onTick() {
        throw new UnsupportedOperationException("onTick");
    }


    @Override
    public void updateView() {
        sm.updateCountValue();           //update the number on display
    }

    @Override
    public int getId() {
        return R.string.SETTIME;
    }

    @Override
    public int getValue(){
        return sm.getValue();
    }

    @Override
    public void onAlarm() {

    }

    }




