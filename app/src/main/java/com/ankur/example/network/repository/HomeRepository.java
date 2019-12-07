package com.ankur.example.network.repository;

import android.content.Context;
import android.text.TextUtils;

import com.ankur.example.R;
import com.ankur.example.model.MeetingListItem;
import com.ankur.example.network.ApiRestClient;
import com.ankur.example.network.IApiService;
import com.ankur.example.network.UrlConstant;
import com.ankur.example.ui.home.viewModel.IHomeApiCallback;
import com.ankur.example.utill.CollectionUtils;
import com.ankur.example.utill.NetworkUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private Context mContext;

    public HomeRepository(Context context) {
        mContext = context;
    }

    public void getMeetingList(String date, IHomeApiCallback callback) {
        if (!TextUtils.isEmpty(date)) {
            if (NetworkUtils.isNetworkAvailable(mContext)) {
                IApiService iApiService = ApiRestClient.getApiService(IApiService.class, UrlConstant.BASE_URL);
                Call<ArrayList<MeetingListItem>> call = iApiService.doLoginOnServer(date);
                call.enqueue(new Callback<ArrayList<MeetingListItem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MeetingListItem>> call, Response<ArrayList<MeetingListItem>> response) {
                        if (response.body() == null) {
                            callback.onApiFailure(mContext.getString(R.string.genric_error));
                        } else {
                            ArrayList<MeetingListItem> items = response.body();
                            if (CollectionUtils.isEmpty(items)) {
                                callback.noMeetingFound();
                            } else {
                                callback.onApiSuccess(items);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<MeetingListItem>> call, Throwable t) {
                        callback.onApiFailure(mContext.getString(R.string.genric_error));
                    }
                });


            } else {
                callback.onApiFailure(mContext.getString(R.string.no_connection));

            }

        } else {
            callback.onApiFailure(mContext.getString(R.string.genric_error));
        }
    }

}
