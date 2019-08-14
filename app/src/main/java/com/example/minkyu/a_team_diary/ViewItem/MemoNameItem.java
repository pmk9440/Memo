package com.example.minkyu.a_team_diary.ViewItem;

public class MemoNameItem {
    String Title;
    int ResId;
    String date;

    public MemoNameItem(String title, int resId) {
        Title = title;
        ResId = resId;
    }

    public MemoNameItem(String title, int resId, String date) {
        Title = title;
        ResId = resId;
        this.date = date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getResId() {
        return ResId;
    }

    public void setResId(int resId) {
        ResId = resId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MemoNameItem{" +
                "Title='" + Title + '\'' +
                ", ResId=" + ResId +
                '}';
    }
}
