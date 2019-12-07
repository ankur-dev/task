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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortraitListAdapter
        extends RecyclerView.Adapter<PortraitListAdapter.ViewHolder> {

    private final List<MeetingListItem> mItems;
    private Context mContext;

    public PortraitListAdapter(Context context, @NonNull List<MeetingListItem> items) {
        this.mItems = items;
        mContext = context;

    }

    @Override
    public PortraitListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meeting_list_layout_portait, parent, false);
        return new PortraitListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PortraitListAdapter.ViewHolder viewHolder, final int position) {
        final MeetingListItem item = mItems.get(position);
        if (item != null) {

            String meetingStartTime = item.getStartTime();
            String meetingEndTime = item.getEndTime();
            String description = item.getDescription();


            if (!TextUtils.isEmpty(meetingEndTime) && !TextUtils.isEmpty(meetingStartTime)) {
                String timeWithFormat = String.format("%s - %s", getTimeIn12HourFormat(meetingStartTime), getTimeIn12HourFormat(meetingEndTime));
                viewHolder.meetingTime.setText(timeWithFormat);
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

        @BindView(R.id.meetingTime)
        AppCompatTextView meetingTime;
        @BindView(R.id.meetingDescription)
        AppCompatTextView meetingDescription;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
