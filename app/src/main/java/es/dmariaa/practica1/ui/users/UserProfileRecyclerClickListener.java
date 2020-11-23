package es.dmariaa.practica1.ui.users;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserProfileRecyclerClickListener implements RecyclerView.OnItemTouchListener {
    public interface OnItemClickListener {
        public void OnItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private GestureDetector gestureDetector;

    public UserProfileRecyclerClickListener(Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        boolean singleTap = gestureDetector.onTouchEvent(e);
        if(childView != null && onItemClickListener != null && singleTap) {
            onItemClickListener.OnItemClick(childView, rv.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}
