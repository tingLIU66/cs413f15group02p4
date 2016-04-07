package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.SimpleTimerUIUpdateSource;
import edu.luc.etl.cs313.android.simplestopwatch.common.SimpleTimerUIListener;


/**
 * A thin model facade. Following the Facade pattern,
 * this isolates the complexity of the model from its clients (usually the adapter).
 *
 * @author laufer
 */
public interface SimpleTimerModelFacade extends SimpleTimerUIListener, SimpleTimerUIUpdateSource {
    void onStart();
}
