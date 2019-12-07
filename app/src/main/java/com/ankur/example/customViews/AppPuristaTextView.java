package com.ankur.example.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by ankur.khandelwal on 27-07-2017.
 */

public class AppPuristaTextView extends AutoResizeTextView {

    public AppPuristaTextView(Context context) {
        super(context);
        init(context);
    }

    public AppPuristaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public AppPuristaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/purista_semibold.otf");
            this.setTypeface(tf);
        }
    }
}
