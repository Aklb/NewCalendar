package com.example.newcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewCalendar extends LinearLayout {

    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView gridView;

    private Calendar curDate=Calendar.getInstance();
    private String displayFormat;

    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public NewCalendar(Context context,@Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
    }

    //绑定事件
    private void initControl(Context context,AttributeSet attrs){
        bindControl(context);
        bindControlEvent(context);

        TypedArray typedArray=getContext().obtainStyledAttributes(attrs,R.styleable.NewCalendar);

        try {
            String format=typedArray.getString(R.styleable.NewCalendar_dataFormat);

            displayFormat=format;
            if (displayFormat==null){
                displayFormat="MMM yyyy";
            }
        }finally {
            typedArray.recycle();
        }
        renderCalender();
    }

    //绑定控件
    private void bindControl(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view,this);

        btnPrev=(ImageView)findViewById(R.id.btnPrev);
        btnNext=(ImageView)findViewById(R.id.btnNext);
        txtDate=(TextView)findViewById(R.id.txtDate);
        gridView=(GridView)findViewById(R.id.calendar_grid);
    }

    private void bindControlEvent(Context context){
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH,-1);
                renderCalender();
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH,+1);
                renderCalender();
            }
        });
    }

    //声明渲染
    private void  renderCalender(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(displayFormat);/*"MMM yyyy dd",格式化日期,使用 MMM，则会
        根据语言环境显示不同语言的月份*/
        txtDate.setText(simpleDateFormat.format(curDate.getTime()));

        ArrayList<Date> cells=new ArrayList<>();
        Calendar calendar=(Calendar) curDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays=calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);

        int maxCellCount=6*7;
        while (cells.size()<maxCellCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        gridView.setAdapter(new CalendarAdapter(getContext(),cells));
    }

    private class CalendarAdapter extends ArrayAdapter<Date>{
        LayoutInflater inflater;
        public CalendarAdapter(Context context,ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day,days);
            inflater=LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date=getItem(position);
            if (convertView==null){
                convertView=inflater.inflate(R.layout.calendar_text_day,parent,false);
            }

            int day=date.getDate();
            ( (TextView)convertView).setText(String.valueOf(day));

           /* Calendar calendar=(Calendar) curDate.clone();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            int daysInMonth=calendar.getActualMaximum(Calendar.DATE);*/
            Date now=new Date();
            boolean isTheSameMonth=false;
            if (date.getMonth()==now.getMonth()){
                isTheSameMonth=true;
            }
            if(isTheSameMonth){
                ( (TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else {
                ( (TextView)convertView).setTextColor(Color.parseColor("#FFBDBDBD"));
            }


            if(now.getDate()==date.getDate()&&now.getMonth()==date.getMonth()&&now.getYear()==date.getYear()){
                ( (TextView)convertView).setTextColor(Color.parseColor("#FFF44336"));
                ( (Calender_day_textView)convertView).isToday=true;
            }

            return convertView;
        }
    }

    public interface NewCalendarListener{
        void onItemLongPress(Date date);
    }
}
