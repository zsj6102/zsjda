package com.colpencil.propertycloud.Ui;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * User:Chenbao
 * This View is a view for recording
 */
public class RecordView extends ImageView {

    private RecordListener listener;

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(RecordListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (listener != null) {
                    listener.startRecord();
                }
                break;
            case MotionEvent.ACTION_UP:
                SystemClock.sleep(500);
                if (listener != null) {
                    listener.stopRecord();
                }
                break;
        }
        return true;
    }

    /**
     * recording control interface
     */
    public interface RecordListener {
        void startRecord();

        void stopRecord();
    }
}
