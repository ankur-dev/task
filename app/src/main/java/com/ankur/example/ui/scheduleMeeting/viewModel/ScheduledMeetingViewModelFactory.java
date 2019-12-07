package com.ankur.example.ui.scheduleMeeting.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ScheduledMeetingViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;
    private Application mApplication;


    public ScheduledMeetingViewModelFactory(Application application, Context context) {
        mContext = context;
        mApplication = application;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ScheduledMeetingViewModel(mApplication, mContext);
    }

}
