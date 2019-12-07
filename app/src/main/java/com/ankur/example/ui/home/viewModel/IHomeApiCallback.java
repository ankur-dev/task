package com.ankur.example.ui.home.viewModel;

import com.ankur.example.model.MeetingListItem;

import java.util.ArrayList;

public interface IHomeApiCallback {

    void onApiSuccess(ArrayList<MeetingListItem> items);

    void onApiFailure(String error);

    void noMeetingFound();
}

