package tw.org.iii.appps.brad08;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines, recycler;
    private Paint paint;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.GREEN);

        lines = new LinkedList<>();
        recycler = new LinkedList<>();

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);


//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.v("brad", "debug2");
//            }
//        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (LinkedList<HashMap<String,Float>> line : lines) {
            for (int i = 1; i < line.size(); i++) {
                HashMap<String, Float> p0 = line.get(i - 1);
                HashMap<String, Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"), p0.get("y"),
                        p1.get("x"), p1.get("y"), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        HashMap<String,Float> point = new HashMap<>();
        point.put("x", event.getX()); point.put("y", event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            recycler.clear();
            LinkedList<HashMap<String,Float>> line = new LinkedList<>();
            line.add(point);
            lines.add(line);
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            lines.getLast().add(point);
        }

        invalidate();   // repaint

        return true; //super.onTouchEvent(event);
    }

    public void clearView(){
        lines.clear();
        invalidate();
    }

    public void undo(){
        if (lines.size()>0){
            recycler.add(lines.removeLast());
            invalidate();
        }
    }

    public void redo(){
        if (recycler.size()>0){
            lines.add(recycler.removeLast());
            invalidate();
        }
    }

}






