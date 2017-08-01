package com.hbh.cl.listviewhorizontalscrolldemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

/**
 * Created by Tom Chen on 2017/8/1 0001.
 */

public class MyListView extends ListView {

    private HorizontalScrollView horizontalScrollView;
    private boolean isSliding;
    private int moveDelataY;
    /**     * 速度追踪对象     */
    private VelocityTracker velocityTracker;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalScrollView getHorizontalScrollView() {
        return horizontalScrollView;
    }

    public void setHorizontalScrollView(HorizontalScrollView horizontalScrollView) {
        this.horizontalScrollView = horizontalScrollView;
    }

    int downX, downY, moveX, moveY, lastX, touchSlop;
    boolean isSlid;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                lastX = downX;
                downY = y;
//                horizontalScrollView.onTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = x;
                moveY = y;
                int dx = moveX - downX;
                int dy = moveY - downY;

                if (Math.abs(dx) >  Math.abs(dy) && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop  ) {
                    isSliding = true;
                }

//                if (delaX > delaY && delaY > 0) {
//                    float distance =  (downX - ev.getX());
//                    Log.e("test",distance+"");


//                     horizontalScrollView.scrollTo((int)(horizontalScrollView.getScrollY()+ distance),0);
//                    return true;
//                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
//        downY = ev.getY();
//        downX = ev.getX();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSliding) {
            requestDisallowInterceptTouchEvent(true);
            final int action = ev.getAction();
            int x = (int) ev.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                    int deltaX = downX - x;
                    downX = x;
                    // 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
                    horizontalScrollView.scrollBy(deltaX,0);

                    return true;
//                         拖动的时候ListView不滚动
                case MotionEvent.ACTION_UP:

//                    int velocityX = getScrollVelocity();
//                    if (velocityX > SNAP_VELOCITY) {
//                        scrollRight();
//                    } else if (velocityX < -SNAP_VELOCITY) {
//                        scrollLeft();
//                    } else {
//                        scrollByDistanceX();
//                    }
//                    recycleVelocityTracker();
//                         手指离开的时候就不响应左右滚动
                    isSliding = false;
                    break;
            }
        }


        return super.onTouchEvent(ev);
    }

    /**     * 添加用户的速度跟踪器     *     * @param event     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }
    /**     * 移除用户速度跟踪器     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

}
