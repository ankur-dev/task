package com.ankur.example.ui.scheduleMeeting.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ankur.example.model.MeetingListItem;
import com.ankur.example.network.repository.HomeRepository;
import com.ankur.example.ui.home.viewModel.IHomeApiCallback;

import java.util.ArrayList;

public class ScheduledMeetingViewModel extends AndroidViewModel {

    private HomeRepository mRepository;

    private MutableLiveData<ArrayList<MeetingListItem>> mApiSuccess = new MutableLiveData<>();
    private MutableLiveData<String> mApiError = new MutableLiveData<>();
    private MutableLiveData<String> mEmptyList = new MutableLiveData<>();


    public ScheduledMeetingViewModel(@NonNull Application application, Context context) {
        super(application);
        mRepository = new HomeRepository(context);
    }

    public void getMeetingList(String date) {
        mRepository.getMeetingList(date, new IHomeApiCallback() {
            @Override
            public void onApiSuccess(ArrayList<MeetingListItem> items) {
                mApiSuccess.setValue(items);
            }

            @Override
            public void onApiFailure(String error) {
                mApiError.setValue(error);
            }

            @Override
            public void noMeetingFound() {
                mEmptyList.setValue("No Meeting Found");
            }
        });
    }

    public MutableLiveData<ArrayList<MeetingListItem>> getApiSuccess() {
        return mApiSuccess;
    }

    public MutableLiveData<String> getApiError() {
        return mApiError;
    }

    public MutableLiveData<String> getEmptyList() {
        return mEmptyList;
    }
}
