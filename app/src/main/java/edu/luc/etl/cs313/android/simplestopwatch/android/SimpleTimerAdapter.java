package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.SimpleTimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.ConcreteSimpleTimerModelFacade;
import edu.luc.etl.cs313.android.simplestopwatch.model.SimpleTimerModelFacade;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import java.io.IOException;

//import edu.luc.etl.cs313.misc.boundedcounter.cli.BoundedCounter;

/**
 * A thin adapter component for the simpleTimer.
 *
 * @author laufer
 */
public class SimpleTimerAdapter extends Activity implements SimpleTimerUIUpdateListener {

    private static String TAG = "SimpleTimer-android-activity";

    /**
     * The state-based dynamic model.
     */
    private SimpleTimerModelFacade model;

    protected void setModel(final SimpleTimerModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);
        // inject dependency on model into this so model receives UI events
        this.setModel(new ConcreteSimpleTimerModelFacade());
        // inject dependency on this into model to register for UI updates
        model.setUIUpdateListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.onStart();
    }

    // TODO remaining lifecycle methods

    /**
     * Updates the seconds and minutes in the UI.
     * @param time
     */
    public void updateTime(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvS = (TextView) findViewById(R.id.seconds);
           // final TextView tvM = (TextView) findViewById(R.id.minutes);
            final int seconds = time;
           // final int minutes = time / Constants.SEC_PER_MIN;
            tvS.setText(Integer.toString(seconds / 10) + Integer.toString(seconds % 10));
           // tvS.setText(Integer.toString(seconds));
           // tvM.setText(Integer.toString(minutes / 10) + Integer.toString(minutes % 10));
        });
    }

    /**
     * Updates the state name in the UI.
     * @param stateId
     */
    public void updateState(final int stateId) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView stateName = (TextView) findViewById(R.id.stateName);
            stateName.setText(getString(stateId));
        });
    }

    /**
     * Updates the button name in the UI.
     * @param stateId
     */
    public void updateButton(final int stateId){
       //UI adapter responsibility to schedule incoming events on UI thread
       //added on 4/ic_launcher/2016
        runOnUiThread(() -> {
           final TextView buttonName = (TextView) findViewById(R.id.button);
            if(stateId == R.string.SETTIME || stateId == R.string.STOPPED)
                buttonName.setText("Increment");
            else if(stateId == R.string.RUNNING)
                buttonName.setText("Cancel");
            else if(stateId == R.string.ALARM)
                buttonName.setText("Stop");
        });
    }

    /**
     * Updates the view from the model.
     */
    // begin-method-updateView
    public void updateCount() {
        final TextView valueView = (TextView) findViewById(R.id.seconds);
        valueView.setText(Integer.toString(model.getValue() / 10) + Integer.toString(model.getValue() % 10));      //added on 4/4/2016

        // afford controls according to model state
        //findViewById(R.id.button).setEnabled(!model.isFull());
    }
    // end-method-updateView

    // forward event listener methods to the model

    public void onClickButton(final View view) {
        model.onClickButton();
    }

    public void playDefaultALARM() {
        final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        final Context context = getApplicationContext();

        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
