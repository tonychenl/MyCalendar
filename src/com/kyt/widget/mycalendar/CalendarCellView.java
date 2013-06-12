package com.kyt.widget.mycalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.widget.TextView;

public class CalendarCellView extends TextView{
	private static Bitmap tagBitmap;
	private boolean hasTag = false;
	private boolean isCurrentDate = false;
	private boolean isCurrentMonth = false;
	private boolean isWeekEnd = false;
	private Calendar date;
	private SpannableString msp = null;
	private String dateStr;

	public CalendarCellView(Context context, boolean hasTag, Date d,Date month) {
		super(context);
		this.hasTag = hasTag;
		this.date = Calendar.getInstance();
		this.date.setTime(d);
		
		setGravity(Gravity.CENTER);
		setTextSize(30);
		setTextColor(Color.rgb(0x3b,0x3b,0x39));
		dateStr = String.valueOf(this.date.get(Calendar.DAY_OF_MONTH));
		msp = new SpannableString(dateStr);
		msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,dateStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体
		setText(msp);
		
		SimpleDateFormat spfd = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat spfm = new SimpleDateFormat("yyyyMM");
		if(spfd.format(new Date()).equals(spfd.format(date.getTime()))){
			isCurrentDate = true; //是否是今天
		}
		if(spfm.format(month).equals(spfm.format(date.getTime()))){
			isCurrentMonth = true;//是否是当月
		}
		int dayOfWeek = this.date.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
			isWeekEnd = true; //是否是周末
		
		if(isWeekEnd)
			setTextColor(Color.rgb(0xd0, 0x20, 0x20));
		
		if(isCurrentDate)
			setSelected(true);
		
		if(!isCurrentMonth)
			setTextColor(Color.GRAY);
		
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(tagBitmap == null){
			tagBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.rl_tag);
		}
		if(hasTag)
			canvas.drawBitmap(tagBitmap, 0, 0,null);
	}

	public boolean isHasTag() {
		return hasTag;
	}

	public Date getDate() {
		return date.getTime();
	}

	public boolean isCurrentDate() {
		return isCurrentDate;
	}

	public boolean isCurrentMonth() {
		return isCurrentMonth;
	}

	public boolean isWeekEnd() {
		return isWeekEnd;
	}
}
