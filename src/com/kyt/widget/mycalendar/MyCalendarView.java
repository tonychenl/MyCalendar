package com.kyt.widget.mycalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MyCalendarView extends RelativeLayout implements OnClickListener{
	private final SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM");
	private ImageButton lastMonthBtn,nextMonthBtn;
	private Button currentMonthBtn;
	private GridView calendar_grid;
	private List<CalendarCellView> gridlist;
	private static final int FULL_MONTH_WEEK = 42;
	private CalendarAdapter adapter;
	private Calendar  currentDate;
	private int currentDatePostion = -1;

	public MyCalendarView(Context context) {
		super(context);
		init(context);
	}
	
	public MyCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.my_calendar_view,this,true);
		lastMonthBtn = (ImageButton) findViewById(R.id.calendar_last_month);
		nextMonthBtn = (ImageButton) findViewById(R.id.calendar_next_month);
		currentMonthBtn = (Button) findViewById(R.id.calendar_current_month);
		calendar_grid = (GridView) findViewById(R.id.calendar_grid);
		initView(new Date());
		nextMonthBtn.setOnClickListener(this);
		lastMonthBtn.setOnClickListener(this);
		currentMonthBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v == lastMonthBtn){
			currentDate.set(Calendar.DAY_OF_MONTH, 1);
			currentDate.add(Calendar.MONTH, -1);
			initView(currentDate.getTime());
		}else if(v == nextMonthBtn){
			
		}else if(v == currentMonthBtn){
			
		}
	}
	
	public void initView(Date date){
		initDate(date);
		currentMonthBtn.setText(sdfm.format(date));
		
		adapter =  new CalendarAdapter();
		adapter.setSeclection(currentDatePostion);
		calendar_grid.setAdapter(adapter);
		calendar_grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int viewId,long postion) {
				adapter.setSeclection(viewId);
				adapter.notifyDataSetChanged();
			}
		});
	}
	

	private void initDate(Date date) {
		gridlist = new ArrayList<CalendarCellView>();
		Calendar calendar = Calendar.getInstance();
		this.currentDate = Calendar.getInstance();
		this.currentDate.setTime(date);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, -week);
		CalendarCellView view;
		for (int i = 0; i < FULL_MONTH_WEEK; i++) {
			calendar.add(Calendar.DATE, 1);
			view = new CalendarCellView(getContext(), false, calendar.getTime(),date);
			if(view.isCurrentDate())
				currentDatePostion = i;
			gridlist.add(view);
		}
	}
	
	class CalendarAdapter extends BaseAdapter{
		private int clickTemp = -1;
        //标识选择的Item
		public void setSeclection(int position) {
			clickTemp = position;
		}

		@Override
		public int getCount() {
			return FULL_MONTH_WEEK;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CalendarCellView cellView;
			if (convertView == null) { 
				cellView = gridlist.get(position);
			}else{
				cellView = (CalendarCellView) convertView;
			}
			if (clickTemp == position) {
				cellView.setBackgroundResource(R.drawable.rl_p_b);
			} else {
				cellView.setBackgroundResource(R.drawable.rl_p_a);
			}
			return cellView;
		}
		
	}

}
