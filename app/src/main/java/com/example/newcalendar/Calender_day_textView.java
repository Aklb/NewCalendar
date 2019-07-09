package com.example.newcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class Calender_day_textView extends AppCompatTextView {

    public boolean isToday=false;
    private Paint paint=new Paint();

    public Calender_day_textView(Context context) {
        super(context);
    }

    public Calender_day_textView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ininControl();
    }

    public Calender_day_textView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininControl();
    }

    private void ininControl(){
       paint.setStyle(Paint.Style.STROKE);
       paint.setColor(Color.parseColor("#FF44336"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }
    }
}
