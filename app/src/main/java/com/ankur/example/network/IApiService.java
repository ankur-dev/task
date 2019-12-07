package com.ankur.example.network;

import com.ankur.example.model.MeetingListItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface IApiService {

    //    // get meeting list for given date api request
    @GET(UrlConstant.GET_MEETING_LIST)
    Call<ArrayList<MeetingListItem>> doLoginOnServer(@Query(value = "date",encoded = true) String date);

}
