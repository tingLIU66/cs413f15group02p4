package edu.luc.etl.cs313.android.simplestopwatch.model.time;

/**
 * An implementation of the stopwatch data model.
 */
public class DefaultTimeModel implements TimeModel {


    private int runningTime ;
    private int tickcount = 0;

  //  private int lapTime = -1;

    @Override
    public void resetRuntime() {
        runningTime = 0;
    }

    @Override
    public void decRuntime(int runningTime) {
        this.runningTime = runningTime--;
        //tickcount++;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }


  //  @Override
   // public void setLaptime() {
    //    lapTime = runningTime;
  //  }

  //  @Override
  //  public int getLaptime() {
      //  return lapTime;
   // }
}