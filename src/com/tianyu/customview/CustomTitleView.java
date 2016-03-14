package com.tianyu.customview;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CustomTitleView extends View {

	// 字体标题
	private String mTxtTitle;
	// 字体颜色
	private int mTxtTitleColor;
	// 字体大小
	private int mTxtTitleSize;
	// 绘制时控制文本绘制的大小
	private final Rect mBound;
	// 画笔
	private final Paint mPaint;

	/**
	 * 获得自定义样式属性
	 * 
	 * @param context
	 */
	public CustomTitleView(Context context) {
		this(context, null);
	}

	/**
	 * 获得自定义样式属性
	 * 
	 * @param context
	 */
	public CustomTitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	/**
	 * 获得自定义样式属性
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */

	public CustomTitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		/*
		 * 获得自定义样式属性
		 */
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CustomTitleView, defStyle, 0);
		// 下标数量
		int dst = array.getIndexCount();
		for (int i = 0; i < dst; i++) {

			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.CustomTitleView_textTitle:// 标题字体

				mTxtTitle = array.getString(attr);

				break;
			case R.styleable.CustomTitleView_textTitleColor:// 字体颜色
				// 默认颜色设为黑色，第二个参数为默认值
				mTxtTitleColor = array.getColor(attr, Color.BLACK);

				break;
			case R.styleable.CustomTitleView_textTitleSize:// 字体大小
				// 默认设置为16sp，TypeValue也可以把sp转化为px
				mTxtTitleSize = array.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_SP, 16, getResources()
										.getDisplayMetrics()));

				break;
			default:
				break;
			}

		}
		array.recycle();
		/**
		 * 获取绘制文本的宽和高
		 */
		mPaint = new Paint();
		mPaint.setTextSize(mTxtTitleSize);
		mBound = new Rect();
		mPaint.getTextBounds(mTxtTitle, 0, mTxtTitle.length(), mBound);

		// 点击事件
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTxtTitle = randomText();
				postInvalidate();
			}

		});
	}

	/**
	 * 测量 (三种类型)<br>
	 * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT <br>
	 * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT<br>
	 * UNSPECIFIED：表示子布局想要多大就多大，很少使用
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 宽
		int width = 0;
		// 高
		int height = 0;

		// 宽的模式
		int widthModel = MeasureSpec.getMode(widthMeasureSpec);
		// 宽度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		switch (widthModel) {
		case MeasureSpec.EXACTLY:// 一般明确指定了值或者是match_parent

			width = getPaddingLeft() + getPaddingRight() + widthSize;
			break;
		case MeasureSpec.AT_MOST:// 表示子布局限制在一个最大值内，一般为WARP_CONTENT

			width = getPaddingLeft() + getPaddingRight() + mBound.width();
			break;
		}

		// 高的模式
		int heightModel = MeasureSpec.getMode(heightMeasureSpec);
		// 高度
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		switch (heightModel) {
		case MeasureSpec.EXACTLY:// 一般明确指定了值或者是match_parent
			height = getPaddingTop() + getPaddingBottom() + heightSize;
			break;

		case MeasureSpec.AT_MOST:// 表示子布局限制在一个最大值内，一般为WARP_CONTENT
			height = getPaddingTop() + getPaddingBottom() + mBound.height();

			break;
		}

		setMeasuredDimension(width, height);
	}

	/**
	 * 画图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPaint.setColor(Color.GREEN);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

		mPaint.setColor(mTxtTitleColor);
		canvas.drawText(mTxtTitle, getWidth() / 2 - mBound.width() / 2,
				getHeight() / 2 + mBound.height() / 2, mPaint);
	}

	/**
	 * 随机数
	 * 
	 * @return
	 */
	private String randomText() {
		Random random = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < 4) {
			int randomInt = random.nextInt(10);
			set.add(randomInt);
		}
		StringBuffer sb = new StringBuffer();
		for (Integer i : set) {
			sb.append("" + i);
		}

		return sb.toString();
	}

}
