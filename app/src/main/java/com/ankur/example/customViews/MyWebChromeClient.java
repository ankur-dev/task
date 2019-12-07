package com.ankur.example.customViews;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/*
 * Created by ankur.khandelwal on 04-09-2018.
 */
public class MyWebChromeClient extends WebChromeClient {
    private ProgressListener mListener;

    public MyWebChromeClient(ProgressListener listener) {
        mListener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mListener.onUpdateProgress(newProgress);
        super.onProgressChanged(view, newProgress);
    }

    public interface ProgressListener {
        void onUpdateProgress(int progressValue);
    }

}
