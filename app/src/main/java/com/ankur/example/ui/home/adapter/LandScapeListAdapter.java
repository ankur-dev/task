package com.ankur.example.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ankur.example.R;
import com.ankur.example.model.MeetingListItem;
import com.ankur.example.utill.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandScapeListAdapter extends RecyclerView.Adapter<LandScapeListAdapter.ViewHolder> {

    private final List<MeetingListItem> mItems;
    private Context mContext;

    public LandScapeListAdapter(Context context, @NonNull List<MeetingListItem> items) {
        this.mItems = items;
        mContext = context;

    }

    @Override
    public LandScapeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.landscape_list_layout, parent, false);
        return new LandScapeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LandScapeListAdapter.ViewHolder viewHolder, final int position) {
        final MeetingListItem item = mItems.get(position);
        if (item != null) {

            String meetingStartTime = item.getStartTime();
            String meetingEndTime = item.getEndTime();
            String description = item.getDescription();
            List<String> attendeeName = item.getParticipants();
            StringBuilder builder = new StringBuilder();
            if(!CollectionUtils.isEmpty(attendeeName)){
                for(String name : attendeeName){
                    builder.append(name);
                    builder.append(", ");
                }
            }


            if (!TextUtils.isEmpty(meetingEndTime)) {
                viewHolder.meetingEndTime.setText( getTimeIn12HourFormat(meetingEndTime));
            }

            if (!TextUtils.isEmpty(meetingStartTime)) {
                viewHolder.meetingStartTime.setText( getTimeIn12HourFormat(meetingStartTime));
            }

            if (!TextUtils.isEmpty(builder.toString())) {
                viewHolder.attendeeName.setText(builder.toString());
            }

            if (!TextUtils.isEmpty(description)) {
                viewHolder.meetingDescription.setText(description);
            }

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

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.meetingStartTime)
        AppCompatTextView meetingStartTime;
        @BindView(R.id.meetingDescription)
        AppCompatTextView meetingDescription;
        @BindView(R.id.meetingEndTime)
        AppCompatTextView meetingEndTime;
        @BindView(R.id.attendeeName)
        AppCompatTextView attendeeName;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
