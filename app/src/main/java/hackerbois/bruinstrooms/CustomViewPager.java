package hackerbois.bruinstrooms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Vince Wu on 3/14/2018.
 */

public class CustomViewPager extends ViewPager {
	private boolean enabled;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = false;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onTouchEvent(event);
		}

		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onInterceptTouchEvent(event);
		}

		return false;
	}

	/**
	 * Enable or disable the swipe navigation
	 * @param enabled
	 */
	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
