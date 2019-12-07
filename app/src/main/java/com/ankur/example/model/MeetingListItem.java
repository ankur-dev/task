package com.ankur.example.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeetingListItem implements Parcelable{
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("description")
    private String description;
    @JsonProperty("participants")
    private List<String> participants = null;

    @JsonProperty("start_time")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("start_time")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("end_time")
    public String getEndTime() {
        return endTime;
    }

    @JsonProperty("end_time")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("participants")
    public List<String> getParticipants() {
        return participants;
    }

    @JsonProperty("participants")
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.description);
        dest.writeStringList(this.participants);
    }

    public MeetingListItem() {
    }

    protected MeetingListItem(Parcel in) {
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.description = in.readString();
        this.participants = in.createStringArrayList();
    }

    public static final Creator<MeetingListItem> CREATOR = new Creator<MeetingListItem>() {
        @Override
        public MeetingListItem createFromParcel(Parcel source) {
            return new MeetingListItem(source);
        }

        @Override
        public MeetingListItem[] newArray(int size) {
            return new MeetingListItem[size];
        }
    };
}
