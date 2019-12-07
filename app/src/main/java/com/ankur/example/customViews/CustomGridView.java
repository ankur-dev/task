package com.ankur.example.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


/**
 * Created by ankur.khandelwal on 17-08-2017.
 */

public class CustomGridView extends GridView {

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}