package com.ankur.example.ui.home.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ankur.example.model.MeetingListItem;
import com.ankur.example.model.MeetingResponse;
import com.ankur.example.network.ApiConstants;
import com.ankur.example.network.repository.HomeRepository;

import java.util.ArrayList;

public class HomeListViewModel extends AndroidViewModel {

    private HomeRepository mRepository;
    private MutableLiveData<MeetingResponse> mApiResponse ;


    public HomeListViewModel(@NonNull Application application, Context context) {
        super(application);
        mRepository = new HomeRepository(context);
        mApiResponse = new MutableLiveData<>();
    }

    public void getMeetingList(String date) {
        MeetingResponse response = new MeetingResponse();
        mRepository.getMeetingList(date, new IHomeApiCallback() {
            @Override
            public void onApiSuccess(ArrayList<MeetingListItem> items) {
                response.setItems(items);
                response.setStatus(ApiConstants.SUCCESSFUL);
                mApiResponse.setValue(response);
            }

            @Override
            public void onApiFailure(String error) {
                response.setStatus(ApiConstants.FAILURE);
                response.setMessage(error);
                mApiResponse.setValue(response);
            }

            @Override
            public void noMeetingFound() {
                response.setStatus(ApiConstants.EMPTY);
                response.setMessage("No Meeting Found");
                mApiResponse.setValue(response);
            }
        });
    }

    public MutableLiveData<MeetingResponse> getApiResponse() {
        return mApiResponse;
    }
}
