package com.ankur.example.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ankur.example.R;
import com.ankur.example.common.BaseActivity;
import com.ankur.example.constant.AppConstant;
import com.ankur.example.model.MeetingListItem;
import com.ankur.example.model.MeetingResponse;
import com.ankur.example.network.ApiConstants;
import com.ankur.example.ui.home.adapter.LandScapeListAdapter;
import com.ankur.example.ui.home.adapter.PortraitListAdapter;
import com.ankur.example.ui.home.viewModel.HomeListViewModel;
import com.ankur.example.ui.home.viewModel.HomeViewModelFactory;
import com.ankur.example.ui.scheduleMeeting.ScheduleMeetingActivity;
import com.ankur.example.utill.CollectionUtils;
import com.ankur.example.utill.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.OnClick;

public class MeetingHOmeActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.scheduleMeetingBtn)
    AppCompatButton mScheduleMeetingBtn;
    private boolean mDoubleBackToExitPressedOnce;
    private Calendar mCurrentSelectedDate = Calendar.getInstance();
    private HomeListViewModel mViewModel;

    private ArrayList<MeetingListItem> mItems;
    private boolean mIsMeetingListLoaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_meeting_home);

        mViewModel = ViewModelProviders.of(this, new HomeViewModelFactory(getApplication(), this))
                .get(HomeListViewModel.class);

        showToolBarLeftButton("PREV");
        showToolBarRightButton("NEXT");
        initalizeObserver();
        checkForSavedInstanceState(savedInstanceState);

        setActivityTitle();
    }

    private void checkForSavedInstanceState(Bundle bundle) {
        if (bundle != null && bundle.getBoolean(AppConstant.BundleKeys.IS_MEETING_LIST_LOADED)) {
            long selectedTimeMiles = bundle.getLong(AppConstant.BundleKeys.CURRENT_DATE, Calendar.getInstance().getTimeInMillis());
            mCurrentSelectedDate.setTimeInMillis(selectedTimeMiles);
        }
    }


    private void initalizeObserver() {
        if (!mViewModel.getApiResponse().hasActiveObservers()) {
            mViewModel.getApiResponse().observe(this, response -> {
                hideLoading();
                handleApiResponse(response);

            });
        }
    }

    private void handleApiResponse(MeetingResponse response) {
        int status = response.getStatus();
        switch (status) {
            case ApiConstants
                    .SUCCESSFUL:
                mIsMeetingListLoaded = true;
                mItems = new ArrayList<>();
                mItems = response.getItems();
                Comparator<MeetingListItem> listItemComparator = (o1, o2) -> Double.valueOf(o1.getStartTime().replace(":","."))
                        .compareTo(Double.valueOf(o2.getStartTime().replace(":",".")));
                Collections.sort(mItems,listItemComparator);
                setListData();
                Log.d("anku78", "getSuccess First time");
                break;
            case ApiConstants
                    .FAILURE:
            case ApiConstants
                    .EMPTY:
                mIsMeetingListLoaded = true;
                String error = response.getMessage();
                mItems = new ArrayList<>();
                setListData();
                Toast.makeText(MeetingHOmeActivity.this, error, Toast.LENGTH_SHORT).show();
                Log.d("anku78", "Error  "+error);
                break;


        }

    }

    private void getDataFromServer() {
        if (mViewModel != null) {
            showLoading();
            String date = DateUtil.getStringFormatFor(AppConstant.API, mCurrentSelectedDate);
            mViewModel.getMeetingList(date);
        }
    }

    @Override
    public void onClickBackButton() {
        if (mDoubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.mDoubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.app_exit_message, Toast.LENGTH_SHORT).show();

        int DOUBLE_TAP_TIME = 2000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, DOUBLE_TAP_TIME);
    }

    @Override
    public void onClickToolbarLeftButton() {
        mCurrentSelectedDate = DateUtil.getPreviousDate(mCurrentSelectedDate);
        setActivityTitle();
        getDataFromServer();
    }

    @Override
    public void onClickToolbarRightButton() {
        mCurrentSelectedDate = DateUtil.getNextDate(mCurrentSelectedDate);
        setActivityTitle();
        getDataFromServer();

    }


    private void setActivityTitle() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setActivityTitle(DateUtil.getStringFormatFor(AppConstant.ORIENTATION_LANDSCAPE, mCurrentSelectedDate));
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setActivityTitle(DateUtil.getStringFormatFor(AppConstant.ORIENTATION_PORTRAIT, mCurrentSelectedDate));

        }
    }


    private void setListData() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LandScapeListAdapter adapter = new LandScapeListAdapter(this, mItems);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(adapter);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            PortraitListAdapter adapter = new PortraitListAdapter(this, mItems);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.scheduleMeetingBtn)
    public void onViewClicked() {
        Intent intent = new Intent(MeetingHOmeActivity.this, ScheduleMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("anku78", "on Destroy called");
        mViewModel.getApiResponse().removeObservers(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstant.BundleKeys.IS_MEETING_LIST_LOADED, mIsMeetingListLoaded);
        outState.putLong(AppConstant.BundleKeys.CURRENT_DATE, mCurrentSelectedDate.getTimeInMillis());


    }
}


