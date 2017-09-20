package com.colpencil.propertycloud.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout.LayoutParams;

import com.colpencil.propertycloud.Tools.ScreenUtils;

public class CustomSurfaceView extends SurfaceView {

	public static final String TAG = "MyGesture";

	GestureDetector mGestureDetector = null;

	ScaleGestureDetector scaleGestureDetector = null;

	private int screenHeight = 0;

	private int screenWidth = 0;

	/** ��¼��������Ƭģʽ���ǷŴ���С��Ƭģʽ */
	private int mode = 0;// ��ʼ״̬
	/** ������Ƭģʽ */
	private static final int MODE_DRAG = 1;
	/** �Ŵ���С��Ƭģʽ */
	private static final int MODE_ZOOM = 2;

	private static final int MODE_DOUBLE_CLICK = 3;

	private long firstTime = 0;

	private static final float SCALE_MAX = 4.0f;

	private static float touchSlop = 0;

	private int start_Top = -1, start_Right = -1, start_Left = -1, start_Bottom = -1;

	private int start_x, start_y, current_x, current_y;

	View view;

	private int View_Width = 0;

	private int View_Height = 0;

	private int initViewWidth = 0;

	private int initViewHeight = 0;

	private int fatherView_W;

	private int fatherView_H;

	private int fatherTop;

	private int fatherBottom;

	int distanceX = 0;

	int distanceY = 0;

	private boolean isControl_Vertical = false;

	private boolean isControl_Horizal = false;

	private float ratio = 0.3f;

	public CustomSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomSurfaceView(Context context) {
		super(context);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		touchSlop = ViewConfiguration.getTouchSlop();
		this.setFocusable(true);
		this.setClickable(true);
		this.setLongClickable(true);
		view = CustomSurfaceView.this;
		screenHeight = ScreenUtils.getScreenHeight(getContext());
		screenWidth = ScreenUtils.getScreenWidth(getContext());
		scaleGestureDetector = new ScaleGestureDetector(getContext(), new simpleScaleGestueListener());
		mGestureDetector = new GestureDetector(new simpleGestureListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			onTouchDown(event);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			onPointerDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			onTouchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			mode = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = 0;
			break;
		default:
			break;
		}
		return scaleGestureDetector.onTouchEvent(event);
	}

	/**
	 * �����¼��ַ�
	 * 
	 * @param event
	 */
	private void onTouchMove(MotionEvent event) {
		int left = 0, top = 0, right = 0, bottom = 0;
		if (mode == MODE_DRAG) {
			left = getLeft();
			right = getRight();
			top = getTop();
			bottom = getBottom();
			distanceX = (int) (event.getRawX() - current_x);
			distanceY = (int) (event.getRawY() - current_y);
			if(icallBack!=null){
				icallBack.getAngle((int)getX(),this.getWidth());
			}
			if (touchSlop <= getDistance(distanceX, distanceY)) {
				left = left + distanceX;
				right = right + distanceX;
				bottom = bottom + distanceY;
				top = top + distanceY;
				// ˮƽ�ж�
				if (isControl_Horizal) {
					if (left >= 0) {
						left = 0;
						right = this.getWidth();
					}
					if (right <= screenWidth) {
						left = screenWidth - this.getWidth();
						right = screenWidth;
					}
				} else {
					left = getLeft();
					right = getRight();
				}
				// ��ֱ�ж�
				if (isControl_Vertical) {
					if (top > 0) {
						top = 0;
						bottom = this.getHeight();
					}
					if (bottom <= start_Bottom) {
						bottom = start_Bottom;
						top = fatherView_H - this.getWidth();
					}
				} else {
					top = this.getTop();
					bottom = this.getBottom();
				}
				if (isControl_Horizal || isControl_Vertical) {
					this.setPosition(left, top, right, bottom);
				}
				current_x = (int) event.getRawX();
				current_y = (int) event.getRawY();
			}
		}
	}

