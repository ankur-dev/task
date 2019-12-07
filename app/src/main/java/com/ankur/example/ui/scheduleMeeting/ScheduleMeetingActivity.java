package com.ankur.example.ui.scheduleMeeting;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import com.ankur.example.R;
import com.ankur.example.common.BaseActivity;
import com.ankur.example.constant.AppConstant;
import com.ankur.example.customViews.AppRobotoBoldTextView;
import com.ankur.example.model.MeetingListItem;
import com.ankur.example.ui.scheduleMeeting.viewModel.ScheduledMeetingViewModel;
import com.ankur.example.ui.scheduleMeeting.viewModel.ScheduledMeetingViewModelFactory;
import com.ankur.example.utill.DateUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class ScheduleMeetingActivity extends BaseActivity {
    @BindView(R.id.dateBtn)
    AppRobotoBoldTextView mDateBtn;
    @BindView(R.id.startTimeBtn)
    AppRobotoBoldTextView mStartTimeBtn;
    @BindView(R.id.endTimeBtn)
    AppRobotoBoldTextView mEndTimeBtn;
    @BindView(R.id.descriptionEt)
    AppCompatEditText mDescriptionEt;
    @BindView(R.id.scheduleMeetingBtn)
    AppCompatButton mScheduleMeetingBtn;
    @BindView(R.id.nestedScrollView2)
    NestedScrollView nestedScrollView2;
    @BindView(R.id.avalibility)
    AppCompatTextView mAvalibility;
    private ScheduledMeetingViewModel mViewModel;
    private Calendar mCurrentSelectedDate = Calendar.getInstance();
    private Calendar mMeetingEndTime = Calendar.getInstance();
    private Calendar mMeetingStartTime = Calendar.getInstance();

    private String mSelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_schedule_meeting);

        mViewModel = ViewModelProviders.of(this, new ScheduledMeetingViewModelFactory(getApplication(),
                this)).get(ScheduledMeetingViewModel.class);

        mAvalibility = findViewById(R.id.avalibility);

        showToolBarLeftButton("BACK");
        setActivityTitle("SCHEDULE A MEETING");
        initalizeObserver();

        checkForSaveInstanceData(savedInstanceState);
    }

    private void checkForSaveInstanceData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(AppConstant.BundleKeys.IS_DATE_SELECTED)) {
                long currentDateTime = savedInstanceState.getLong(AppConstant.BundleKeys.CURRENT_DATE);
                mCurrentSelectedDate.setTimeInMillis(currentDateTime);
                String date = getDateFormat(mCurrentSelectedDate);
                mDateBtn.setText(date);
                mSelectedDate = DateUtil.getStringFormatFor(3, mCurrentSelectedDate);
            }


            if (savedInstanceState.getBoolean(AppConstant.BundleKeys.IS_START_TIME_SELECTED)) {
                long currentDateTime = savedInstanceState.getLong(AppConstant.BundleKeys.START_TIME);
                mMeetingStartTime.setTimeInMillis(currentDateTime);
                String date = getTimeFormat(mMeetingStartTime);
                mStartTimeBtn.setText(date);
            }

            if (savedInstanceState.getBoolean(AppConstant.BundleKeys.IS_END_TIME_SELECTED)) {
                long currentDateTime = savedInstanceState.getLong(AppConstant.BundleKeys.END_TIME);
                mMeetingEndTime.setTimeInMillis(currentDateTime);
                String date = getTimeFormat(mMeetingEndTime);
                mEndTimeBtn.setText(date);
            }

            if (!TextUtils.isEmpty(savedInstanceState.getString(AppConstant.BundleKeys.DESCRIPTION))) {
                mDescriptionEt.setText(savedInstanceState.getString(AppConstant.BundleKeys.DESCRIPTION));
            }
        }
    }

    @Override
    public void onClickBackButton() {
        finish();
    }

    @Override
    public void onClickToolbarLeftButton() {
    }

    @Override
    public void onClickToolbarRightButton() {
    }

    private void initalizeObserver() {
        mViewModel.getApiSuccess().observe(this, meetingListItems -> {
            hideLoading();
            checkAvailabilityForGivenTime(meetingListItems);
        });

        mViewModel.getApiError().observe(this, s -> hideLoading());

        mViewModel.getEmptyList().observe(this, s -> hideLoading());
    }

    private void checkAvailabilityForGivenTime(ArrayList<MeetingListItem> meetingListItems) {
        boolean isAllSlotFull = false;
        boolean isSlotAvailable = false;
        Comparator<MeetingListItem> listItemComparator = new Comparator<MeetingListItem>() {
            @Override
            public int compare(MeetingListItem o1, MeetingListItem o2) {
                return Double.valueOf(o1.getStartTime().replace(":", "."))
                        .compareTo(Double.valueOf(o2.getStartTime().replace(":", ".")));
            }
        };
        Collections.sort(meetingListItems, listItemComparator);

        for (MeetingListItem item : meetingListItems) {
            double scheduleStartTimeFloat = Double.valueOf(item.getStartTime().replace(":", "."));
            double scheduleEndTimeFloat = Double.valueOf(item.getEndTime().replace(":", "."));

            String startTimeString = getTimeFormatIn24Hour(mMeetingStartTime);
            String endTimeString = getTimeFormatIn24Hour(mMeetingEndTime);

            double startMeetingTimeFloat = Double.valueOf(startTimeString.replace(":", "."));
            double endMeetingTimeFloat = Double.valueOf(endTimeString.replace(":", "."));

            if (startMeetingTimeFloat < scheduleEndTimeFloat) {
                if (endMeetingTimeFloat > scheduleStartTimeFloat) {
                    isSlotAvailable = false;
                    break;
                } else {
                    isSlotAvailable = true;
                    break;
                }
            } else if (startMeetingTimeFloat != scheduleEndTimeFloat) {
                isSlotAvailable = true;
            }
        }
        if (isSlotAvailable) {
            Toast.makeText(ScheduleMeetingActivity.this, getString(R.string.slot_available), Toast.LENGTH_SHORT).show();
            mAvalibility.setText(getString(R.string.slot_available));
        } else {
            mAvalibility.setText(getString(R.string.slot_not_available));
            Toast.makeText(ScheduleMeetingActivity.this, getString(R.string.slot_not_available), Toast.LENGTH_SHORT).show();
        }

    }

    private void getDataFromServer(String mSelectedDate) {
        if (mViewModel != null) {
            showLoading();
            mViewModel.getMeetingList(mSelectedDate);
        }
    }


    @OnClick({R.id.dateBtn, R.id.startTimeBtn, R.id.endTimeBtn, R.id.scheduleMeetingBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dateBtn:
                openDatePicker();
                break;
            case R.id.startTimeBtn:
                if (!TextUtils.isEmpty(mDateBtn.getText())) {
                    openStartTimePicker();
                } else {
                    mStartTimeBtn.setError("First Select Date");
                    Toast.makeText(ScheduleMeetingActivity.this, "First Select Date", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.endTimeBtn:
                if (!TextUtils.isEmpty(mStartTimeBtn.getText())) {
                    openEndTimePicker();
                } else {
                    mStartTimeBtn.setError("First Select Start Time");
                    Toast.makeText(ScheduleMeetingActivity.this, "First Select Start Time", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.scheduleMeetingBtn:
                if (valid()) {
                    checkForMeeting();
                }
                break;
        }
    }

    private void checkForMeeting() {
        getDataFromServer(mSelectedDate);
    }

    private boolean valid() {
        if (TextUtils.isEmpty(mDateBtn.getText())) {
            mDateBtn.setError("Required");
            Toast.makeText(ScheduleMeetingActivity.this, "Select Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mStartTimeBtn.getText())) {
            mDateBtn.setError("Required");
            Toast.makeText(ScheduleMeetingActivity.this, "Select Start Time", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(mEndTimeBtn.getText())) {
            mDateBtn.setError("Required");
            Toast.makeText(ScheduleMeetingActivity.this, "Select End Time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mDescriptionEt.getText().toString().trim())) {
            mDateBtn.setError("Required");
            Toast.makeText(ScheduleMeetingActivity.this, "Enter Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void openEndTimePicker() {
        try {
            int hour;
            int min1;
            hour = mMeetingEndTime.get(Calendar.HOUR_OF_DAY);
            min1 = mMeetingEndTime.get(Calendar.MINUTE);
            TimePickerDialog tpd = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
                mMeetingEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mMeetingEndTime.set(Calendar.MINUTE, minute);
                mMeetingEndTime.set(Calendar.SECOND, 0);
                mEndTimeBtn.setText(getTimeIn12HourFormat(hourOfDay + ":" + minute));
            }, hour, min1, 0, false);

            tpd.setTimeInterval(1, 30, 1);
            tpd.setVersion(TimePickerDialog.Version.VERSION_1);
            tpd.setMinTime(mMeetingStartTime.get(Calendar.HOUR), mMeetingStartTime.get(Calendar.MINUTE) + 30, 0);
            tpd.setAccentColor(ContextCompat.getColor(ScheduleMeetingActivity.this, R.color.mdtp_accent_color));
            tpd.show(getSupportFragmentManager(), "Timepickerdialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openStartTimePicker() {
        try {
            int hour;
            int min1;
            hour = mCurrentSelectedDate.get(Calendar.HOUR_OF_DAY) + 1;
            min1 = 0;
            TimePickerDialog tpd = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
                mMeetingStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mMeetingStartTime.set(Calendar.MINUTE, minute);
                mMeetingStartTime.set(Calendar.SECOND, 0);
                mMeetingStartTime.set(Calendar.MILLISECOND, 0);
                mStartTimeBtn.setText(getTimeIn12HourFormat(hourOfDay + ":" + minute));
                mMeetingEndTime.setTimeInMillis(mMeetingStartTime.getTimeInMillis() + 30 * 60 * 1000);
                mEndTimeBtn.setText("");
            }, hour, min1, 0, false);

            tpd.setTimeInterval(1, 30, 1);
            tpd.setVersion(TimePickerDialog.Version.VERSION_1);
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH);
            int selectedMonth = mCurrentSelectedDate.get(Calendar.MONTH);
            int selectedDay = mCurrentSelectedDate.get(Calendar.DAY_OF_MONTH);
            if (!(selectedMonth > currentMonth) && selectedDay <= currentDay) {
                tpd.setMinTime(hour, 0, 0);
            }
            tpd.setAccentColor(ContextCompat.getColor(ScheduleMeetingActivity.this, R.color.mdtp_accent_color));
            tpd.show(getSupportFragmentManager(), "Timepickerdialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDatePicker() {
        try {

            DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
                mSelectedDate = String.format(Locale.ENGLISH, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year);
                mDateBtn.setText(mSelectedDate);
                mCurrentSelectedDate.set(Calendar.YEAR, year);
                mCurrentSelectedDate.set(Calendar.MONTH, monthOfYear);
                mCurrentSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mCurrentSelectedDate.set(Calendar.HOUR_OF_DAY, mCurrentSelectedDate.get(Calendar.HOUR_OF_DAY));
                mMeetingEndTime.setTimeInMillis(mCurrentSelectedDate.getTimeInMillis());
                mMeetingStartTime.setTimeInMillis(mCurrentSelectedDate.getTimeInMillis());
                mEndTimeBtn.setText("");
                mStartTimeBtn.setText("");

            });
            dpd.setVersion(DatePickerDialog.Version.VERSION_1);
            dpd.setMinDate(Calendar.getInstance());
            dpd.setAccentColor(ContextCompat.getColor(ScheduleMeetingActivity.this, R.color.mdtp_accent_color));
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getTimeIn12HourFormat(String time) {
        String _12HourFormat = "";
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            Date _24HourDt = _24HourSDF.parse(time);
            assert _24HourDt != null;
            _12HourFormat = _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _12HourFormat;
    }

    private String getTimeFormat(Calendar calendar) {
        SimpleDateFormat dateFormatForPortrait = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return dateFormatForPortrait.format(calendar.getTime());
    }

    private String getTimeFormatIn24Hour(Calendar calendar) {
        SimpleDateFormat dateFormatForPortrait = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        return dateFormatForPortrait.format(calendar.getTime());
    }

    private String getDateFormat(Calendar calendar) {
        SimpleDateFormat dateFormatForPortrait = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return dateFormatForPortrait.format(calendar.getTime());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstant.BundleKeys.IS_DATE_SELECTED, !TextUtils.isEmpty(mDateBtn.getText()));
        outState.putBoolean(AppConstant.BundleKeys.IS_START_TIME_SELECTED, !TextUtils.isEmpty(mStartTimeBtn.getText()));
        outState.putBoolean(AppConstant.BundleKeys.IS_END_TIME_SELECTED, !TextUtils.isEmpty(mEndTimeBtn.getText()));
        outState.putBoolean(AppConstant.BundleKeys.DESCRIPTION, !TextUtils.isEmpty(mDescriptionEt.getText().toString().trim()));
        outState.putLong(AppConstant.BundleKeys.CURRENT_DATE, mCurrentSelectedDate.getTimeInMillis());
        outState.putLong(AppConstant.BundleKeys.START_TIME, mMeetingStartTime.getTimeInMillis());
        outState.putLong(AppConstant.BundleKeys.END_TIME, mMeetingEndTime.getTimeInMillis());
    }
}
