package com.felix.simplebook.utils.timer;

import com.felix.simplebook.callback.ITimerListener;

import java.util.TimerTask;

/**
 * Created by android on 18-5-24.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener iTimerListener = null;

    public BaseTimerTask(ITimerListener iTimerListener) {
        this.iTimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if (iTimerListener != null) {
            iTimerListener.onTimer();
        }
    }

}
