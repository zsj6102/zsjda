package com.colpencil.tenement.Tools.PhotoPreview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.property.colpencil.colpencilandroidlibrary.Ui.MyViewPager;

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * <p/>
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * <p/>
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 *
 * @author Chris Banes
 */
public class PhotoViewPager extends MyViewPager {
	public PhotoViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setSupportDamping(true);
	}

	public PhotoViewPager(Context context) {
		super(context);
		this.setSupportDamping(true);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}
}
