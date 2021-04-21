package e99co.e99.integratedannotationtool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

public class CanvasView extends View {
    private Paint paint = new Paint();


    //여러가지의 그리기 명령을 모았다가 한번에 출력해주는
    //버퍼역할을 담당한다..
    private Path path = new Path();

    private int x,y;
    private int startX,startY,stopX,stopY;

    public CanvasView(Context context, AttributeSet attr){

        super(context,attr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.RED);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(50);
        //두께
        paint.setStrokeWidth(6);

        Log.i(TAG, "startX: "+startX+"startY: "+startY);
        canvas.drawRect((float)startX,(float)startY,(float)stopX,(float)stopY,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //x = (int) event.getX();
        //y = (int) event.getY();
//        startX=(int) event.getX();
//        startY=(int) event.getY();
//        stopX=(int) event.getX();
//        stopY=(int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //path.moveTo(x, y);
                startX=(int) event.getX();
                startY=(int) event.getY();
                //invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                //x = (int) event.getX();
                //y = (int) event.getY();
                stopX= (int) event.getX();
                stopY= (int) event.getY();
                invalidate();
                //path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }

        //View의 onDraw()를 호출하는 메소드...
        //invalidate();

        return true;
    }
}
