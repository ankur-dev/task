package com.ankur.example.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/*
 * Created by ankur.khandelwal on 31-07-2017.
 */

public class MyRaidProBoldButton extends AppCompatButton {

    public MyRaidProBoldButton(Context context) {
        super(context);
        init(context);
    }

    public MyRaidProBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MyRaidProBoldButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/myriad_pro_bold.otf");
            this.setTypeface(tf);
        }
    }
}
