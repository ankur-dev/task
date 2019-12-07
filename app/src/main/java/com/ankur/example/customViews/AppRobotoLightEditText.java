package com.ankur.example.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;

/*
 * Created by ankur.khandelwal on 31-07-2017.
 */

public class AppRobotoLightEditText extends AppCompatEditText {

    public AppRobotoLightEditText(Context context) {
        super(context);
        init(context);
    }

    public AppRobotoLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public AppRobotoLightEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/roboto_light.ttf");
            this.setTypeface(tf);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int getAutofillType() {
        return AUTOFILL_TYPE_NONE;
    }
}
