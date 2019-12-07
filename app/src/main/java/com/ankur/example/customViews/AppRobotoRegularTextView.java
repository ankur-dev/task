package com.ankur.example.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/*
 * Created by ankur.khandelwal on 27-07-2017.
 */

public class AppRobotoRegularTextView extends AppCompatTextView {
    public AppRobotoRegularTextView(Context context) {
        super(context);
        init(context);

    }

    public AppRobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public AppRobotoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/roboto_regular.ttf");
            this.setTypeface(tf);
        }
    }
}
