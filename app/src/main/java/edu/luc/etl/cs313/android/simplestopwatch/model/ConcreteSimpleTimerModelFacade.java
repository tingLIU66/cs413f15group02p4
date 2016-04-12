package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.SimpleTimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.DefaultClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.counter.BoundedCounterModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.counter.DefaultBoundedCounterModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultSimpleTimerStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.SimpleTimerStateMachine;

/**
 * An implementation of the model facade.
 *
 * @author laufer
 */
public class ConcreteSimpleTimerModelFacade implements SimpleTimerModelFacade {

    private SimpleTimerStateMachine stateMachine;

    private ClockModel clockModel;

   // private TimeModel timeModel;

    private BoundedCounterModel boundedcounterModel;

    public ConcreteSimpleTimerModelFacade() {
        clockModel = new DefaultClockModel();
        boundedcounterModel = new DefaultBoundedCounterModel(0,99);  //added 0n 4/4/2016 set boundedcounter's minValue = 0; maxValue = 99;
        stateMachine = new DefaultSimpleTimerStateMachine(clockModel, boundedcounterModel);  ////added 0n 4/4/2016
        clockModel.setOnTickListener(stateMachine);
    }

    @Override
    public void onStart() {
        stateMachine.actionInit();
    }

    @Override
    public void setUIUpdateListener(final SimpleTimerUIUpdateListener listener) {
        stateMachine.setUIUpdateListener(listener);
    }

    @Override
    public int getValue() { return stateMachine.getValue();}   //added on 4/4/2016

    @Override
    public void onClickButton() {
        stateMachine.onClickButton();
    }


}
