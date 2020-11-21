package com.example.yu_map.Recycler;

public class FriendData {

    private String title;
    private String content;
    private int resId;

    public FriendData(String title, String content, int resId) {
        this.title = title;
        this.content = content;
        this.resId = resId;
    }

    public FriendData() {

    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public FriendData(String title) {
        this.title = title;
    }
}
