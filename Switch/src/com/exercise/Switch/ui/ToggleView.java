package com.exercise.Switch.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleView extends View {

	private Bitmap switch_background;
	private boolean attributeBooleanValue;
	private Bitmap slide_button;
	private Paint paint;
	private float countX;
	private boolean isTouchMode;
	private OnSwitchStateUpdateListener switchStateUpdateListener;
	private OnSwitchStateUpdateListener SwitchStateUpdateListener ;

	public ToggleView(Context context) {
		super(context);
		initPaint();

	}

	private void initPaint() {
		paint = new Paint();

	}

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
		//命名空间路径
		String nameSpace = "http://schemas.android.com/apk/res/com.exercise.Switch";
		//通过属性值获取相应的资源ID
		int attributeResourceValue = attrs.getAttributeResourceValue(nameSpace,
				"switch_background", -1);
		int attributeResourceValue2 = attrs.getAttributeResourceValue(
				nameSpace, "slide_button", -1);
		attributeBooleanValue = attrs.getAttributeBooleanValue(nameSpace,
				"switch_state", false);
		setSwitchBackgroundResource(attributeResourceValue);
		setSlideButtonResource(attributeResourceValue2);
		setSwitchState(attributeBooleanValue);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 設置自己自定的view的寬高
		setMeasuredDimension(switch_background.getWidth(),
				switch_background.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//绘画背景图片
		canvas.drawBitmap(switch_background, 0, 0, paint);
          
		//根据触摸事件进行绘画
		if (isTouchMode) {
                   
			  //滑动距离超过开关按钮的一半时进行状态的切换
			  float newLen =countX- slide_button.getWidth()/2.0f;
			  //设置能滑动的最大距离
			   float maxLen=switch_background.getWidth()
						- slide_button.getWidth();
			// 限定滑块范围
				if(newLen < 0){
					newLen = 0; // 左边范围
				}else if (newLen > maxLen) {
					newLen = maxLen; // 右边范围
				}
			   
				//绘画开关
				canvas.drawBitmap(slide_button, newLen, 0, paint);

			
		} else {
			// 根据開關狀態进行开关按钮绘画
			if (attributeBooleanValue) {
				
				float newlen = switch_background.getWidth()
						- slide_button.getWidth();
				
				canvas.drawBitmap(slide_button, newlen, 0, paint);
			} else {
				
				canvas.drawBitmap(slide_button, 0, 0, paint);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTouchMode = true;
			countX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			countX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			isTouchMode = false;
			countX = event.getX();
			/*
			 * 获取背景图的中间位置，与滑动的距离进行比较，与当前开关状态进行对比，不同时回调开关状态的相反状态
			 */
			float center =switch_background.getWidth()/2.0f;
			
			boolean stat =countX>center;
			 
			 if(stat !=attributeBooleanValue && SwitchStateUpdateListener!=null){
				 SwitchStateUpdateListener.SwitchStateUpdateListener(stat);
			 }
			 attributeBooleanValue =stat;
			break;
		default:
			break;

		}
		
		// 被調用時重新執行ondraw
		invalidate();
		return true;
	}

	/**
	 * 初始化开关状态
	 * @param attributeBooleanValue
	 */
	private void setSwitchState(boolean attributeBooleanValue) {
		this.attributeBooleanValue = attributeBooleanValue;

	}

	 /**
	  * 设置开关按钮的图片资源
	  * @param attributeResourceValue2
	  */
	private void setSlideButtonResource(int attributeResourceValue2) {
		slide_button = BitmapFactory.decodeResource(getResources(),
				attributeResourceValue2);

	}
   /**
    * 设置背景图片资源
    * @param attributeResourceValue
    */
	private void setSwitchBackgroundResource(int attributeResourceValue) {

		switch_background = BitmapFactory.decodeResource(getResources(),
				attributeResourceValue);

	}

	public ToggleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
   
	public interface OnSwitchStateUpdateListener{
    	void SwitchStateUpdateListener(boolean state);
    }

   public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener SwitchStateUpdateListener){
	
	    this.switchStateUpdateListener = SwitchStateUpdateListener;
	   
   }
}
