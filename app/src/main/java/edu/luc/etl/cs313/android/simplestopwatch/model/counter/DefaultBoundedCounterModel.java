package edu.luc.etl.cs313.android.simplestopwatch.model.counter;

import java8.util.function.BooleanSupplier;
import edu.luc.etl.cs313.android.simplestopwatch.BuildConfig;

/**
 * Created by developer on 4/4/2016.
 */
public class DefaultBoundedCounterModel implements BoundedCounterModel {

    /** The lower bound of the counter. */
    private final int min;

    /** The upper bound of the counter. */
    private final int max;

    /** The current value of the counter. */
    private int value;

    /** Constructs a bounded counter with the default bounds. */
    //public DefaultBoundedCounterModel() {
        //this(0, 99);
    //}

    /**
     * Constructs a bounded counter with the given bounds.
     *
     * @param min the lower bound
     * @param max the upper bound
     * @throws IllegalArgumentException if min > max
     */
    public DefaultBoundedCounterModel(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min >= max");
        }
        this.min = min;
        this.max = max;
        this.value = this.min;
    }

    /**
     * Indicates whether this counter currently satisfies its internal data
     * invariant: the counter value is within the bounds.
     *
     * @return whether the data invariant is satisfied
     */
    protected boolean dataInvariant() {
        return min <= value && value <= max;
    }

    @Override
    public void increment() {
            ++value;
     }

    @Override
    public void decrement() {
           --value;
     }

        @Override
    public int getClickValue() {
        return value;
    }

    @Override
    public void reset() {
        while (!isEmpty()) {
            decRuntime();
        }
    }

    @Override
    public boolean isFull() {
        return value >= max;
    }

    @Override
    public boolean isEmpty() {
        return value <= min;
    }


    private int runningTime ;

    @Override
    public void resetRuntime() {
        this.runningTime = 0;
    }

    @Override
    public void decRuntime() {
        this.runningTime = value--;
}

    @Override
    public int getRuntime() {
        return this.runningTime;
    }

}
