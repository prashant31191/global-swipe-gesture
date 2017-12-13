package com.homegesture.gesture;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class GlobalTouchService extends Service implements OnTouchListener{

	private String TAG = this.getClass().getSimpleName();
	// window manager 
	private WindowManager mWindowManager;
	// linear layout will use to detect touch event
	private LinearLayout touchLayout;
	private GestureDetector mGestureDetector;
	static final int SWIPE_MIN_DISTANCE = 120;
	static final int SWIPE_MAX_OFF_PATH = 250;
	static final int SWIPE_THRESHOLD_VELOCITY = 200;
	Context mContex;
	int count = 0;



	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		// create linear layout
		mContex = getApplicationContext();
		touchLayout = new LinearLayout(this);
		touchLayout.setBackgroundColor(Color.parseColor("#DFDFDF"));
		// set layout width 30 px and height is equal to full screen
		LayoutParams lp = new LayoutParams(30, LayoutParams.MATCH_PARENT);
		touchLayout.setLayoutParams(lp);
		// set color if you want layout visible on screen
//		touchLayout.setBackgroundColor(Color.CYAN); 
		// set on touch listener
		touchLayout.setOnTouchListener(this);
		mGestureDetector = new GestureDetector(mContex, new GestureListener());

		// fetch window manager object 
		 mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		 // set layout parameter of window manager
		 WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
	        		30, // width of layout 30 px
	        		WindowManager.LayoutParams.MATCH_PARENT, // height is equal to full screen
	                WindowManager.LayoutParams.TYPE_PHONE, // Type Ohone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
	                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // this window won't ever get key input focus  
	                PixelFormat.TRANSLUCENT);      
         mParams.gravity = Gravity.RIGHT | Gravity.TOP;
 		Log.i(TAG, "add View");

	     mWindowManager.addView(touchLayout, mParams);
		
	}

	class GestureListener extends GestureDetector.SimpleOnGestureListener
	{

		String currentGestureDetected;

		// Override s all the callback methods of GestureDetector.SimpleOnGestureListener
		@Override
		public boolean onSingleTapUp(MotionEvent ev) {
			currentGestureDetected=ev.toString();

			return true;
		}
		@Override
		public void onShowPress(MotionEvent ev) {
			currentGestureDetected=ev.toString();

		}
		@Override
		public void onLongPress(MotionEvent ev) {
			currentGestureDetected=ev.toString();

		}
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			currentGestureDetected=e1.toString()+ "  "+e2.toString();

			return true;
		}
		@Override
		public boolean onDown(MotionEvent ev) {
			currentGestureDetected=ev.toString();

			return true;
		}
		@SuppressLint("WrongConstant")
		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//			currentGestureDetected=e1.toString()+ "  "+e2.toString();
//			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());
//			return true;
//		}
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
							   float velocityY) {

			Log.v("fling", "Flinged.");
//			final int position = lvCountry.pointToPosition(
//					Math.round(e1.getX()), Math.round(e1.getY()));

//			String countryName = (String) lvCountry.getItemAtPosition(position);

//			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
//				if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
//						|| Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
//					return false;
//				}
//				if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
//					Toast.makeText(mContex, "bottomToTop", 5000).show();
//				} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
//					Toast.makeText(mContex, "topToBottom  ", 5000).show();
//				}
//			} else {
//				if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
//					return false;
//				}
//				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
//					Toast.makeText(mContex, "swipe RightToLeft ", 5000).show();
//				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
//					Toast.makeText(mContex, "swipe LeftToright  ", 5000).show();
//				}
//			}

			return super.onFling(e1, e2, velocityX, velocityY);

		}

	}



	@Override
	public void onDestroy() {
		 if(mWindowManager != null) {
	            if(touchLayout != null) mWindowManager.removeView(touchLayout);
	        }
		super.onDestroy();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP)
			mGestureDetector.onTouchEvent(event);
			Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());
			count++;
			if (count == 20)
			{
				count = 0;
//				Intent intent =new Intent(mContex , LockScreenActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
			}
		
		return true;
	}
	
	

}
