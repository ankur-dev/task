package com.ankur.example.model;

import java.util.ArrayList;

public class MeetingResponse {

    private int status;

    private String message;

    private ArrayList<MeetingListItem> items;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<MeetingListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MeetingListItem> items) {
        this.items = items;
    }
}
