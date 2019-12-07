package com.ankur.example.ui.home.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;
    private Application mApplication;


    public HomeViewModelFactory(Application application, Context context) {
        mContext = context;
        mApplication = application;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeListViewModel(mApplication, mContext);
    }

}