	/**
	 * ��㴥��ʱ
	 * 
	 * @param event
	 */
	private void onPointerDown(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			mode = MODE_ZOOM;
		}
	}

	/**
	 * ����ʱ���¼�
	 * 
	 * @param event
	 */

	private void onTouchDown(MotionEvent event) {
		mode = MODE_DRAG;
		start_x = (int) event.getRawX();
		start_y = (int) event.getRawY();
		current_x = (int) event.getRawX();
		current_y = (int) event.getRawY();
		View_Width = getWidth();
		View_Height = getHeight();
		if (View_Height > fatherView_H) {
			isControl_Vertical = true;
		} else {
			isControl_Vertical = false;
		}
		if (View_Width > fatherView_W) {
			isControl_Horizal = true;
		} else {
			isControl_Horizal = false;
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (start_Top == -1) {
			start_Top = top;
			start_Left = left;
			start_Right = right;
			start_Bottom = bottom;
			initViewWidth = view.getWidth();
			initViewHeight = view.getHeight();
		}
	}

	/**
	 * �������Ƶļ����¼�
	 * 
	 * @author Administrator
	 * 
	 */
	private class simpleScaleGestueListener implements ScaleGestureDetector.OnScaleGestureListener {
		// �õ��ķŴ���С�ķ���
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			int left = 0, right = 0, top = 0, bottom = 0;
			float length = 0;
			if (mode == MODE_ZOOM) {
				float ratio = detector.getScaleFactor();
				left = getLeft();
				top = getTop();
				bottom = getBottom();
				right = getRight();
				if (ratio > 1) { // �Ŵ�ײ״̬
					length = (int) ((getHeight() * (ratio - 1)) / 7.0);
					left = (int) (left - length / 2);
					right = (int) (right + length / 2);
					bottom = (int) (bottom + length / 2);
					top = (int) (top - length / 2);
					if (getWidth() <= (screenWidth * 3) && getHeight() <= (fatherView_H * 3)) {
						setPosition(left, top, right, bottom);
					}
				} else {
					length = (int) ((getHeight() * (1 - ratio)) / 7.0);
					left = (int) (left + length / 2);
					right = (int) (right - length / 2);
					bottom = (int) (bottom - length / 2);
					top = (int) (top + length / 2);
					if (left >= 0) {
						left = 0;
					}
					if (right <= screenWidth) {
						right = screenWidth;
					}
					if (top >= 0) {
						top = 0;
					}
					if (bottom <= fatherView_H) {
						bottom = fatherView_H;
					}
					if (getWidth() > initViewWidth && getHeight() > fatherView_H) {
						setPosition(left, top, right, bottom);
					} else {
						setPosition(start_Left, start_Top, start_Right, start_Bottom);
					}
				}
			}
			return false;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

		}

	}

	/**
	 * ˫���¼��Ĵ���
	 * 
	 * @author Administrator
	 * 
	 */
	private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener {
		// �õ���˫���ķ���
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(TAG, "˫����Ļ");
			// ˫����Ļ
			int left = 0, top = 0, right = 0, bottom = 0;
			int length = 0;
			left = getLeft();
			top = getTop();
			bottom = getBottom();
			right = getRight();
			if (getHeight() > fatherView_H) {
				// ��Сģʽ
				Log.i(TAG, "��Сģʽ");
				while (getHeight() > fatherView_H) {
					length = (int) ((getHeight() * ratio) / 5.0);
					left = (int) (getLeft() + length / 2);
					right = (int) (getRight() - length / 2);
					bottom = (int) (getBottom() - length / 2);
					top = (int) (getTop() + length / 2);
					if (left >= 0) {
						left = 0;
					}
					if (right <= screenWidth) {
						right = screenWidth;
					}
					if (top >= 0) {
						top = 0;
					}
					if (bottom <= fatherView_H) {
						bottom = fatherView_H;
					}
					if (getWidth() > initViewWidth && getHeight() > fatherView_H) {
						setPosition(left, top, right, bottom);
					} else {
						setPosition(start_Left, start_Top, start_Right, start_Bottom);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			} else {
				// �Ŵ�ģʽ
				Log.i(TAG, "�Ŵ�ģʽ");
				if (getHeight() <= fatherView_H) {
					while (getHeight() < initViewHeight * 2) {
						length = (int) ((getHeight() * ratio) / 5.0);
						left = (int) (getLeft() - length / 2);
						right = (int) (getRight() + length / 2);
						bottom = (int) (getBottom() + length / 2);
						top = (int) (getTop() - length / 2);
						if (getWidth() <= (screenWidth * 3) && getHeight() <= (fatherView_H * 3)) {
							setPosition(left, top, right, bottom);
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			return true;
		}
		
	}

	/** ʵ���϶��Ĵ��� */
	private void setPosition(int left, int top, int right, int bottom) {
		this.layout(left, top, right, bottom);

	}

	/**
	 * surfaceView���ؼ��Ŀ��
	 * 
	 * @param fatherView_Width
	 * @param fatherView_Height
	 */
	public void setFatherW_H(int fatherView_Width, int fatherView_Height) {
		this.fatherView_W = fatherView_Width;
		this.fatherView_H = fatherView_Height;
	}

	public void setLayoutParam(float scale) {
		LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.width = (int) (scale * (layoutParams.width));
		layoutParams.height = (int) (scale * (layoutParams.height));
		setLayoutParams(layoutParams);
	}

	/** ��ȡ����ľ��� **/
	private float getDistance(float distanceX, float distanceY) {
		return (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	}

	public void setFatherTopAndBottom(int fatherTop, int fatherBottom) {
		this.fatherTop = fatherTop;
		this.fatherBottom = fatherBottom;
	}

	 /**
     * һ��һ���ӿ�
     */
    public interface ICoallBack {
        void getAngle(int angle, int viewW);
    }

    /**
     * ��ʼ���ӿڱ���
     */
    ICoallBack icallBack = null;

    /**
     * �Զ���ؼ����Զ����¼�
     */
    public void setEvent(ICoallBack iBack) {
        icallBack = iBack;
    }
	
}
