package com.ankur.example.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by ankur.khandelwal on 08-08-2017.
 */

public class AppRobotoBoldTextView extends AppCompatTextView {
    public AppRobotoBoldTextView(Context context) {
        super(context);
        init(context);
    }

    public AppRobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public AppRobotoBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/roboto_bold.ttf");
            this.setTypeface(tf);
        }
    }
}
