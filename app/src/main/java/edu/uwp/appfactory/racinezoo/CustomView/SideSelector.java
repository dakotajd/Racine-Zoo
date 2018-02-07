package edu.uwp.appfactory.racinezoo.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;

import edu.uwp.appfactory.racinezoo.Util.Config;

/**
 * Created by hanh on 2/12/17.
 */

public class SideSelector extends View {

    private Paint paint;
    private ListView listView;
    private SectionIndexer sectionIndexer;

    public SideSelector(Context context) {
        super(context);
        init();
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setColor(0xFF7A6A3D);
        paint.setTextSize(45);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setListView(ListView listView){
        this.listView = listView;
        sectionIndexer = (SectionIndexer)listView.getAdapter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int y = (int) event.getY();
        float selectedIndex = ((float) y / (float) getPaddedHeight()) * Config.ALPHABET.length;

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {

            int position = sectionIndexer.getPositionForSection((int) selectedIndex);
            if (position == -1) {
                return true;
            }

            listView.setSelection(position);
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {

        int viewHeight = getPaddedHeight();
        float charHeight = ((float) viewHeight) / (float) Config.ALPHABET.length;
        float widthCenter = getMeasuredWidth() / 2;
        for (int i = 0; i < Config.ALPHABET.length; i++) {
            canvas.drawText(String.valueOf(Config.ALPHABET[i]), widthCenter, charHeight + (i * charHeight), paint);
        }
        super.onDraw(canvas);
    }

    private int getPaddedHeight() {
        return getHeight()-getPaddingBottom()-getPaddingTop();
    }
}
