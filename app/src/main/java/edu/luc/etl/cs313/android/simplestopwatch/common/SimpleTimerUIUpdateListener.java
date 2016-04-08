package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * A listener for UI update notifications.
 * This interface is typically implemented by the adapter, with the
 * notifications coming from the model.
 *
 * @author laufer
 */
public interface SimpleTimerUIUpdateListener {
    void updateTime(int timeValue);
    void updateState(int stateId);
    void updateButton(int stateId);      //added on 4/ic_launcher/2016
    void updateCount();                  //added on 4/4/2016
    void playDefaultALARM();
}
